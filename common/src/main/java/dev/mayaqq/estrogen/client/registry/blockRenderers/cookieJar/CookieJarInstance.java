package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import dev.mayaqq.estrogen.utils.client.ItemModelBufferer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public class CookieJarInstance extends BlockEntityInstance<CookieJarBlockEntity> {

    protected final PoseStack poseStack = new PoseStack();
    protected final List<ModelData> instances = new ObjectArrayList<>(8);

    public CookieJarInstance(MaterialManager materialManager, CookieJarBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        poseStack.translate(0.5, 0.35, -0.032F);

        for (ItemStack jarItem : blockEntity.getItems()) {
            if (jarItem.isEmpty()) {
                continue;
            }
            poseStack.translate(0.025, 0.025, -0.032F);
            createItemInstance(jarItem);
            poseStack.translate(-0.05, -0.05, -0.032F);
            createItemInstance(jarItem);
            poseStack.translate(0.025, 0.025, 0);
        }
        poseStack.popPose();
    }

    protected void createItemInstance(ItemStack stack) {
        ModelData instance = getTransformMaterial()
            .model(stack.getItem(), () -> ItemModelBufferer.getModel(world, stack, ItemDisplayContext.GROUND))
            .createInstance();

        instance.translate(this.getInstancePosition()).transform(poseStack);
        this.relight(pos, instance);

        instances.add(instance);
    }

    @Override
    public void update() {
        int expectedInstances = 0;
        for(ItemStack jarItem : blockEntity.getItems()) {
            if(!jarItem.isEmpty()) expectedInstances += 2;
        }

        if(expectedInstances != instances.size()) {
            // Reset the instances if the count changed
            this.remove();
            this.init();
        }
    }

    @Override
    public void updateLight() {
        instances.forEach(instance -> relight(pos, instance));
    }

    @Override
    protected void remove() {
        instances.forEach(ModelData::delete);
        instances.clear();
    }
}
