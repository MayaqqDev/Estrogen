package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel;

import com.jozufozu.flywheel.core.materials.BasicData;
import net.minecraft.core.BlockPos;
import org.joml.Vector3f;

public class DreamData extends BasicData {

    public float posX;
    public float posY;
    public float posZ;

    public DreamData setPosition(BlockPos pos) {
        return setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    public DreamData setPosition(Vector3f pos) {
        return setPosition(pos.x(), pos.y(), pos.z());
    }

    public DreamData setPosition(float x, float y, float z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        markDirty();
        return this;
    }

}
