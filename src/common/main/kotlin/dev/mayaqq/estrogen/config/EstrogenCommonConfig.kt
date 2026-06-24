package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import uwu.serenity.kittyconfig.Comment
import uwu.serenity.kittyconfig.Config
import uwu.serenity.kittyconfig.KittyConfig
import uwu.serenity.kittyconfig.minecraft.SyncToClient
import uwu.serenity.kittyconfig.toml.TomlFormat
import uwu.serenity.kittyconfig.validation.DecimalRange

@Config("$MOD_ID/common", TomlFormat::class)
@SyncToClient
object EstrogenCommonConfig : KittyConfig {

    @Comment("Settings for the dash effect")
    object Dash {

        @Comment("Enable dash from the Effect of Estrogen")
        var enabled: Boolean = true

        @Comment("The multiplier for the dash delta movement")
        var deltaModifier: @DecimalRange(0.0, 100.0) Double = 2.0

    }

    @Comment("Recipe* Configuration")
    object Recipes {
        @Comment("Enable Cauldron Interactions")
        var cauldronInteractions: Boolean = true
    }

    @Comment("Settings for the durations of effects")
    object Durations {

        @Comment("How long does Girl Power from the Estrogen Pill last in ticks")
        var estrogenPillDuration: Int = 6000

        @Comment("How long does Girl Power from the Crystal Estrogen Pill last in ticks")
        var crystalEstrogenPillDuration: Int = 6000

        @Comment("How long does Girl Power from the Estrogen Chip Cookie last in ticks")
        var estrogenChipCookieDuration: Int = 6000
    }

}
