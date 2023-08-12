package dev.mayaqq.estrogen.threeamedition;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;

public class DataInventory extends SmartInventory {

    private final CentrifugeBlockEntity be;

    public DataInventory(int slots, SyncedBlockEntity be) {
        super(slots, be);
        this.be = (CentrifugeBlockEntity) be;
    }

    public CentrifugeBlockEntity getCentrifuge() {
        return be;
    }
}