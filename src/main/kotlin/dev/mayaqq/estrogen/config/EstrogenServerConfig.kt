package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import invoke.kitty.kritter.config.api.Config
import invoke.kitty.kritter.config.api.ConfigCategory
import invoke.kitty.kritter.config.formats.Json5Format
import invoke.kitty.kritter.config.validation.types.min
import invoke.kitty.kritter.config.validation.types.range

object EstrogenServerConfig : Config("$MOD_ID/server", Json5Format.Default) {

    object Patch : ConfigCategory() {

        val girlPowerLevel by field(1) {
            comment = "The level of girl power you get from estrogen patches"
            range = 1..255
        }

        val drain by field(true) {
            comment = "Enable the estrogen patch to drain"
        }

        val drainSpeed by field(144) {
            comment = "The amount of ticks it takes for the estrogen patches to drain a millibucket"
            min = 0
        }

    }

    object ThighHighs : ConfigCategory() {
        val fallDamageReduction by field(100) {
            comment = "The amount of Fall Damage Reduction Thigh Highs add"
            range = 0..1000
        }

        val prideThighHighsChance by field(10) {
            comment = "The chance for the Pride Thigh Highs to spawn in chests (in % per container)"
            range = 0..100
        }
    }

    object DreamBlock : ConfigCategory(comment = "Settings for the dream block") {

        val dreamingEffectRange by field(10) {
            comment = "Horizontal range from a dream block in which the dreaming effect can be applied to sleeping players"
        }

        val dreamingTickChance by field(25) {
            comment = "Chance for a random tick to cause the dreaming effect to be applied in percent"
            range = 0..100
        }

        val dreamCatcherRange by field(10) {
            comment = "Range from a dream catcher for a player to be to block the Dreaming Effect"
        }
    }

    object Minigame : ConfigCategory(comment = "Settings which are more fun and not fit for survival") {

        val enabled: Boolean by field(false) {
            comment = "Enable/Disable all minigame settings"
        }

        val permaDash: Boolean by field(false) {
            comment = "Gives you permanent, unremovable Girl Power effect"
        }

        val girlPowerLevel: Int by field(1) {
            comment = "The level of Girl Power Effect when Perma-Dash is enabled"
            range = 0..255
        }

    }
}