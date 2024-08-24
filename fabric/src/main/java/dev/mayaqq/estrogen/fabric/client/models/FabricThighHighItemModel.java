package dev.mayaqq.estrogen.fabric.client.models;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.models.ThighHighItemModel;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.utils.LocationResolver;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricThighHighItemModel extends ThighHighItemModel<FabricThighHighItemModel.Default> {

    public FabricThighHighItemModel(UnbakedModel defaultModel, LocationResolver spriteLocations) {
        super(new Default(defaultModel), spriteLocations);
        Estrogen.LOGGER.info(String.valueOf(spriteLocations.locations().size()));
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        defaultModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        Optional<ResourceLocation> style = EstrogenItems.THIGH_HIGHS.get().getStyle(stack);
        style.map(styleModels::get).ifPresentOrElse(
            model -> model.emitItemQuads(stack, randomSupplier, context),
            () -> defaultModel.emitItemQuads(stack, randomSupplier, context)
        );
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    public static class Default extends ForwardingBakedModel implements UnbakedModel {

        private final UnbakedModel unbaked;

        public Default(UnbakedModel unbaked) {
            this.unbaked = unbaked;
        }

        @Override
        public Collection<ResourceLocation> getDependencies() {
            return unbaked.getDependencies();
        }

        @Override
        public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
            unbaked.resolveParents(resolver);
        }

        @Nullable
        @Override
        public BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state, ResourceLocation location) {
            ItemModelGenerator generator = new ItemModelGenerator();
            BlockModel bm = generator.generateBlockModel(spriteGetter, (BlockModel) unbaked);
            this.wrapped = bm.bake(baker, bm, spriteGetter, state, location, false);
            return this;
        }
    }
}
