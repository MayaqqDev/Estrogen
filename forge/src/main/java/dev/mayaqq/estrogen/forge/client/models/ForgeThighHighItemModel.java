package dev.mayaqq.estrogen.forge.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.mayaqq.estrogen.client.registry.models.ThighHighItemModel;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.utils.LocationResolver;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class ForgeThighHighItemModel extends ThighHighItemModel<ForgeThighHighItemModel.Default> {

    // Need an AtomicRefernce here as the LocationResolver is loaded on another thread
    // * thread safety yippie *
    public static final AtomicReference<LocationResolver> forgeLocationResolver = new AtomicReference<>();

    public ForgeThighHighItemModel(UnbakedModel defaultModel) {
        super(new Default(defaultModel), forgeLocationResolver.get());
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        return List.of(EstrogenItems.THIGH_HIGHS.get()
            .getStyle(itemStack)
            .map(styleModels::get)
            .orElse(defaultModel));
    }

    // This needs to return false on forge and true on fabric
    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    public static class Default implements BakedModel, UnbakedModel {

        private final UnbakedModel original;
        private BakedModel baked;

        public Default(UnbakedModel originalModel) {
            this.original = originalModel;
        }

        @Override
        public Collection<ResourceLocation> getDependencies() {
            return original.getDependencies();
        }

        @Override
        public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
            original.resolveParents(resolver);
        }

        @Nullable
        @Override
        public BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state, ResourceLocation location) {
            ItemModelGenerator generator = new ItemModelGenerator();
            BlockModel bm = generator.generateBlockModel(spriteGetter, (BlockModel) original);
            this.baked = bm.bake(baker, bm, spriteGetter, state, location, false);
            return this;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
            return baked.getQuads(state, direction, random);
        }

        @Override
        public boolean useAmbientOcclusion() {
            return baked.useAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            return baked.isGui3d();
        }

        @Override
        public boolean usesBlockLight() {
            return baked.usesBlockLight();
        }

        @Override
        public boolean isCustomRenderer() {
            return baked.isCustomRenderer();
        }

        @Override
        public TextureAtlasSprite getParticleIcon() {
            return baked.getParticleIcon();
        }

        @Override
        public ItemOverrides getOverrides() {
            return baked.getOverrides();
        }

        @Override
        public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
            return baked.getQuads(state, side, rand, data, renderType);
        }

        @Override
        public boolean useAmbientOcclusion(BlockState state) {
            return baked.useAmbientOcclusion(state);
        }

        @Override
        public boolean useAmbientOcclusion(BlockState state, RenderType renderType) {
            return baked.useAmbientOcclusion(state, renderType);
        }

        @Override
        public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
            return baked.applyTransform(transformType, poseStack, applyLeftHandTransform);
        }

        @Override
        public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
            return baked.getModelData(level, pos, state, modelData);
        }

        @Override
        public TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
            return baked.getParticleIcon(data);
        }

        @Override
        public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
            return baked.getRenderTypes(state, rand, data);
        }

        @Override
        public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
            return baked.getRenderTypes(itemStack, fabulous);
        }

        @Override
        public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
            return baked.getRenderPasses(itemStack, fabulous);
        }
    }
}
