package dev.mayaqq.estrogen.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import dev.mayaqq.estrogen.Estrogen

object EstrogenDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fdg: FabricDataGenerator) {
        Estrogen.info("Hello")
    }
}