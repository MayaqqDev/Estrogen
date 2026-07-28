package dev.mayaqq.estrogen.utils

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.estrogen.Estrogen
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class EstrogenMixinPlugin : IMixinConfigPlugin {

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        if (targetClassName.contains(".compat.")) {
            val path = targetClassName.split(".compat.")[1]
            val mod = path.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().first()
            return isModLoaded(mod)
        }
        return true
    }

    override fun getRefMapperConfig(): String? = null
    override fun acceptTargets(my: Set<String>, others: Set<String>) {}
    override fun getMixins(): List<String>? = null
    override fun onLoad(mixinPackage: String) {
        if (!eventsSubscribed) {
            Estrogen.hookEarlyEvents()
            eventsSubscribed = true
        }
    }
    override fun preApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {}
    override fun postApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {}

    companion object {
        var eventsSubscribed = false
    }
}