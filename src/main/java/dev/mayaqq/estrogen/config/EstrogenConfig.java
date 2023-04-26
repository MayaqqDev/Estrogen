package dev.mayaqq.estrogen.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EstrogenConfig {
    public static final EstrogenConfig INSTANCE = new EstrogenConfig();

    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("estrogen.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean enableNoChestWithEars = true;

    public void save() {
        try {
            Files.deleteIfExists(configFile);

            JsonObject json = new JsonObject();
            json.addProperty("enableNoChestWithEars", enableNoChestWithEars);

            Files.writeString(configFile, gson.toJson(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (Files.notExists(configFile)) {
                save();
                return;
            }

            JsonObject json = gson.fromJson(Files.readString(configFile), JsonObject.class);

            if (json.has("enableNoChestWithEars"))
                enableNoChestWithEars = json.getAsJsonPrimitive("enableNoChestWithEars").getAsBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("Estrogen"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("General"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.of("Enable Chest Dispersal with Ears"))
                                .binding(
                                        true,
                                        () -> enableNoChestWithEars,
                                        value -> enableNoChestWithEars = value
                                )
                                .tooltip(Text.of("If you have Chest enabled in the Ears mod, it will make it disappear until you get the Effect!"))
                                .controller(TickBoxController::new)
                                .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }
}
