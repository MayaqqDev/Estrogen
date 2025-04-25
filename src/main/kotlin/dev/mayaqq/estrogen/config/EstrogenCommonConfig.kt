package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import uwu.serenity.kittyconfig.api.Comment
import uwu.serenity.kittyconfig.api.Config
import uwu.serenity.kittyconfig.api.CustomName
import uwu.serenity.kittyconfig.api.validation.DecimalRange
import uwu.serenity.kittyconfig.api.validation.Range

@Config("$MOD_ID/common", side = Config.Side.COMMON)
object EstrogenCommonConfig {

    @Comment("Settings for the dash effect")
    object Dash {

        @Comment("Enable dash from the Effect of Estrogen")
        var enabled: Boolean = true

        @Comment("The multiplier for the dash delta movement")
        var deltaModifier: @DecimalRange(0.0, 100.0) Double = 2.0

    }

    @Comment("Settings which are more fun and not fit for survival")
    object Minigame {

        @Comment("Enable/Disable all minigame settings")
        var enabled: Boolean = false

        @Comment("Gives you permanent, unremovable Girl Power effect")
        var permaDash: Boolean = false

        @Comment("The level of Girl Power Effect when Perma-Dash is enabled")
        @CustomName("permaDashLevel")
        var girlPowerLevel: @Range(0, 255) Int = 1

    }

    @Comment("Settings for the durations of effects")
    object Durations {

        @Comment("How long does Girl Power from the Estrogen Pill last in ticks")
        var estrogenPillDuration: Int = 6000

        @Comment("How long does Girl Power from the Crystal Estrogen Pill last in ticks")
        var crystalEstrogenPillDuration: Int = 6000

        @Comment("How long does Girl Power from the Estrogen Pill last in ticks")
        var estrogenChipCookieDuration: Int = 6000
    }

}