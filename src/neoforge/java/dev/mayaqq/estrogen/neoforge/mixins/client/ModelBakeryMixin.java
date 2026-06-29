package dev.mayaqq.estrogen.neoforge.mixins.client;

import net.minecraft.client.resources.model.ModelBakery;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ModelBakery.class)
public class ModelBakeryMixin {

    /* TODO: Yeah uh idk uh
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
     */
}
