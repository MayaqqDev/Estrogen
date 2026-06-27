package dev.mayaqq.estrogen.neoforge.client

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_TEXTURES
import dev.mayaqq.estrogen.client.THIGH_HIGH_MODELS_DIRECTORY
import dev.mayaqq.estrogen.client.content.block.ClientDreamBlock
import dev.mayaqq.estrogen.client.content.models.ThighHighsItemModel
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import dev.mayaqq.estrogen.utils.resources.listResourceIds
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModLoadingContext
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.ModelEvent
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent

@EventBusSubscriber(modid = MOD_ID, value = [Dist.CLIENT])
object EstrogenForgeClient {
    @SubscribeEvent
    fun onClientInit(event: FMLClientSetupEvent) {
        Estrogen.info("initializing estrogen client")
        event.enqueueWork(::estrogenClient)

        /* TODO: Config Screen Factory
        @Suppress("Deprecation", "Removal")
        ModLoadingContext.get().activeContainer.registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory::class.java
        ) {
            ConfigScreenHandler.ConfigScreenFactory { minecraft: Minecraft, screen: Screen ->
                EstrogenMenuScreen(screen)
            }
        }
         */

    }

    @SubscribeEvent
    fun registerCustomItemTinters(event: RegisterColorHandlersEvent.Item) {
        event.register(ThighHighsItem::getItemColor, EstrogenItems.ThighHighs)
        event.register(DreamCatcherItem::getItemColor, EstrogenBlocks.DreamCatcher.asItem())
    }

    @SubscribeEvent
    fun loadAdditionModels(event: ModelEvent.RegisterAdditional) {
        Minecraft.getInstance().resourceManager.listResourceIds(THIGH_HIGH_MODELS_DIRECTORY, "models", ".json")
            .first
            //TODO: so i jsut made it standalone here? obviously not gonna work but whatt
            .forEach { location -> event.register(ModelResourceLocation.standalone(location))}
    }

    @SubscribeEvent
    fun modifyBakeResult(event: ModelEvent.ModifyBakingResult) {
        //TODO: idk im a bit over my head with them just messing with ModelResourceLocation
        val replaceIds: List<Pair<ModelResourceLocation, BakedModel>> = event.models.mapNotNull { (id, model) ->
            if (model == null) return@mapNotNull null
            if (id.id.namespace == MOD_ID && id.id.path == "dream_block") id to model else null
        }

        replaceIds.forEach { (id , model) ->
            event.models[id] = ForgeConnectedModel(model, ClientDreamBlock.DORMANT_CONNECTED_TEXTURE)
        }
    }
}

fun modifyThighHighModel(default: UnbakedModel): UnbakedModel {
    val textures = Minecraft.getInstance().resourceManager.listResourceIds(THIGH_HIGH_ITEM_TEXTURES, "textures", ".png").first
    return ThighHighsItemModel(default, textures) { default, styles ->
        object : BakedModel {
            override fun getRenderPasses(itemStack: ItemStack, fabulous: Boolean): List<BakedModel> {
                return EstrogenItems.ThighHighs.getStyle(itemStack)?.let(styles::get)
                    ?.let(::listOf)
                    ?: default.getRenderPasses(itemStack, fabulous)
            }

            override fun getQuads(
                state: BlockState?,
                direction: Direction?,
                random: RandomSource
            ): List<BakedQuad?> = default.getQuads(state, direction, random)

            override fun useAmbientOcclusion(): Boolean = default.useAmbientOcclusion()

            override fun isGui3d(): Boolean = default.isGui3d

            override fun usesBlockLight(): Boolean = default.usesBlockLight()

            override fun isCustomRenderer(): Boolean = default.isCustomRenderer

            override fun getParticleIcon(): TextureAtlasSprite = default.particleIcon

            override fun getOverrides(): ItemOverrides = default.overrides
        }
    }
}

