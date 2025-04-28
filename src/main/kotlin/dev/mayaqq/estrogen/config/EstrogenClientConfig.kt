package dev.mayaqq.estrogen.config

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.config.types.DreamBlockRenderMode
import uwu.serenity.kittyconfig.api.Comment
import uwu.serenity.kittyconfig.api.Config
import uwu.serenity.kittyconfig.api.CustomName
import uwu.serenity.kittyconfig.api.observable.observable
import uwu.serenity.kittyconfig.api.validation.DecimalRange
import uwu.serenity.kittyconfig.json5.Json5Format

@Config(id = "$MOD_ID/client", side = Config.Side.CLIENT)
object EstrogenClientConfig {

    @Comment("Enable entity patting")
    @CustomName("entity")
    var entityPatting: Boolean = true

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
        var enabled: Boolean = true

        @Comment("Enable chest feature armor")
        var armor: Boolean = true

        @Comment("Enable chest feature physics")
        var physics: Boolean = true

        @Comment("Chest feature bounciness")
        var bounciness: @DecimalRange(0.0, 1.0) Double = 0.27

        @Comment("Chest feature physics damping")
        var damping: @DecimalRange(0.0, 1.0) Float = 0.375f

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

    object UI {

        @Comment("Enable dash overlay")
        var dashOverlay: Boolean = true
    }

}