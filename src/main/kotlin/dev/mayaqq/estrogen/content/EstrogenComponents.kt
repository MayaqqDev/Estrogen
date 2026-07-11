package dev.mayaqq.estrogen.content

import com.mojang.serialization.Codec
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.components.ThighHighColor
import dev.mayaqq.estrogen.content.components.ThighHighStyle
import dev.mayaqq.estrogen.utils.TriColor
import earth.terrarium.common_storage_lib.fluid.util.FluidStorageData
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec

object EstrogenComponents : Registrar<DataComponentType<*>> by Registrar(MOD_ID, Registries.DATA_COMPONENT_TYPE) {
    val TriColorComponent: DataComponentType<TriColor> by entry("colors",
        DataComponentType.builder<TriColor>().persistent(TriColor.CODEC)::build
    )
    val ThighHighStyleComponent: DataComponentType<ThighHighStyle> by entry("thigh_high_style",
        DataComponentType.builder<ThighHighStyle>().persistent(ThighHighStyle.CODEC)::build
    )
    val ThighHighColorComponent: DataComponentType<ThighHighColor> by entry("thigh_high_color",
        DataComponentType.builder<ThighHighColor>().persistent(ThighHighColor.CODEC)::build
    )
    val FluidComponent: DataComponentType<FluidStorageData> by entry("fluid",
        DataComponentType.builder<FluidStorageData>().persistent(FluidStorageData.CODEC)::build
    )

    val DashLevel: DataComponentType<Int> by entry("dash_level",
        DataComponentType.builder<Int>().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT)::build
    )
}