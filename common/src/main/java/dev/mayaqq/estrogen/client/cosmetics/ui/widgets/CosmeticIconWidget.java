package dev.mayaqq.estrogen.client.cosmetics.ui.widgets;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CosmeticIconWidget extends AbstractSimiWidget {

    public static final PartPose DEFAULT_POSE = PartPose.rotation(30.0f, 225.0f, 0);

    private final PartPose pose;

    private Cosmetic cosmetic;
    private float scale = 0.5f;
    private float rotationSpeed;
    private float animRot;
    private ContentScaling contentScalingMode = ContentScaling.SCALE_Y;

    public boolean debug;

    public CosmeticIconWidget(Cosmetic cosmetic, int x, int y, int width, int height, @Nullable PartPose referencePose) {
        super(x, y, width, height);
        this.cosmetic = cosmetic;
        this.pose = (referencePose != null) ? referencePose : DEFAULT_POSE;
        this.z = 150;
    }

    @Override
    protected void doRender(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.translate(this.getX(), this.getY() + 16, z);

        Vector3f modelSize = cosmetic.model().maxBound().sub(cosmetic.model().minBound());
        float scaleX, scaleY;

        // Ignore IDEA advice it's dumb
        switch (contentScalingMode) {
            case SCALE_X -> scaleY = scaleX = width / modelSize.x;
            case SCALE_Y -> scaleX = scaleY = height / modelSize.y;
            case SQUISH -> {
                scaleX = width / modelSize.x;
                scaleY = height / modelSize.y;
            }
            default -> scaleX = scaleY = 1f;
        }

        matrices.translate(0f, height - modelSize.y, 0f);
        matrices.scale(16.0F * scaleX, -16.0F * scaleY, 16.0F);

        if(pose.x != 0 || pose.y != 0 || pose.z != 0) {
            matrices.translate(pose.x / 16f, pose.y / 16f, pose.z / 16f);
        }

        if(this.rotateOrScale()) {
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.scale(scale, scale, scale);
            matrices.mulPose(rotateXYZ(pose.xRot, pose.yRot, pose.zRot));
            if(rotationSpeed != 0) {
                matrices.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, animRot, animRot + rotationSpeed)));
            }
            matrices.translate(-0.5f, -0.5f, -0.5f);
        }

        if(debug) {
            // White box is the widgets size
            drawDebugBounds(getX(), getY(), getX() + width, getY() + height, 0xFFFFFFFF);
            // Red box is the model's size
            drawDebugBounds(getX(), getY(), getX() + modelSize.x, getY() + modelSize.y, 0xFFFF0000);
        }

        Lighting.setupForEntityInInventory();
        cosmetic.render(RenderType::entityTranslucent, graphics.bufferSource(), matrices, LightTexture.FULL_BRIGHT);
        graphics.flush();

    }

    private void drawDebugBounds(float minX, float minY, float maxX, float maxY, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        builder.vertex(minX, minY, 0).color(color).endVertex();
        builder.vertex(minX, maxY, 0).color(color).endVertex();
        builder.vertex(maxX, maxY, 0).color(color).endVertex();
        builder.vertex(maxX, minY, 0).color(color).endVertex();

        Tesselator.getInstance().end();
        RenderSystem.disableBlend();
    }

    // TODO: Actually properly animate this
    @Override
    public void tick() {
        if(rotationSpeed > 0) {
            this.animRot += rotationSpeed;
            if(animRot >= 360f) animRot = 0;
        }
    }

    private Quaternionf rotateXYZ(float x, float y, float z) {
        return new Quaternionf().rotateXYZ(x * Mth.DEG_TO_RAD, y * Mth.DEG_TO_RAD, z * Mth.DEG_TO_RAD);
    }

    public CosmeticIconWidget setRotationSpeed(float speed) {
        this.rotationSpeed = speed;
        return this;
    }

    public CosmeticIconWidget setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public boolean rotateOrScale() {
        return pose.xRot != 0 || pose.yRot != 0 || pose.zRot != 0 || scale != 1.0f || rotationSpeed != 0 || animRot != 0;
    }

    public void setCosmetic(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }

    public void setContentScalingMode(ContentScaling mode) {
        this.contentScalingMode = mode;
    }

    public enum ContentScaling {
        SCALE_X,
        SCALE_Y,
        SQUISH,
        NONE;
    }
}
