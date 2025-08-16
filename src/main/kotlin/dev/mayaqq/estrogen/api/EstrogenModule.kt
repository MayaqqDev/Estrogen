@file:Suppress("RedundantVisibilityModifier")

package dev.mayaqq.estrogen.api

import dev.mayaqq.cynosure.utils.colors.Color
import net.minecraft.client.gui.screens.Screen

public interface EstrogenModule {
    public fun createConfigScreen(): (Screen) -> Screen

    public val flags: Array<EstrogenFlag>

    public val color: Color

    public fun hasFlag(flag: EstrogenFlag): Boolean {
        return flags.contains(flag) || flags.any { it.inheritedFlags.contains(flag) }
    }
}