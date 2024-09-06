package dev.mayaqq.estrogen.client.cosmetics.ui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CosmeticIconWidget extends AbstractSimiWidget {

    public static final PartPose DEFAULT_POSE = PartPose.rotation(30.0f, 225.0f, 0);
    private static final Vector3f LIGHT_0 = new Vector3f(0.2F, -1.0f, -0.5F).normalize();
    private static final Vector3f LIGHT_1 = new Vector3f(-0.8f, -1.0f, 1.0F).normalize();

    private PartPose pose;
    private Cosmetic cosmetic;

    private ContentScaling contentScalingMode = ContentScaling.SCALE_Y;
    private HighlightPredicate hoverPredicate;
    private float scale = 0.5f;
    private float rotationSpeed;

    private final LerpedFloat overlayAnimation = LerpedFloat.linear();
    private boolean isHovered;
    private float animRot;

    public boolean debug;

    public CosmeticIconWidget(Cosmetic cosmetic, int x, int y, int width, int height, @Nullable PartPose referencePose) {
        super(x, y, width, height);
        this.cosmetic = cosmetic;
        this.pose = (referencePose != null) ? referencePose : DEFAULT_POSE;
        this.z = 150;
        defaultHighlightPredicate();
    }

    public static CosmeticIconWidget of(Cosmetic cosmetic) {
        return new CosmeticIconWidget(cosmetic, 0, 0, 0, 0, null);
    }

    @Override
    protected void beforeRender(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.beforeRender(graphics, mouseX, mouseY, partialTicks);

        if(!visible) return;
        boolean hover = hoverPredicate.test(this, mouseX, mouseY);

        if(hover != isHovered) {
            isHovered = hover;
            overlayAnimation.startWithValue(hover ? 0 : 1);
            overlayAnimation.chase(hover ? 1 : 0, 0.6, LerpedFloat.Chaser.EXP);
            overlayAnimation.tickChaser();
        }
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
        matrices.scale(16.0F * scaleX, -16.0F * scaleY, 16.0F * scaleX);

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

        float animation = overlayAnimation.getValue(partialTicks);
        int overlay = (isHovered || !overlayAnimation.settled())
            ? OverlayTexture.pack(animation * 0.5f, false)
            : OverlayTexture.NO_OVERLAY;

        RenderSystem.setShaderLights(LIGHT_0, LIGHT_1);
        cosmetic.render(RenderType::entityTranslucent, graphics.bufferSource(), matrices, LightTexture.FULL_BRIGHT, overlay);
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

    @Override
    public void tick() {
        if(rotationSpeed > 0) {
            this.animRot += rotationSpeed;
            if(animRot >= 360f) animRot = 0;
        }
        overlayAnimation.tickChaser();

    }

    private Quaternionf rotateXYZ(float x, float y, float z) {
        return new Quaternionf().rotateXYZ(x * Mth.DEG_TO_RAD, y * Mth.DEG_TO_RAD, z * Mth.DEG_TO_RAD);
    }

    public CosmeticIconWidget withRotationSpeed(float speed) {
        this.rotationSpeed = speed;
        return this;
    }

    public CosmeticIconWidget withScale(float scale) {
        this.scale = scale;
        return this;
    }

    public CosmeticIconWidget withSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
    public CosmeticIconWidget withContentScaling(ContentScaling mode) {
        this.contentScalingMode = mode;
        return this;
    }

    public CosmeticIconWidget withPose(PartPose pose) {
        this.pose = pose;
        return this;
    }

    public CosmeticIconWidget withHighlightPredicate(HighlightPredicate predicate) {
        this.hoverPredicate = predicate;
        return this;
    }

    public CosmeticIconWidget defaultHighlightPredicate() {
        this.hoverPredicate = HighlightPredicate.DEFAULT;
        return this;
    }

    public CosmeticIconWidget neverHighlight() {
        return this.withHighlightPredicate(($0, $1, $2) -> false);
    }

    public CosmeticIconWidget withDefaultPose() {
        this.pose = DEFAULT_POSE;
        return this;
    }

    public boolean rotateOrScale() {
        return pose.xRot != 0 || pose.yRot != 0 || pose.zRot != 0 || scale != 1.0f || rotationSpeed != 0 || animRot != 0;
    }

    public void setCosmetic(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }

    public enum ContentScaling {
        SCALE_X,
        SCALE_Y,
        SQUISH,
        NONE;
    }

    public interface HighlightPredicate {
        HighlightPredicate DEFAULT = (self, mouseX, mouseY) -> mouseX >= self.getX() && mouseX <= self.getX() + self.width && mouseY >= self.getY() && mouseY <= self.getY() + self.height;

        boolean test(CosmeticIconWidget widget, int mouseX, int mouseY);
    }
}
