package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.api.instance.TickableInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.mojang.blaze3d.vertex.*;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamData;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamType;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.vertex.BasicModel;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import it.unimi.dsi.fastutil.booleans.BooleanSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.BitSet;

public class DreamBlockInstance extends BlockEntityInstance<DreamBlockEntity> {

    protected DreamData data;

    public DreamBlockInstance(MaterialManager materialManager, DreamBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        ResourceLocation dream_block = Estrogen.id("textures/dream.png");
        data = materialManager.transparent(RenderType.entityTranslucentCull(dream_block))
            .material(DreamType.INSTANCE)
            .model(blockState, this::buildModel)
            .createInstance();

        data.setPosition(this.getInstancePosition());
    }

    protected Model buildModel() {
        BasicModel.Builder builder = BasicModel.builder("dream_block");

        this.face(builder, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        this.face(builder, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        this.face(builder, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        this.face(builder, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        this.face(builder, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        this.face(builder, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);

        return builder.build();
    }

    private void face(BasicModel.Builder builder, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction direction) {
        BasicModel.Quad innerQuad = builder.quad();
        addInnerVertex(innerQuad, x0, y0, z0);
        addInnerVertex(innerQuad, x1, y0, z1);
        addInnerVertex(innerQuad, x1, y1, z2);
        addInnerVertex(innerQuad, x0, y1, z3);
        innerQuad.end();

        if(!blockEntity.isTouchingDreamBlock(direction)) {
            BasicModel.Quad outerQuad = builder.quad();
            addOuterVertex(outerQuad, x0, y1, z3);
            addOuterVertex(outerQuad, x1, y1, z2);
            addOuterVertex(outerQuad, x1, y0, z1);
            addOuterVertex(outerQuad, x0, y0, z0);
            outerQuad.end();
        }
    }

    /**
     * Vertices for the inner faces, which will have the shader applied.
     * Vertices are moved when there are neighboring dream blocks, so that their interiors connect.
     */
    private void addInnerVertex(BasicModel.Quad builder, float x, float y, float z) {
        // ternary nightmare
        float x2 = blockEntity.isTouchingDreamBlock(x > 0.5 ? Direction.EAST : Direction.WEST) ? x : x * 7f/8f + 1f/16f;
        float y2 = blockEntity.isTouchingDreamBlock(y > 0.5 ? Direction.UP : Direction.DOWN) ? y : y * 7f/8f + 1f/16f;
        float z2 = blockEntity.isTouchingDreamBlock(z > 0.5 ? Direction.SOUTH : Direction.NORTH) ? z : z * 7f/8f + 1f/16f;

        addVertex(builder, x2, y2, z2, false);
    }

    /**
     * Workaround to changing canOcclude() via config
     */
    private void addOuterVertex(BasicModel.Quad builder, float x, float y, float z) {
        float x2 = x * 0.999f + 0.0005f;
        float y2 = y * 0.999f + 0.0005f;
        float z2 = z * 0.999f + 0.0005f;

        addVertex(builder, x2, y2, z2, true);
    }

    private void addVertex(BasicModel.Quad builder, float x, float y, float z, boolean isBorder) {
        builder.pos(x, y, z).uv(0, 0);
        if (isBorder) {
            builder.normal(1, 1, 1);
        } else {
            // Using normal to detect border here
            builder.normal(0, 0, 0);
        }
        builder.next();
    }

    @Override
    protected void remove() {
        data.delete();
    }

    @Override
    public void updateLight() {
        relight(this.getInstancePosition(), data);
    }
}
