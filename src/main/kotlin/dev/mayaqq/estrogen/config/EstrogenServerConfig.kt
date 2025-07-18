package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
//import uwu.serenity.kittyconfig.api.Comment
//import uwu.serenity.kittyconfig.api.Config
//import uwu.serenity.kittyconfig.api.CustomName
//import uwu.serenity.kittyconfig.api.validation.Range

//@Config(id = "$MOD_ID/server", side = Config.Side.SERVER)
object EstrogenServerConfig {

    object Patch {

        //@Comment("The level of girl power you get from estrogen patches")
        var girlPowerLevel: /*@Range(1, 255)*/ Int = 0

        //@Comment("Enable the estrogen patch to drain")
        var drain: Boolean = true

        //@Comment("The amount of ticks it takes for the estrogen patches to drain a millibucket")
        var drainSpeed: /*@Range(min = 0)*/ Int = 144

    }

    //@Comment("Settings for the dream block")
    object DreamBlock {

        //@Comment("Horizontal range from a dream block in which the dreaming effect can be applied to sleeping players")
        var dreamingEffectRange: Int = 20

        //@Comment("Chance for a random tick to cause the dreaming effect to be applied in percent")
        var dreamingTickChance: /*@Range(0, 100)*/ Int = 100

        //@Comment("Range from a dream catcher for a player to be to block the Dreaming Effect")
        var dreamCatcherRange: Int = 10
    }

    //@Comment("Settings which are more fun and not fit for survival")
    object Minigame {

        //@Comment("Enable/Disable all minigame settings")
        var enabled: Boolean = false

        //@Comment("Gives you permanent, unremovable Girl Power effect")
        var permaDash: Boolean = false

        //@Comment("The level of Girl Power Effect when Perma-Dash is enabled")
        //@CustomName("permaDashLevel")
        var girlPowerLevel: /*@Range(0, 255)*/ Int = 1

    }

}