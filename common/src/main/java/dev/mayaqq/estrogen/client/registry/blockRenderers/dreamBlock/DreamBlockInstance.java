package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.api.instance.TickableInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.mojang.blaze3d.vertex.*;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderTypes;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamData;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamType;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.vertex.BasicModel;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DreamBlockTexture;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DynamicDreamTexture;
import dev.mayaqq.estrogen.platform.CommonPlatform;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import earth.terrarium.botarium.client.ClientHooks;
import earth.terrarium.botarium.util.CommonHooks;
import it.unimi.dsi.fastutil.booleans.BooleanSet;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.BitSet;

public class DreamBlockInstance extends BlockEntityInstance<DreamBlockEntity> {

    protected DreamData data;

    public DreamBlockInstance(MaterialManager materialManager, DreamBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        data = materialManager.transparent(DynamicDreamTexture.INSTANCE.getRenderType())
            .material(EstrogenRenderer.DREAM)
            .model(blockState, this::buildModel)
            .createInstance();

        data.setPosition(this.getInstancePosition())
            .setColor(0xFFFFFFFF)
            .setBlockLight(255)
            .setSkyLight(255);

        DynamicDreamTexture.addActive();
    }

    protected Model buildModel() {
        //BasicModel.Builder builder = BasicModel.builder("dream_block");
        BufferBuilder builder = new BufferBuilder(6 * 4 * DefaultVertexFormat.BLOCK.getVertexSize());

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        this.face(builder, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        this.face(builder, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        this.face(builder, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        this.face(builder, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        this.face(builder, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        this.face(builder, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);

        BufferBuilder.RenderedBuffer buffer = builder.end();
        return new BlockModel(buffer.vertexBuffer(), buffer.indexBuffer(), buffer.drawState(), 0, "dream_block");
    }

    private void face(VertexConsumer builder, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction direction) {
        //BasicModel.Quad innerQuad = builder.quad();
        addInnerVertex(builder, x0, y0, z0);
        addInnerVertex(builder, x1, y0, z1);
        addInnerVertex(builder, x1, y1, z2);
        addInnerVertex(builder, x0, y1, z3);
        //innerQuad.end();

        if(!blockEntity.isTouchingDreamBlock(direction)) {
            //BasicModel.Quad outerQuad = builder.quad();
            addOuterVertex(builder, x0, y1, z3);
            addOuterVertex(builder, x1, y1, z2);
            addOuterVertex(builder, x1, y0, z1);
            addOuterVertex(builder, x0, y0, z0);
            //outerQuad.end();
        }
    }

    /**
     * Vertices for the inner faces, which will have the shader applied.
     * Vertices are moved when there are neighboring dream blocks, so that their interiors connect.
     */
    private void addInnerVertex(VertexConsumer builder, float x, float y, float z) {
        // ternary nightmare
        float x2 = blockEntity.isTouchingDreamBlock(x > 0.5 ? Direction.EAST : Direction.WEST) ? x : x * 7f/8f + 1f/16f;
        float y2 = blockEntity.isTouchingDreamBlock(y > 0.5 ? Direction.UP : Direction.DOWN) ? y : y * 7f/8f + 1f/16f;
        float z2 = blockEntity.isTouchingDreamBlock(z > 0.5 ? Direction.SOUTH : Direction.NORTH) ? z : z * 7f/8f + 1f/16f;

        addVertex(builder, x2, y2, z2, false);
    }

    /**
     * Workaround to changing canOcclude() via config
     */
    private void addOuterVertex(VertexConsumer builder, float x, float y, float z) {
        float x2 = x * 0.999f + 0.0005f;
        float y2 = y * 0.999f + 0.0005f;
        float z2 = z * 0.999f + 0.0005f;

        addVertex(builder, x2, y2, z2, true);
    }

    private void addVertex(VertexConsumer builder, float x, float y, float z, boolean isBorder) {
        builder.vertex(x, y, z).color(0xffffffff).uv(0, 0)
            .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT);
        if (isBorder) {
            builder.normal(1, 1, 1);
        } else {
            // Using normal to detect border here
            builder.normal(0, 0, 0);
        }
        builder.endVertex();
    }

    @Override
    protected void remove() {
        DynamicDreamTexture.removeActive();
        data.delete();
    }
}
