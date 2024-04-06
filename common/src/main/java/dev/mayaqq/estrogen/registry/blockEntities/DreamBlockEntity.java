package dev.mayaqq.estrogen.registry.blockEntities;

import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DreamBlockTexture;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DreamBlockEntity extends BlockEntity {
    @Environment(EnvType.CLIENT)
    DreamBlockTexture texture;

    public DreamBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public DreamBlockEntity(BlockPos pos, BlockState state) {
        super(EstrogenBlockEntities.DREAM_BLOCK.get(), pos, state);
    }

    @Environment(EnvType.CLIENT)
    public void setTexture(@Nullable DreamBlockTexture tex) {
        if(tex != null) tex.init();
        this.texture = tex;
    }

    @Environment(EnvType.CLIENT)
    public DreamBlockTexture getTexture() {
        return texture;
    }

    @Environment(EnvType.CLIENT)
    public void updateTexture() {
        if(this.texture != null)
            texture.redraw();
    }
}
