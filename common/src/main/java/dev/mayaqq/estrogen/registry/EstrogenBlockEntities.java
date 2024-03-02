package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeCogInstance;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeRenderer;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class EstrogenBlockEntities {

    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITIES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Estrogen.MOD_ID);

    public static final RegistryEntry<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE = BLOCK_ENTITIES.register(
            "centrifuge",
            () -> RegistryHelpers.createBlockEntityType(
                    CentrifugeBlockEntity::new,
                    EstrogenBlocks.CENTRIFUGE.get()
            )
    );
}
