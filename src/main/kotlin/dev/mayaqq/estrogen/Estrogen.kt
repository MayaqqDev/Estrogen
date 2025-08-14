package dev.mayaqq.estrogen

//import dev.mayaqq.estrogen.config.Instance
//import uwu.serenity.kittyconfig.api.defaults.load
import dev.mayaqq.cynosure.data.registerDatapackReloadListener
import dev.mayaqq.cynosure.events.PostInitEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.features.thighhighs.ThighHighStyleLoader
import dev.mayaqq.estrogen.network.EstrogenNetwork
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uwu.serenity.kittyconfig.loadConfig
import uwu.serenity.kritter.RegistryManager
import uwu.serenity.kritter.get

const val MOD_ID = "estrogen"
const val MOD_NAME = "Estrogen"

inline fun id(path: String) = ResourceLocation(MOD_ID, path)
inline fun mcid(path: String) = ResourceLocation("minecraft", path)

@EventSubscriber
object Estrogen : Logger by LoggerFactory.getLogger(MOD_NAME), RegistryManager by RegistryManager(MOD_ID) {

    fun init() {
        // TODO: When intellij plugin use the extensions instead of the top-level variants
        loadConfig(EstrogenCommonConfig)
        loadConfig(EstrogenServerConfig)

        EstrogenAttributes.register()
        EstrogenSounds.register()
        EstrogenBlocks.register()
        EstrogenBlockEntities.register()
        EstrogenEffects.register()
        EstrogenParticles.register()
        EstrogenEnchantments.register()
        AdvancementTriggers.register()
        EstrogenFluids.register()
        EstrogenPotions.register()
        EstrogenItems.register()
        EstrogenCreativeTab.register()
        EstrogenEntities.register()
        EstrogenRecipes.register()
        EstrogenRecipes.Serializers.register()
        EstrogenPoiTypes.register()

        info("Injecting Estrogen into your veins!")

        registerDatapackReloadListener(id("thigh_high_styles"), ThighHighStyleLoader)

        EstrogenNetwork
    }

    @Subscription
    fun postInit(event: PostInitEvent) {
        val key = Estrogen[ResourceKey.create(Registries.ITEM, id("estrogen_pill"))]
        Estrogen.info("Info: $key")
    }
}