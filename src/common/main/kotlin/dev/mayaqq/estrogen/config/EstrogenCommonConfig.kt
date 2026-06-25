package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import invoke.kitty.kritter.config.api.ConfigCategory
import invoke.kitty.kritter.config.api.SyncedConfig
import invoke.kitty.kritter.config.formats.Json5Format
import invoke.kitty.kritter.config.validation.types.range

object EstrogenCommonConfig : SyncedConfig("$MOD_ID/common", Json5Format.Default) {

    object Dash : ConfigCategory(comment = "Settings for the dash effect") {

        var enabled by field(true) {
            comment = "Enable dash from the Effect of Estrogen"
        }

        var deltaModifier by field(2.0) {
            comment = "The multiplier for the dash delta movement"
            range = 0.0..100.0
        }

    }

    object Recipes : ConfigCategory(comment = "Recipe* Configuration") {
        var cauldronInteractions by field(true) {
            comment = "Enable Cauldron Interactions"
        }
    }

    object Durations : ConfigCategory(comment = "Settings for the durations of effects") {

        var estrogenPillDuration by field(6000) {
            comment = "How long does Girl Power from the Estrogen Pill last in ticks"
        }

        var crystalEstrogenPillDuration by field(6000) {
            comment = "How long does Girl Power from the Crystal Estrogen Pill last in ticks"
        }

        var estrogenChipCookieDuration by field(6000) {
            comment = "How long does Girl Power from the Estrogen Chip Cookie last in ticks"
        }
    }

}
