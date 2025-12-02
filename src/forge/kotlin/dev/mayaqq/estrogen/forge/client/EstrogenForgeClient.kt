package dev.mayaqq.estrogen.forge.client

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_TEXTURES
import dev.mayaqq.estrogen.client.THIGH_HIGH_MODELS_DIRECTORY
import dev.mayaqq.estrogen.client.content.block.ClientDreamBlock
import dev.mayaqq.estrogen.client.content.models.ThighHighsItemModel
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.content.EstrogenItems
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
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.ConfigScreenHandler
import net.minecraftforge.client.event.ModelEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object EstrogenForgeClient {
    @SubscribeEvent
    fun onClientInit(event: FMLClientSetupEvent) {
        Estrogen.info("initializing estrogen client")
        event.enqueueWork(::estrogenClient)

        @Suppress("Deprecation", "Removal")
        ModLoadingContext.get().activeContainer.registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory::class.java
        ) {
            ConfigScreenHandler.ConfigScreenFactory { minecraft: Minecraft, screen: Screen ->
                EstrogenMenuScreen(screen)
            }
        }

    }

    @SubscribeEvent
    fun loadAdditionModels(event: ModelEvent.RegisterAdditional) {
        Minecraft.getInstance().resourceManager.listResourceIds(THIGH_HIGH_MODELS_DIRECTORY, "models", ".json")
            .first
            .forEach(event::register)
    }

    @SubscribeEvent
    fun modifyBakeResult(event: ModelEvent.ModifyBakingResult) {
        val replaceIds: List<Pair<ResourceLocation, BakedModel>> = event.models.mapNotNull { (id, model) ->
            if (model == null) return@mapNotNull null
            if (id !is ModelResourceLocation) {
                return@mapNotNull if (id == ClientDreamBlock.DORMANT_MODEL) id to model else null
            }
            if (id.namespace == MOD_ID && id.path == "dream_block") id to model else null
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

