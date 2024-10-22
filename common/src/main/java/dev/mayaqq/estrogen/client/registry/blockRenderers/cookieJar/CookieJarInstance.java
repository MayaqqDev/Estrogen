package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.utility.Pair;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import dev.mayaqq.estrogen.utils.client.render.ItemModelBufferer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CookieJarInstance extends BlockEntityInstance<CookieJarBlockEntity> {

    protected final PoseStack poseStack = new PoseStack();
    protected final List<ModelData> instances = new ObjectArrayList<>();

    public CookieJarInstance(MaterialManager materialManager, CookieJarBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        BlockPos pos = this.getInstancePosition();
        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
        this.reloadInstances();
    }

    @Override
    public void update() {
        int expectedInstances = 0;
        for(ItemStack jarItem : blockEntity.getItems()) {
            if(!jarItem.isEmpty()) expectedInstances += 2;
        }

        if(expectedInstances != instances.size()) {
           this.reloadInstances();
           this.relight(pos, instances.stream());
        }
    }

    protected void reloadInstances() {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XN.rotationDegrees(90));
        poseStack.translate(0.5, -0.625, 0.032F);

        int index = -2;
        for(ItemStack jarItem : blockEntity.getItems()) {
            index += 2;
            boolean hasInstances = index < instances.size() - 1;

            if (jarItem.isEmpty()) {
                if(hasInstances) {
                    instances.remove(index + 1).delete();
                    instances.remove(index).delete();
                }
                continue;
            }

            poseStack.translate(-0.025, -0.025, 0.032F);
            if(!hasInstances) createItemInstance(jarItem);
            else instances.get(index).loadIdentity().transform(poseStack);
            poseStack.translate(0.05, 0.05, 0.032F);
            if(!hasInstances) createItemInstance(jarItem);
            else instances.get(index + 1).loadIdentity().transform(poseStack);
            poseStack.translate(-0.025, -0.025, 0);
        }

        poseStack.popPose();
    }

    protected void createItemInstance(ItemStack stack) {
        ModelData instance = getTransformMaterial()
            .model(Pair.of(stack.getItem(), ItemDisplayContext.GROUND),
                () -> ItemModelBufferer.bufferModel(world, stack, ItemDisplayContext.GROUND))
            .createInstance();

        instance.transform(poseStack);
        instances.add(instance);
    }

    @Override
    public void updateLight() {
        relight(pos, instances.stream());
    }

    @Override
    protected void remove() {
        instances.forEach(ModelData::delete);
        instances.clear();
    }
}
