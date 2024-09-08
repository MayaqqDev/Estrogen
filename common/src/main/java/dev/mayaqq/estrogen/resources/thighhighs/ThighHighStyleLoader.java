package dev.mayaqq.estrogen.resources.thighhighs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ThighHighStyleLoader implements ResourceManagerReloadListener {

    public static final ThighHighStyleLoader INSTANCE = new ThighHighStyleLoader();

    public static final Codec<List<ResourceLocation>> STYLES_CODEC = Codec.list(ResourceLocation.CODEC);

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        final List<ResourceLocation> styles = new ArrayList<>();
        resourceManager.getResourceStack(Estrogen.id("thigh_high_styles.json")).forEach(resource -> {
            try(Reader reader = resource.openAsReader()) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                JsonElement replaceElement = root.get("replace");

                boolean replace = replaceElement.isJsonPrimitive() ? replaceElement.getAsBoolean() : false;
                if(replace) styles.clear();

                List<ResourceLocation> added = STYLES_CODEC.decode(JsonOps.INSTANCE, root.get("values"))
                    .resultOrPartial(Estrogen.LOGGER::error)
                    .map(Pair::getFirst)
                    .orElseGet(List::of);

                styles.addAll(added);
            } catch (IOException | JsonParseException e) {
                Estrogen.LOGGER.error("Invalid thigh high styles:", e);
            }
        });
        EstrogenItems.THIGH_HIGHS.get().loadStyles(styles);
    }
}
