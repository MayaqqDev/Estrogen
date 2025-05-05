package dev.mayaqq.estrogen.mixin.client.accessor;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {

    @Invoker("setSectionDirty")
    void invokeSetSetionDirty(int i, int j, int k);
}
