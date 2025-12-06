package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import uwu.serenity.kittyconfig.Comment
import uwu.serenity.kittyconfig.Config
import uwu.serenity.kittyconfig.KittyConfig
import uwu.serenity.kittyconfig.SerialName
import uwu.serenity.kittyconfig.toml.TomlFormat
import uwu.serenity.kittyconfig.validation.Range

@Config("$MOD_ID/server", TomlFormat::class)
object EstrogenServerConfig : KittyConfig {

    object Patch {

        @Comment("The level of girl power you get from estrogen patches")
        var girlPowerLevel: @Range(1, 255) Int = 1

        @Comment("Enable the estrogen patch to drain")
        var drain: Boolean = true

        @Comment("The amount of ticks it takes for the estrogen patches to drain a millibucket")
        var drainSpeed: @Range(min = 0) Int = 144

    }

    object ThighHighs {
        @Comment("The amount of Fall Damage Reduction Thigh Highs add")
        var fallDamageReduction: @Range(min = 0, max = 1000) Int = 100
    }

    @Comment("Settings for the dream block")
    object DreamBlock {

        @Comment("Horizontal range from a dream block in which the dreaming effect can be applied to sleeping players")
        var dreamingEffectRange: Int = 20

        @Comment("Chance for a random tick to cause the dreaming effect to be applied in percent")
        var dreamingTickChance: @Range(0, 100) Int = 100

        @Comment("Range from a dream catcher for a player to be to block the Dreaming Effect")
        var dreamCatcherRange: Int = 10
    }

    @Comment("Settings which are more fun and not fit for survival")
    object Minigame {

        @Comment("Enable/Disable all minigame settings")
        var enabled: Boolean = false

        @Comment("Gives you permanent, unremovable Girl Power effect")
        var permaDash: Boolean = false

        @Comment("The level of Girl Power Effect when Perma-Dash is enabled")
        @SerialName("permaDashLevel")
        var girlPowerLevel: @Range(0, 255) Int = 1

    }

    @Comment("Bug fixes and other improvements related to this mod")
    object Fixes {
        @Comment("Fix a dupe glitch related to Jukeboxes and stacks")
        var jukeboxFix: Boolean = true
    }

}