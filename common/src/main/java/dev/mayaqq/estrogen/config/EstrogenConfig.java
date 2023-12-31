package dev.mayaqq.estrogen.config;

import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.mayaqq.estrogen.Estrogen;

import java.nio.file.Path;

public class EstrogenConfig {

    public static final Path configFile = Platform.getConfigFolder().resolve("Estrogen.json");

    @AutoGen(category = "client")
    @CustomDescription("yacl3.config.estrogen:config.chest_feature.desc")
    @TickBox
    @SerialEntry
    public boolean chestFeature = true;

    public static final ConfigClassHandler<EstrogenConfig> HANDLER = ConfigClassHandler.createBuilder(EstrogenConfig.class)
            .id(Estrogen.id("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(configFile).appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();
}
