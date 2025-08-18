package dev.mayaqq.estrogen.modules

import dev.mayaqq.cynosure.core.mod.Mod
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.api.EstrogenEntrypoint
import dev.mayaqq.estrogen.api.EstrogenModule
import net.minecraftforge.fml.ModList
import org.objectweb.asm.Type
import kotlin.jvm.optionals.getOrNull

actual fun getModules(): Set<ModuleContainer> {
    val list = arrayListOf<ModuleContainer>()
    val type = Type.getType(EstrogenEntrypoint::class.java)
    ModList.get().allScanData.forEach { scan ->
        scan.annotations.forEach { annot ->
            try {
                if (annot.annotationType.equals(type)) {
                    val clazz = Class.forName(annot.memberName)
                    if (EstrogenModule::class.java.isAssignableFrom(clazz)) {
                        val pluginClass = clazz.asSubclass(EstrogenModule::class.java)
                        val plugin = pluginClass.getConstructor().newInstance()
                        val data = scan.iModInfoData[0].mods[0]
                        list.add(ModuleContainer(
                            plugin,
                            Mod(
                                data.modId,
                                data.displayName,
                                data.description,
                                data.version.toString(),
                                data.logoFile.getOrNull()
                            )
                        ))
                    }
                }
            } catch (t: Throwable) {
                Estrogen.error("Critical exception thrown when loading Estrogen Module: ${scan.iModInfoData[0].mods[0].modId}", t)
            }
        }
    }
    return list.toSet()
}