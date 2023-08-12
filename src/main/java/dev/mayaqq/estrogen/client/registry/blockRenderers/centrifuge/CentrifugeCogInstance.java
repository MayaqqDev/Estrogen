package dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;

public class CentrifugeCogInstance extends SingleRotatingInstance<CentrifugeBlockEntity> {

    public CentrifugeCogInstance(MaterialManager materialManager, CentrifugeBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(EstrogenRenderer.CENTRIFUGE_COG, blockEntity.getCachedState());
    }
}
