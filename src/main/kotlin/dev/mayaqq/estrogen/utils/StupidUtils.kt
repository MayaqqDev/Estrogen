package dev.mayaqq.estrogen.utils

import net.minecraft.core.Holder

inline fun <T : Any> T.holder() = Holder.direct(this)