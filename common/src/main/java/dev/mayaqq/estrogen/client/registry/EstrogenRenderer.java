package dev.mayaqq.estrogen.client.registry;

import com.google.common.collect.ImmutableMap;
import com.jozufozu.flywheel.api.struct.StructType;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.render.SuperByteBufferCache;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamData;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamType;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenRenderer {

    public static final SuperByteBufferCache.Compartment<DreamBlockEntity> DREAM_BLOCKS =
        new SuperByteBufferCache.Compartment<>();

    public static final StructType<DreamData> DREAM = new DreamType();

    public static final VertexFormat POS_TEX_NORMAL_FORMAT = new VertexFormat(
        ImmutableMap.<String, VertexFormatElement>builder()
            .put("Position", ELEMENT_POSITION)
            .put("UV0", ELEMENT_UV0)
            .put("Normal", ELEMENT_NORMAL).build()
    );

    public static final PartialModel CENTRIFUGE_COG = block("centrifuge/cog");
    private static PartialModel block(String path) {
        return new PartialModel(id("block/" + path));
    }

    public static void register() {
        CreateClient.BUFFER_CACHE.registerCompartment(DREAM_BLOCKS, 2000);
    }
}
