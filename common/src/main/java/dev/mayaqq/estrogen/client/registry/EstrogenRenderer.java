package dev.mayaqq.estrogen.client.registry;

import com.jozufozu.flywheel.api.struct.StructType;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.render.SuperByteBufferCache;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamData;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamType;
import net.minecraft.resources.ResourceLocation;

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
}
