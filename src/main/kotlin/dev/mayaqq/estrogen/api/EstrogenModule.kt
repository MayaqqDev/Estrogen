@file:Suppress("RedundantVisibilityModifier")

package dev.mayaqq.estrogen.api

import net.minecraft.client.gui.screens.Screen

public abstract class EstrogenModule {
    public abstract fun <S : Screen> createConfigScreen(parent: Screen): S
}