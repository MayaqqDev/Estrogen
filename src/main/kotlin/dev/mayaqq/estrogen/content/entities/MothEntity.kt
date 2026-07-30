package dev.mayaqq.estrogen.content.entities

import dev.mayaqq.cynosure.core.Loader
import dev.mayaqq.cynosure.core.currentLoader
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.utils.tag
import dev.mayaqq.estrogen.cid
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.utils.defaultInstance
import net.minecraft.Util
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.DamageTypeTags
import net.minecraft.tags.TagKey
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.FlyingMoveControl
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.animal.FlyingAnimal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.pathfinder.PathType
import net.minecraft.world.phys.Vec3
import java.util.*

class MothEntity(type: EntityType<MothEntity>, level: Level) : Animal(type, level), FlyingAnimal, Shearable {
    val flyingAnimationState: AnimationState = AnimationState()
    val idleAnimationState: AnimationState = AnimationState()
    val fuzzUpFlyingAnimationState: AnimationState = AnimationState()
    val fuzzUpIdleAnimationState: AnimationState = AnimationState()
    val landingAnimationState: AnimationState = AnimationState()
    val takingOffAnimationState: AnimationState = AnimationState()

    var ticksToFuzzUp: Int = 0
        get() {
            if (field == 0) {
                field = this.random.nextIntBetweenInclusive(12000, 36000)
            }
            return field
        }
        private set

    private var fuzzingUp = false
    private var fuzzupCooldown = 0

    init {
        //There was a thing for the define here as well
        this.moveControl = FlyingMoveControl(this, 20, true)
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0f)
        this.setPathfindingMalus(PathType.WATER, -1.0f)
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0f)
        this.setPathfindingMalus(PathType.FENCE, -1.0f)
    }

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        super.defineSynchedData(builder)
        builder.define(ANIMATION_STATES, 1.toByte())
        builder.define(DATA_FUZZY, false)
    }

    override fun tick() {
        fuzzupCooldown--
        if (fuzzupCooldown < 0) {
            fuzzupCooldown = 0
        }
        if (fuzzupCooldown == 0) {
            this.fuzzingUp = false
        }
        super.tick()

        if (this.isFuzzy()) {
            if (this.random.nextFloat() < 0.05f) {
                for (i in 0..<this.random.nextInt(2) + 1) {
                    this.spawnFuzzyParticle(
                        this.level(),
                        this.x - 0.3,
                        this.x + 0.3,
                        this.z - 0.3,
                        this.z + 0.3,
                        this.getY(0.5),
                        getParticleType()
                    )
                }
            }
        }

        if (!this.level().isClientSide && !this.isFuzzy() && !this.isBaby) {
            if (this.level().gameTime % this.ticksToFuzzUp == 0L) {
                this.setFuzzy()
                for (i in 0..6) {
                    this.spawnFuzzyParticle(
                        this.level(),
                        this.x - 0.3,
                        this.x + 0.3,
                        this.z - 0.3,
                        this.z + 0.3,
                        this.getY(0.5),
                        getParticleType()
                    )
                }
                this.playSound(EstrogenSounds.MOTH_FUZZ_UP.get())
                this.fuzzingUp()
            }
        }
    }

    private fun fuzzingUp() {
        fuzzupCooldown = 48
        this.fuzzingUp = true
    }

    private fun isFuzzingUp(): Boolean {
        return this.fuzzingUp
    }

    override fun aiStep() {
        super.aiStep()
        val isMoving = this.x - this.xo != 0.0 || this.z - this.zo != 0.0
        if (this.isFlying) {
            if (this.isFuzzingUp()) {
                this.setState(State.FUZZUP_FLYING)
            }
            this.setState(State.FLYING)
        } else {
            if (this.isFuzzingUp()) {
                this.setState(State.FUZZUP_FLYING)
            }
            if (this.getState() == State.FLYING) {
                this.setState(State.LANDING)
            } else {
                this.setState(if (isMoving) State.TAKING_OFF else State.IDLE)
            }
        }
    }

    private fun getParticleType(): ParticleOptions = EstrogenParticles.MothFuzz

    // Stolen from bee code :3
    private fun spawnFuzzyParticle(
        level: Level,
        startX: Double,
        endX: Double,
        startZ: Double,
        endZ: Double,
        posY: Double,
        particleOption: ParticleOptions
    ) {
        level.addParticle(
            particleOption,
            Mth.lerp(level.random.nextDouble(), startX, endX),
            posY,
            Mth.lerp(level.random.nextDouble(), startZ, endZ),
            0.0,
            0.0,
            0.0
        )
    }

    fun getState(): State {
        return State.entries[this.entityData.get<Byte>(ANIMATION_STATES).toInt()]
    }

    private fun setState(state: State) {
        this.entityData.set<Byte>(ANIMATION_STATES, state.ordinal.toByte())
    }

    override fun onSyncedDataUpdated(key: EntityDataAccessor<*>) {
        if (ANIMATION_STATES == key) {
            stopAll()

            when (this.getState()) {
                State.FLYING -> this.flyingAnimationState.startIfStopped(this.age)
                State.IDLE -> this.idleAnimationState.startIfStopped(this.age)
                State.TAKING_OFF -> this.takingOffAnimationState.startIfStopped(this.age)
                State.LANDING -> this.landingAnimationState.startIfStopped(this.age)
                State.FUZZUP_FLYING -> this.fuzzUpFlyingAnimationState.startIfStopped(this.age)
                State.FUZZUP_IDLE -> this.fuzzUpIdleAnimationState.startIfStopped(this.age)
            }
        }
        super.onSyncedDataUpdated(key)
    }

    fun stopAll() {
        this.flyingAnimationState.stop()
        this.idleAnimationState.stop()
        this.fuzzUpFlyingAnimationState.stop()
        this.fuzzUpIdleAnimationState.stop()
        this.landingAnimationState.stop()
        this.takingOffAnimationState.stop()
    }

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        if (source.`is`(DamageTypeTags.IS_FIRE)) {
            this.setSheared()
        }
        return super.hurt(source, amount)
    }

    override fun mobInteract(player2: Player, hand: InteractionHand): InteractionResult {
        val itemStack = player2.getItemInHand(hand)
        if (itemStack.`is`(shearsTag)) {
            if (!this.level().isClientSide && this.readyForShearing()) {
                this.shear(SoundSource.PLAYERS)
                this.gameEvent(GameEvent.SHEAR, player2)
                itemStack.hurtAndBreak(1, player2, getSlotForHand(hand))
                return InteractionResult.SUCCESS
            }
            return InteractionResult.CONSUME
        }
        return super.mobInteract(player2, hand)
    }

    override fun shear(source: SoundSource) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, source, 1.0f, 1.0f)
        this.setSheared()
        val i = 1 + this.random.nextInt(3)
        for (j in 0..< i) {
            val itemEntity = this.spawnAtLocation(EstrogenItems.MothFuzz.defaultInstance(), 0.5f)
            if (itemEntity == null) continue
            itemEntity.deltaMovement = itemEntity.deltaMovement.add(
                ((this.random.nextFloat() - this.random.nextFloat()) * 0.1f).toDouble(),
                (this.random.nextFloat() * 0.05f).toDouble(),
                ((this.random.nextFloat() - this.random.nextFloat()) * 0.1f).toDouble()
            )
        }
    }

    override fun readyForShearing(): Boolean = this.isAlive && this.isFuzzy() && !this.isBaby

    override fun addAdditionalSaveData(compound: CompoundTag) {
        super.addAdditionalSaveData(compound)
        compound.putInt("TicksToFuzzUp", this.ticksToFuzzUp)
        compound.putBoolean("Fuzzy", this.isFuzzy())
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        super.readAdditionalSaveData(compound)
        this.ticksToFuzzUp = compound.getInt("TicksToFuzzUp")
        this.setFuzzy(compound.getBoolean("Fuzzy"))
    }

    fun isFuzzy(): Boolean = this.entityData.get(DATA_FUZZY)

    fun setFuzzy(fuzzy: Boolean) {
        this.entityData.set(DATA_FUZZY, fuzzy)
    }

    fun setFuzzy() {
        this.setFuzzy(true)
    }

    fun setSheared() {
        this.setFuzzy(false)
    }

    override fun getWalkTargetValue(pos: BlockPos, level: LevelReader): Float {
        if (level.getBlockState(pos).isAir) {
            return 10.0f
        }
        return 0.0f
    }

    override fun registerGoals() {
        this.goalSelector.addGoal(0, PanicGoal(this, 1.25))
        this.goalSelector.addGoal(2, BreedGoal(this, 1.0))
        this.goalSelector.addGoal(2, WaterAvoidingRandomFlyingGoal(this, 1.0))
        this.goalSelector.addGoal(3, TemptByLightBlockGoal(this, 1.0, 5))
        this.goalSelector.addGoal(4, TemptGoal(this, 1.25, Ingredient.of(EstrogenTags.Items.LIGHT_EMITTERS), false))
        this.goalSelector.addGoal(5, TemptGoal(this, 1.25, Ingredient.of(EstrogenTags.Items.LEATHER_ITEMS), false))
        this.goalSelector.addGoal(6, FollowParentGoal(this, 1.25))
        this.goalSelector.addGoal(9, FloatGoal(this))
    }

    override fun isFlying(): Boolean {
        return !this.onGround()
    }

    override fun createNavigation(level: Level): PathNavigation {
        val flyingPathNavigation: FlyingPathNavigation = object : FlyingPathNavigation(this, level) {
            override fun isStableDestination(pos: BlockPos): Boolean {
                return !this.level.getBlockState(pos.below()).isAir
            }
        }
        flyingPathNavigation.setCanOpenDoors(false)
        flyingPathNavigation.setCanFloat(false)
        flyingPathNavigation.setCanPassDoors(true)
        return flyingPathNavigation
    }

    override fun isFood(stack: ItemStack): Boolean {
        return stack.`is`(EstrogenTags.Items.LEATHER_ITEMS)
    }

    override fun playStepSound(pos: BlockPos, state: BlockState) {
    }

    public override fun getAmbientSound(): SoundEvent? {
        return null
    }

    override fun getHurtSound(damageSource: DamageSource): SoundEvent = EstrogenSounds.MOTH_HURT.get()

    override fun getDeathSound(): SoundEvent = EstrogenSounds.MOTH_DEATH.get()

    override fun getSoundVolume(): Float = 0.4f

    override fun getBreedOffspring(level: ServerLevel, otherParent: AgeableMob): MothEntity? = EstrogenEntities.Moth.get().create(level)

    public override fun isFlapping(): Boolean = this.isFlying && this.tickCount % TICKS_PER_FLAP == 0

    override fun checkFallDamage(y: Double, onGround: Boolean, state: BlockState, pos: BlockPos) {
        // Lil fella doesn't take fall damage
    }

    override fun jumpInLiquid(fluidTag: TagKey<Fluid?>) {
        this.deltaMovement = this.deltaMovement.add(0.0, 0.01, 0.0)
    }

    public override fun getLeashOffset(): Vec3 = Vec3(0.0, (0.5f * this.eyeHeight).toDouble(), (this.bbWidth * 0.2f).toDouble())

    enum class State {
        FLYING,
        IDLE,
        FUZZUP_FLYING,
        FUZZUP_IDLE,
        LANDING,
        TAKING_OFF
    }

    companion object {
        const val TICKS_PER_FLAP: Int = 2
        private val DATA_FUZZY: EntityDataAccessor<Boolean> = SynchedEntityData.defineId<Boolean>(MothEntity::class.java, EntityDataSerializers.BOOLEAN)
        private val ANIMATION_STATES: EntityDataAccessor<Byte> = SynchedEntityData.defineId<Byte>(MothEntity::class.java, EntityDataSerializers.BYTE)

        val shearsTag: TagKey<Item> = Registries.ITEM.tag(cid("tools/shear"))

        fun createAttributes(): AttributeSupplier.Builder = createMobAttributes()
            .add(Attributes.MAX_HEALTH, 10.0)
            .add(Attributes.FLYING_SPEED, 0.6)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.FOLLOW_RANGE, 48.0)

        fun checkMobSpawnRules(type: EntityType<out Mob?>, level: LevelAccessor, spawnType: MobSpawnType, pos: BlockPos, random: RandomSource): Boolean {
            return Mob.checkMobSpawnRules(type, level, spawnType, pos, random) && level.dayTime() % 24000 in 13000..< 23000 && level.moonPhase != 4
        }
    }

    class TemptByLightBlockGoal(val moth: MothEntity, val speedModifier: Double, val within: Int) : Goal() {
        init {
            this.flags = EnumSet.of(Flag.MOVE)
        }

        private var wantedX: Double = 0.0
        private var wantedY: Double = 0.0
        private var wantedZ: Double = 0.0

        override fun canUse(): Boolean {
            val level = this.moth.level()
            if (this.moth.getRandom().nextInt(20) != 0) return false
            val mothPos = this.moth.blockPosition()
            val pos = this.moth.blockPosition().mutable()

            for (blockPos in OFFSETS) {
                pos.setWithOffset(mothPos, blockPos)
                val state = level.getBlockState(pos)
                if (state.lightEmission > 0) {
                    this.wantedX = pos.x.toDouble()
                    this.wantedY = pos.y.toDouble()
                    this.wantedZ = pos.z.toDouble()
                    return true
                }
            }
            return false
        }

        override fun canContinueToUse(): Boolean {
            val pos = this.moth.blockPosition()
            val targetPos = BlockPos(this.wantedX.toInt(), this.wantedY.toInt(), this.wantedZ.toInt())
            return !this.moth.getNavigation().isDone && targetPos.distToCenterSqr(
                pos.x.toDouble(),
                pos.y.toDouble(),
                pos.z.toDouble()
            ) < (this.within * this.within).toDouble()
        }

        override fun start() {
            this.moth.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier)
        }

        companion object {
            private val OFFSETS: ArrayList<BlockPos> = Util.make<ArrayList<BlockPos>>(ArrayList<BlockPos>()) { offsets: ArrayList<BlockPos> ->
                var i = 0
                while (i.toDouble() <= 5) {
                    var j = 0
                    while (j.toDouble() < 5) {
                        var k = 0
                        while (k <= j) {
                            var l = if (k < j && k > -j) j else 0
                            while (l <= j) {
                                offsets.add(BlockPos(k, i - 1, l))
                                l = if (l > 0) -l else 1 - l
                            }
                            k = if (k > 0) -k else 1 - k
                        }
                        ++j
                    }
                    i = if (i > 0) -i else 1 - i
                }
            }
        }
    }
}