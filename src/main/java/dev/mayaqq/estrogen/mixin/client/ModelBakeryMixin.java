package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.EstrogenKt;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Unique
    private static final ModelResourceLocation estrogen$crossbow_model = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(EstrogenKt.MOD_ID, "gender_change_crossbow"));

    @Shadow
    protected abstract void loadSpecialItemModelAndDependencies(ModelResourceLocation modelLocation);

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/model/ModelBakery;loadSpecialItemModelAndDependencies(Lnet/minecraft/client/resources/model/ModelResourceLocation;)V",
                    ordinal = 0
            )
    )
    private void modifyModel(BlockColors par1, ProfilerFiller par2, Map par3, Map par4, CallbackInfo ci) {
        loadSpecialItemModelAndDependencies(estrogen$crossbow_model);
    }
}
