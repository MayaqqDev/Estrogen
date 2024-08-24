package dev.mayaqq.estrogen.client.registry.models;

import com.mojang.datafixers.util.Either;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.utils.LocationResolver;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class ThighHighItemModel<T extends BakedModel & UnbakedModel> implements BakedModel, UnbakedModel {

    protected final T defaultModel;
    protected final LocationResolver spriteResolver;
    protected final Map<ResourceLocation, BakedModel> styleModels = new HashMap<>();

    protected ThighHighItemModel(T defaultModel, LocationResolver spriteResolver) {
        this.defaultModel = defaultModel;
        this.spriteResolver = spriteResolver;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return defaultModel.getDependencies();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
        defaultModel.resolveParents(resolver);
    }

    @Nullable
    @Override
    public BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state, ResourceLocation location) {

        Estrogen.LOGGER.info("baking {} thigh highs", spriteResolver.locations().size());
        defaultModel.bake(baker, spriteGetter, state, location);
        ItemModelGenerator generator = new ItemModelGenerator();

        for(ResourceLocation sprite : spriteResolver.locations()) {
            Material material = new Material(InventoryMenu.BLOCK_ATLAS, sprite);

            Estrogen.LOGGER.info(sprite.toString());
            // Nothing but the sprites here actually matters
            BlockModel model = new BlockModel(
                null,
                List.of(),
                Map.of(
                    "layer0", Either.left(material)
                ),
                false,
                BlockModel.GuiLight.FRONT,
                defaultModel.getTransforms(),
                List.of()
            );

            BlockModel generated = generator.generateBlockModel(spriteGetter, model);
            styleModels.put(textureToStyleLocation(sprite), generated.bake(baker, generated, spriteGetter, state, location, false));
        }

        return this;
    }

    private static ResourceLocation textureToStyleLocation(ResourceLocation original) {
        String path = original.getPath();
        int length = "item/thigh_highs/".length();
        return new ResourceLocation(original.getNamespace(), path.substring(length));
    }

    // == BEGIN BOILERPLATE ==

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
        return defaultModel.getQuads(state, direction, random);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return defaultModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return defaultModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return defaultModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return defaultModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return defaultModel.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {
        return defaultModel.getOverrides();
    }
}
