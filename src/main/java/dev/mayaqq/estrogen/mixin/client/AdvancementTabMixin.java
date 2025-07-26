package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.mayaqq.estrogen.client.content.EstrogenRenderer;
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(AdvancementTab.class)
public class AdvancementTabMixin {
    @Shadow @Final private DisplayInfo display;

    @WrapOperation(
            method = "drawContents",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V")
    )
    private void shaderBG(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
        if (this.display.getBackground() != null && this.display.getBackground().toString().equals("estrogen:textures/block/dream_block/particle.png")) {
            estrogen$renderDream(instance, x, x + width, y, y + height);
        } else {
            original.call(instance, atlasLocation, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
        }
    }

    @Unique
    private void estrogen$renderDream(GuiGraphics graphics, int minX, int maxX, int minY, int maxY) {
        // probably jank; i wouldnt know, im not a renderologist
        RenderSystem.setShaderTexture(0, DynamicDreamTexture.INSTANCE.getID());
        RenderSystem.setShader(EstrogenRenderer.INSTANCE::getDreamBlockShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), minX, minY);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), minX, maxY);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), maxX, maxY);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), maxX, minY);
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    @Unique
    private void estrogen$vertex(BufferBuilder bufferBuilder, Matrix4f pose, int x, int y) {
        bufferBuilder.vertex(pose, (float) x, (float) y, 0f)
                .color(0, 0, 0, 0)
                .uv((float) x, (float) y)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(0f, 0f, 0f)
                .endVertex();
    }
}
