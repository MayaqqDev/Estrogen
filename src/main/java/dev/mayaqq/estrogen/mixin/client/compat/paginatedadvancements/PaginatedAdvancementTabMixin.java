package dev.mayaqq.estrogen.mixin.client.compat.paginatedadvancements;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.mayaqq.estrogen.client.content.EstrogenRenderer;
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementTabType;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "de.dafuqs.paginatedadvancements.client.PaginatedAdvancementTab")
abstract class PaginatedAdvancementTabMixin extends AdvancementTab {

    public PaginatedAdvancementTabMixin(Minecraft minecraft, AdvancementsScreen advancementsScreen, AdvancementTabType advancementTabType, int i, AdvancementNode advancement, DisplayInfo displayInfo) {
        super(minecraft, advancementsScreen, advancementTabType, i, advancement, displayInfo);
    }

    @WrapOperation(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V")
    )
    private void shaderBG(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
        if (this.getDisplay().getBackground().isPresent() && this.getDisplay().getBackground().get().toString().equals("estrogen:textures/block/dream_block/particle.png")) {
            DynamicDreamTexture.prepareIfNeeded();
            DynamicDreamTexture.generateIfNeeded();
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
        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), minX, minY);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), minX, maxY);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), maxX, maxY);
        estrogen$vertex(bufferBuilder, graphics.pose().last().pose(), maxX, minY);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    }

    @Unique
    private void estrogen$vertex(BufferBuilder bufferBuilder, Matrix4f pose, int x, int y) {
        bufferBuilder.addVertex(pose, (float) x, (float) y, 0f)
                .setColor(0, 0, 0, 0)
                .setUv((float) x, (float) y)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(0f, 0f, 0f);
    }
}
