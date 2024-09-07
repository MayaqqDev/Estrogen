package dev.mayaqq.estrogen.client.registry;

import com.google.common.collect.ImmutableMap;
import com.jozufozu.flywheel.api.struct.StructType;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.render.SuperByteBufferCache;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.render.CosmeticRenderLayer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamData;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamType;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenRenderer {

    // Inctance Types
    public static final StructType<DreamData> DREAM = new DreamType();

    // Partiql Models
    public static final PartialModel THIGH_HIGH = new PartialModel(id("trinket/thigh_high_base"));
    public static final PartialModel THIGH_HIGH_OVERLAY = new PartialModel(id("trinket/thigh_high_overlay"));
    public static final PartialModel CENTRIFUGE_COG = block("centrifuge/cog");

    // Buffer cache compartments
    public static final SuperByteBufferCache.Compartment<ResourceLocation> GENERIC = new SuperByteBufferCache.Compartment<>();

    private static PartialModel block(String path) {
        return new PartialModel(id("block/" + path));
    }

    public static void register() {
        CreateClient.BUFFER_CACHE.registerCompartment(GENERIC);
    }

    public static void registerEntityLayers(Function<String, EntityRenderer<? extends Player>> getter) {
        PlayerRenderer wide = (PlayerRenderer) getter.apply("default");
        PlayerRenderer slim = (PlayerRenderer) getter.apply("slim");
        wide.addLayer(new CosmeticRenderLayer(wide));
        slim.addLayer(new CosmeticRenderLayer(slim));
    }
}
