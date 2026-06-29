package dev.mayaqq.estrogen.utils

import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import net.minecraft.core.Holder
import net.minecraft.world.item.Item

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <E, T : RegistryEntry<E>> T.holder() = this.holder as Holder<E>

@Suppress("NOTHING_TO_INLINE")
inline fun RegistryEntry<Item>.defaultInstance() = this.value!!.defaultInstance