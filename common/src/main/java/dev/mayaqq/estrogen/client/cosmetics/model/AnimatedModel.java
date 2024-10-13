package dev.mayaqq.estrogen.client.cosmetics.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animatable;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.AnimationDefinition;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animations;
import dev.mayaqq.estrogen.client.cosmetics.model.mesh.HierarchicalMesh;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.AnimationState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AnimatedModel extends BakedCosmeticModel implements Animatable.Provider {

    private final List<AnimationDefinition> animations = new ArrayList<>();

    private long accumulatedTime;
    private float scale = 1f;
    private Vector3f vecCache = new Vector3f();

    public AnimatedModel(HierarchicalMesh mesh, Vector3f minBound, Vector3f maxBound) {
        super(mesh, minBound, maxBound);
    }


    @Override
    public void renderInto(VertexConsumer consumer, @NotNull PoseStack transform, int color, int light, int overlay) {
        HierarchicalMesh mesh = (HierarchicalMesh) this.mesh;
        mesh.reset();

        for(AnimationDefinition definition : animations) {
            Animations.animate(this, definition, accumulatedTime, scale, vecCache);
        }

        mesh.renderInto(consumer, transform, color, light, overlay);
    }

    public void tick() {
        if(accumulatedTime == 200000) accumulatedTime = 0;
        accumulatedTime++;
    }

    @Override
    public Optional<Animatable> getAny(String key) {
        return ((HierarchicalMesh) this.mesh).getAny(key);
    }

    public record ModelAnimation(AnimationDefinition definition, AnimationState state) {

        public ModelAnimation(AnimationDefinition definition, boolean alwaysRun) {
            this(definition, new AnimationState());
            state.startIfStopped((int) Minecraft.getInstance().getFrameTime());
        }
    }
}
