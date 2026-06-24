package dev.mayaqq.estrogen.utils.exceptions

import net.minecraft.tags.TagKey

class EmptyTagException(tag: TagKey<*>) : Exception("Tag key with id \"${tag.location()}\" is empty even though it is expected to have at least one entry")