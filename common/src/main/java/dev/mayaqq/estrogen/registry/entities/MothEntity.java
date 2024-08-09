package dev.mayaqq.estrogen.registry.entities;

import dev.mayaqq.estrogen.registry.EstrogenTags;
import dev.mayaqq.estrogen.registry.entities.goals.TemptByLightBlockGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

public class MothEntity extends Animal implements FlyingAnimal, Shearable {

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(MothEntity.class, EntityDataSerializers.BYTE);

    private boolean fluffy;

    public MothEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.lookControl = new MothLookControl(this);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0f);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0f);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0f);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0f);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (level.getBlockState(pos).isAir()) {
            return 10.0f;
        }
        return 0.0f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptByLightBlockGoal(this, 1.0, 5));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25, Ingredient.of(EstrogenTags.Items.LIGHT_EMITTERS), false));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.25, Ingredient.of(EstrogenTags.Items.LEATHER_ITEMS), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(9, new FloatGoal(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("fluffy", this.fluffy);
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.fluffy = compound.getBoolean("fluffy");
        super.readAdditionalSaveData(compound);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public void shear(SoundSource source) {

    }

    @Override
    public boolean readyForShearing() {
        return false;
    }

    static class MothLookControl extends LookControl {
        public MothLookControl(Mob mob) {
            super(mob);
        }
    }
}
