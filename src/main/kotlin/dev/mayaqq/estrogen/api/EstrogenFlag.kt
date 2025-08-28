package dev.mayaqq.estrogen.api

enum class EstrogenFlag(vararg val inheritedFlags: EstrogenFlag) {
    DISABLES_CAULDRON_ESTROGEN,
    MODIFIES_BASE_ESTROGEN(DISABLES_CAULDRON_ESTROGEN);
}