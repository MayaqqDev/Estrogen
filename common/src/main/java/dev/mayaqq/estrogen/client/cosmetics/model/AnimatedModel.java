package dev.mayaqq.estrogen.client.cosmetics.model;

import dev.mayaqq.estrogen.client.cosmetics.model.mesh.HierarchicalMesh;
import net.minecraft.client.model.HierarchicalModel;
import org.joml.Vector3f;

public class AnimatedModel extends BakedCosmeticModel {
    public AnimatedModel(HierarchicalMesh mesh, Vector3f minBound, Vector3f maxBound) {
        super(mesh, minBound, maxBound);
    }
}
