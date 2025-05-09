package dev.mayaqq.estrogen

import dev.mayaqq.cynosure.entities.EntityAttributes
import dev.mayaqq.cynosure.events.PostInitEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.isModLoaded
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.config.Instance
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.network.EstrogenNetwork
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uwu.serenity.kittyconfig.api.defaults.load
import uwu.serenity.kritter.RegistryManager

const val MOD_ID = "estrogen"
const val MOD_NAME = "Estrogen"

private const val mcCapesMessage = """
            ----------------------------------------------------------------------------
            Minecraft Capes is detected! This mod currently causes some features
            of Estrogen to not work properly, before making an issue, please make sure
            to first update and disable Minecraft Capes and see if the issue persists.
            ----------------------------------------------------------------------------
            """

internal inline fun id(path: String) = ResourceLocation(MOD_ID, path)

@EventSubscriber
object Estrogen : Logger by LoggerFactory.getLogger(MOD_NAME), RegistryManager by RegistryManager(MOD_ID) {

    fun init() {
        if (isModLoaded("minecraftcapes")) {
            mcCapesMessage.trimIndent().split("\n").forEach {
                info("[ESTROGEN] $it")
            }
        }

        EstrogenCommonConfig.Instance.load()
        EstrogenServerConfig.Instance.load()

        EstrogenAttributes.register()
        EstrogenSounds.register()
        EstrogenBlocks.register()
        EstrogenBlockEntities.register()
        EstrogenEffects.register()
        EstrogenParticles.register()
        EstrogenEnchantments.register()
        AdvancementTriggers.register()
        EstrogenPotions.register()
        EstrogenItems.register()
        EstrogenCreativeTab.register()
        EstrogenEntities.register()
        EstrogenRecipes.register()
        EstrogenRecipeSerializers.register()

        info("Injecting Estrogen into your veins!")

        EstrogenNetwork
    }

    @Subscription
    fun postInit(event: PostInitEvent) {
        EntityAttributes.modify(EntityType.PLAYER) {
            add(EstrogenAttributes.DASH_LEVEL)
            add(EstrogenAttributes.FALL_DAMAGE_RESISTANCE)
            add(EstrogenAttributes.SHOW_BOOBS)
            add(EstrogenAttributes.BOOB_INITIAL_SIZE)
            add(EstrogenAttributes.BOOB_GROWING_START_TIME)
        }
    }

}