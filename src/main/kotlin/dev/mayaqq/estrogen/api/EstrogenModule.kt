@file:Suppress("RedundantVisibilityModifier")

package dev.mayaqq.estrogen.api

import net.minecraft.client.gui.screens.Screen

public abstract class EstrogenModule {
    public abstract fun <S : Screen> createConfigScreen(parent: Screen): S

    public abstract val flags: Array<EstrogenFlag>

    public fun hasFlag(flag: EstrogenFlag): Boolean {
        return flags.contains(flag) || flags.any { it.inheritedFlags.contains(flag) }
    }
}