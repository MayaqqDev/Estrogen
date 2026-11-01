package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.EstrogenKt;
import dev.mayaqq.estrogen.content.EstrogenItems;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @Unique
    private static final ModelResourceLocation estrogen$crossbow_model = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(EstrogenKt.MOD_ID, "gender_change_crossbow"));

    @ModifyExpressionValue(
            method = "getModel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;"
            )
    )
    private BakedModel modifyModel(BakedModel original, @Local ItemStack stack) {
        if (stack.is(Items.CROSSBOW) && stack.has(DataComponents.CHARGED_PROJECTILES)) {
            if (CrossbowItem.isCharged(stack) && stack.get(DataComponents.CHARGED_PROJECTILES).contains(EstrogenItems.INSTANCE.getGenderChangeArrow().get())) {
                return itemModelShaper.getModelManager().getModel(estrogen$crossbow_model);
            }
        }
        return original;
    }
}
