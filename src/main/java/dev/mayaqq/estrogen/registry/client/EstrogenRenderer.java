package dev.mayaqq.estrogen.registry.client;

import com.jozufozu.flywheel.core.PartialModel;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenRenderer {

    public static final PartialModel CENTRIFUGE_COG = block("centrifuge/cog");

    private static PartialModel block(String path) {
        return new PartialModel(id("block/" + path));
    }

    public static void register() {}
}
