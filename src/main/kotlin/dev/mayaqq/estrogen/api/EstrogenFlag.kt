package dev.mayaqq.estrogen.api

enum class EstrogenFlag(val inheritedFlags: Array<EstrogenFlag>) {
    DISABLES_CAULDRON_ESTROGEN,
    MODIFIES_BASE_ESTROGEN(arrayOf(DISABLES_CAULDRON_ESTROGEN));

    constructor(): this(arrayOf())
}