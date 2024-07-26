package dev.mayaqq.estrogen.client.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Pair;
import dev.mayaqq.estrogen.resources.BreastArmorDataLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class EstrogenCommand {
    public static <T> int dataFormatDump(CommandContext<T> context) {
        Player player = Minecraft.getInstance().player;
        player.sendSystemMessage(Component.literal("Dumping data format..."));
        BreastArmorDataLoader.INSTANCE.data.forEach((key, value) -> {
            ResourceLocation texture = value.textureLocation();
            Pair<Integer, Integer> uv = value.uv();
            player.sendSystemMessage(Component.literal("Texture: " + texture + ", UV: " + uv.getFirst() + ", " + uv.getSecond()));
        });

        return 1;
    }
}
