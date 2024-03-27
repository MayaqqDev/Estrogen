package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EstrogenBlockEntities {

    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITIES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Estrogen.MOD_ID);

    public static final RegistryEntry<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE = BLOCK_ENTITIES.register(
            "centrifuge",
            () -> RegistryHelpers.createBlockEntityType(
                    CentrifugeBlockEntity::new,
                    EstrogenBlocks.CENTRIFUGE.get()
            )
    );

    public static final RegistryEntry<BlockEntityType<CookieJarBlockEntity>> COOKIE_JAR = BLOCK_ENTITIES.register(
            "cookie_jar",
            () -> RegistryHelpers.createBlockEntityType(
                    CookieJarBlockEntity::new,
                    EstrogenBlocks.COOKIE_JAR.get()
            )
    );

    public static final RegistryEntry<BlockEntityType<DreamBlockEntity>> DREAM_BLOCK = BLOCK_ENTITIES.register(
            "dream_block",
            () -> RegistryHelpers.createBlockEntityType(
                    DreamBlockEntity::new,
                    EstrogenBlocks.DREAM_BLOCK.get()
            )
    );
}
