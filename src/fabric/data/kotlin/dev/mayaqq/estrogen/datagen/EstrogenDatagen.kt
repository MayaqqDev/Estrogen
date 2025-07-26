package dev.mayaqq.estrogen.datagen

import dev.mayaqq.estrogen.Estrogen
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object EstrogenDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fdg: FabricDataGenerator) {
        Estrogen.info("Hello")
    }
}