package dev.mayaqq.estrogen.client.registry;

import com.google.common.collect.ImmutableMap;
import com.jozufozu.flywheel.api.struct.StructType;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamData;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamType;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenRenderer {

    public static final StructType<DreamData> DREAM = new DreamType();

    public static final PartialModel THIGH_HIGH = new PartialModel(id("trinket/thigh_high_base"));
    public static final PartialModel THIGH_HIGH_OVERLAY = new PartialModel(id("trinket/thigh_high_overlay"));

    public static final PartialModel CENTRIFUGE_COG = block("centrifuge/cog");
    private static PartialModel block(String path) {
        return new PartialModel(id("block/" + path));
    }

    public static void register() {}
}
