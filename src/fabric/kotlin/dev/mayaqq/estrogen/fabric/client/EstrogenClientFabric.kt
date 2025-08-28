@file:JvmName("EstrogenClientFabric")
package dev.mayaqq.estrogen.fabric.client

import dev.mayaqq.estrogen.client.estrogenClient
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin
import dev.mayaqq.estrogen.fabric.client.models.EstrogenFabricModels

fun init() {
    estrogenClient()
    PreparableModelLoadingPlugin.register(EstrogenFabricModels, EstrogenFabricModels)
}