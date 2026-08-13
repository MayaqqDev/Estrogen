package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.chestConfigSet
import invoke.kitty.kritter.config.api.Config
import invoke.kitty.kritter.config.api.ConfigCategory
import invoke.kitty.kritter.config.formats.Json5Format
import invoke.kitty.kritter.config.validation.types.range
import kotlinx.serialization.Serializable


object EstrogenClientConfig : Config("$MOD_ID/client", Json5Format.Default) {

    object Music : ConfigCategory(comment = "Estrogen ambient music settings") {
        val enabled: Boolean by field(true) {
            comment = "Enable Estrogen Ambient Music"
        }

        val minDelayBetweenSongs: Int by field(0) {
            comment = "Minimum Delay between songs in ticks"
            range = 0..100000
        }

        val maxDelayBetweenSongs: Int by field(0) {
            comment = "Maxim Delay between songs in ticks"
            range = 0..100000
        }

        val replacesCurrentMusic: Boolean by field(true) {
            comment = "If shall the Music wait for the current one to finish"
        }
    }

    object ChestRenderingGlobal : ConfigCategory(comment = "Global settings for chest feature rendering") {

        val rendering: Boolean by field(true) {
            comment = "Enable chest feature rendering"
        }

        val armorRendering: Boolean by field(true) {
            comment = "Enable chest feature armor rendering"
        }

        val physicsRendering: Boolean by field(true) {
            comment = "Enable chest feature physics rendering"
        }
    }

    object ChestFeature : ConfigCategory(comment = "Settings for the chest feature (for local player)") {

        val enabled: Boolean by field(true) {
            comment = "Enable chest feature"
            onChanged { chestConfigSet = false }
        }

        val armor: Boolean by field(true) {
            comment = "Enable chest feature armor"
            onChanged { chestConfigSet = false }
        }

        val physics: Boolean by field(true) {
            comment = "Enable chest feature physics"
            onChanged { chestConfigSet = false }
        }

        val bounciness: Double by field(0.27) {
            comment = "Chest feature bounciness"
            range = 0.0..1.0
            onChanged { chestConfigSet = false }
        }

        val damping: Float by field(0.375f) {
            comment = "Chest feature physics damping"
            range = 0.0F..1.0F
            onChanged {chestConfigSet = false }
        }
    }

    object DreamBlock : ConfigCategory(comment = "Settings for the dream block") {

        val animateTexture: Boolean by field(true) {
            comment = "Animate dream block texture"
        }
    }

    object Accessories : ConfigCategory(comment = "Settings for Equippable Items") {

        val renderEstrogenPatches: Boolean by field(true)
    }

    object UI : ConfigCategory(comment = "UI element Configuration") {

        val dashOverlay: Boolean by field(true) {
            comment = "Enable dash overlay"
        }
    }

    object Compat : ConfigCategory(comment = "Compatibility between other mods settings") {

        val ears: Boolean by field(true) {
            comment = "Enable ears compat"
        }

        val figura: Boolean by field(true) {
            comment = "Enable figura compat"
        }

    }
}