package dev.mayaqq.estrogen.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.impl.controller.TickBoxControllerBuilderImpl;
import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.nio.file.Files;
import java.nio.file.Path;

public class EstrogenConfig {
    public static final EstrogenConfig INSTANCE = new EstrogenConfig();

    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("estrogen.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean chestFeature = true;

    public void save() {
        try {
            Files.deleteIfExists(configFile);

            JsonObject json = new JsonObject();
            json.addProperty("chestFeature", chestFeature);

            Files.writeString(configFile, gson.toJson(json));
        } catch (Exception e) {
            Estrogen.LOGGER.error("Failed to load config file!");
        }
    }

    public void load() {
        try {
            if (Files.notExists(configFile)) {
                save();
                return;
            }

            JsonObject json = gson.fromJson(Files.readString(configFile), JsonObject.class);

            if (json.has("chestFeature"))
                chestFeature = json.getAsJsonPrimitive("chestFeature").getAsBoolean();
        } catch (Exception e) {
            Estrogen.LOGGER.error("Failed to load config file!");
        }
    }

    public Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("Estrogen Config"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("Client"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.of("Chest Feature"))
                                .binding(
                                        false,
                                        () -> chestFeature,
                                        value -> chestFeature = value
                                )
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }
}
