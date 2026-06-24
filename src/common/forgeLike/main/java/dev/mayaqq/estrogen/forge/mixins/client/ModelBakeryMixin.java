package dev.mayaqq.estrogen.forge.mixins.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.client.EstrogenClientKt;
import dev.mayaqq.estrogen.forge.client.EstrogenForgeClientKt;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBakery.class)
public class ModelBakeryMixin {

    @ModifyExpressionValue(
        method = "loadTopLevel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/ModelBakery;getModel(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/resources/model/UnbakedModel;"
        )
    )
    private UnbakedModel wrapThighHighsItemModel(UnbakedModel original, @Local(argsOnly = true) ModelResourceLocation location) {
        if (location.equals(EstrogenClientKt.THIGH_HIGH_ITEM_LOCATION)) {
            return EstrogenForgeClientKt.modifyThighHighModel(original);
        } else {
            return original;
        }
    }

}
