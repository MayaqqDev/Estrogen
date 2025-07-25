package dev.mayaqq.estrogen.utils

import dev.mayaqq.cynosure.core.Loader
import dev.mayaqq.cynosure.core.currentLoader
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class EstrogenMixinPlugin : IMixinConfigPlugin {

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        if (mixinClassName.contains(".forge.")) {
            return currentLoader == Loader.FORGE
        } else if (mixinClassName.contains(".fabric.")) {
            return currentLoader == Loader.FABRIC
        }
        return true
    }

    override fun onLoad(mixinPackage: String) {}
    override fun getRefMapperConfig(): String? = null
    override fun acceptTargets(myTargets: MutableSet<String?>?, otherTargets: MutableSet<String?>?) {}
    override fun getMixins(): MutableList<String>? = null
    override fun preApply(targetClassName: String?, targetClass: ClassNode?, mixinClassName: String?, mixinInfo: IMixinInfo?) {}
    override fun postApply(targetClassName: String?, targetClass: ClassNode?, mixinClassName: String?, mixinInfo: IMixinInfo?) {}
}