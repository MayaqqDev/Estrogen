package dev.mayaqq.estrogen.forge.client;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.command.EstrogenClientCommands;
import dev.mayaqq.estrogen.client.features.dash.DashOverlay;
import dev.mayaqq.estrogen.client.registry.EstrogenClientEvents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID, value = Dist.CLIENT)
public class EstrogenForgeClientEvents {
    @SubscribeEvent
    public static void onGuiRenderEvent(RenderGuiEvent event) {
        DashOverlay.drawOverlay(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        EstrogenClientEvents.onDisconnect();
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        EstrogenClientEvents.onRegisterParticles((particle, provider) -> event.registerSpriteSet(particle, provider::create));
    }

    @SubscribeEvent
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        EstrogenClientCommands.register(event.getDispatcher(), new ForgeClientCommandManager());
    }

    private static class ForgeClientCommandManager implements EstrogenClientCommands.ClientCommandManager<CommandSourceStack> {

        @Override
        public LiteralArgumentBuilder<CommandSourceStack> literal(String name) {
            return Commands.literal(name);
        }

        @Override
        public <I> RequiredArgumentBuilder<CommandSourceStack, I> argument(String name, ArgumentType<I> type) {
            return Commands.argument(name, type);
        }

        @Override
        public boolean hasPermission(CommandSourceStack source, int permissionLevel) {
            return source.hasPermission(permissionLevel);
        }

        @Override
        public void sendFailure(CommandSourceStack source, Component component) {
            source.sendFailure(component);
        }
    }
}