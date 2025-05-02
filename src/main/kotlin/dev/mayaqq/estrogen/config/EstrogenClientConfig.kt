package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.chestConfigSet
import dev.mayaqq.estrogen.config.types.DreamBlockRenderMode
import net.minecraft.client.Minecraft
import uwu.serenity.kittyconfig.api.Comment
import uwu.serenity.kittyconfig.api.Config
import uwu.serenity.kittyconfig.api.CustomName
import uwu.serenity.kittyconfig.api.observable.observable
import uwu.serenity.kittyconfig.api.validation.DecimalRange
import uwu.serenity.kittyconfig.json5.Json5Format

@Config(id = "$MOD_ID/client", side = Config.Side.CLIENT)
object EstrogenClientConfig {

    @Comment("Enable entity patting")
    var entityPatting: Boolean = true

    @Comment("Estrogen ambient music")
    @JvmField
    var ambientMusic: Boolean = true

    @Comment("Global settings for chest feature rendering")
    object ChestRenderingGlobal {

        @Comment("Enable chest feature rendering")
        var rendering: Boolean = true

        @Comment("Enable chest feature armor rendering")
        var armorRendering: Boolean = true

        @Comment("Enable chest feature physics rendering")
        var physicsRendering: Boolean = true
    }

    @Comment("Settings for the chest feature (for local player)")
    object ChestFeature {

        @Comment("Enable chest feature")
        var enabled: Boolean by observable(true) { chestConfigSet = false }

        @Comment("Enable chest feature armor")
        var armor: Boolean by observable(true) { chestConfigSet = false }

        @Comment("Enable chest feature physics")
        var physics: Boolean by observable(true) { chestConfigSet = false }

        @Comment("Chest feature bounciness")
        var bounciness: @DecimalRange(0.0, 1.0) Double by observable(0.27) { chestConfigSet = false }

        @Comment("Chest feature physics damping")
        var damping: @DecimalRange(0.0, 1.0) Float by observable(0.375f) { chestConfigSet = false }

    }

    @Comment("Settings for the dreamn block")
    object DreamBlock {

        @Comment("""
            Use advanced renderer for dream blocks, possibly incompatible with iris shaders. 
            DEFAULT disables it automatically when shaders are in use
        """)
        var dreamBlockRenderMode: DreamBlockRenderMode by observable(DreamBlockRenderMode.DEFAULT) {
            // reloadWorldRenderer()
        }

        @Comment("Animate dream block texture")
        var animateTexture: Boolean = true
    }

    @Comment("Settings for Equippable Items")
    object Accessories {

        var renderEstrogenPatches: Boolean = true
    }

    @Comment("UI element Configuration")
    object UI {

        @Comment("Enable dash overlay")
        var dashOverlay: Boolean = true

        @Comment("Settings for the estrogen button in the create screen")
        object EstrogenButton {

            @Comment("Enable the estrogen button in the create screen")
            var enabled: Boolean = true

            @Comment("""
                X offset the estrogen button in the create screen
                Offset is calculated off of the center of the Configure Button
            """)
            var xOffset: Int = -23

            @Comment("""
                X offset the estrogen button in the create screen
                Offset is calculated off of the center of the Configure Button
            """)
            var yOffset: Int = 0

            @Comment("Custom Splashes on the main menu! Requires resource reload")
            var splashText: Boolean = true
        }
    }

    @Comment("Compatibility between other mods settings")
    object Compat {

        @Comment("Enable ears compat")
        var ears: Boolean = true

        @Comment("Enable figura compat")
        var figura: Boolean = true

    }

}