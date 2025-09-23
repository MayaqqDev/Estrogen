package dev.mayaqq.estrogen.compat.recipeviewers.api

import dev.mayaqq.estrogen.Estrogen
import net.minecraftforge.fml.ModList
import org.objectweb.asm.Type

actual fun getCRVPlugins(): List<PluginContainer> = buildList {
    val type = Type.getType(CRVPluginEntrypoint::class.java)
    ModList.get().allScanData.forEach { scan ->
        scan.annotations.forEach { annot ->
            try {
                if (annot.annotationType.equals(type)) {
                    val clazz = Class.forName(annot.memberName)
                    if (CRVPlugin::class.java.isAssignableFrom(clazz)) {
                        val pluginClass = clazz.asSubclass(CRVPlugin::class.java)
                        val plugin = pluginClass.kotlin.objectInstance?: pluginClass.getConstructor().newInstance()
                        val data = scan.iModInfoData[0].mods[0]
                        add(
                            PluginContainer(
                                plugin,
                                data.modId
                            )
                        )
                    }
                }
            } catch (t: Throwable) {
                Estrogen.error("Critical exception thrown when loading Estrogen Module: ${scan.iModInfoData[0].mods[0].modId}", t)
            }
        }
    }
}