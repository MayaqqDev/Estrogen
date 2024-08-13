package dev.mayaqq.estrogen.fabric.biome;

import dev.mayaqq.estrogen.registry.EstrogenEntities;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;

public class EstrogenBiomeModifications {
    public static void addBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(EstrogenTags.Biomes.SPAWNS_MOTH), MobCategory.CREATURE, EstrogenEntities.MOTH.get(), 25, 1, 3);
    }
}
