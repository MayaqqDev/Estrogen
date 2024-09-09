package dev.mayaqq.estrogen.forge.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.mayaqq.estrogen.forge.client.models.ForgeThighHighItemModel;
import dev.mayaqq.estrogen.utils.client.EstrogenClientPaths;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @WrapOperation(
        method = "loadTopLevel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/ModelBakery;getModel(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/resources/model/UnbakedModel;"
        )
    )
    public UnbakedModel wrapModel(ModelBakery instance, ResourceLocation modelLocation, Operation<UnbakedModel> original) {
        if(modelLocation.equals(EstrogenClientPaths.THIGH_HIGH_ITEM_LOCATION)) {
            return new ForgeThighHighItemModel(original.call(instance, modelLocation));
        }
        return original.call(instance, modelLocation);
    }


}
