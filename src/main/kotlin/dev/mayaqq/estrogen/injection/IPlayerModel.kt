package dev.mayaqq.estrogen.injection

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.core.Holder
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.armortrim.ArmorTrim

interface IPlayerModel {
    fun `estrogen$renderBoobs`(stack: PoseStack, vertexConsumer: VertexConsumer, light: Int, overlay: Int, player: AbstractClientPlayer, size: Float, yOffset: Float)
    fun `estrogen$renderBoobArmor`(stack: PoseStack, bufferSource: MultiBufferSource, light: Int, glint: Boolean, red: Float, green: Float, blue: Float, overlay: Boolean, player: AbstractClientPlayer, size: Float, yOffset: Float)
    fun `estrogen$renderBoobArmorTrim`(stack: PoseStack, bufferSource: MultiBufferSource, light: Int, glint: Boolean, trim: ArmorTrim, material: Holder<ArmorMaterial>, trimAtlas: TextureAtlas, player: AbstractClientPlayer)
}

fun EntityModel<*>.renderBoobs(stack: PoseStack, vertexConsumer: VertexConsumer, light: Int, overlay: Int, player: AbstractClientPlayer, size: Float, yOffset: Float) {
    (this as IPlayerModel).`estrogen$renderBoobs`(stack, vertexConsumer, light, overlay, player, size, yOffset)
}
fun EntityModel<*>.renderBoobArmor(stack: PoseStack, bufferSource: MultiBufferSource, light: Int, glint: Boolean, red: Float, green: Float, blue: Float, overlay: Boolean, player: AbstractClientPlayer, size: Float, yOffset: Float) {
    (this as IPlayerModel).`estrogen$renderBoobArmor`(stack, bufferSource, light, glint, red, green, blue, overlay, player, size, yOffset)
}
fun EntityModel<*>.renderBoobArmorTrim(stack: PoseStack, bufferSource: MultiBufferSource, light: Int, glint: Boolean, trim: ArmorTrim, material: Holder<ArmorMaterial>, trimAtlas: TextureAtlas, player: AbstractClientPlayer) {
    (this as IPlayerModel).`estrogen$renderBoobArmorTrim`(stack, bufferSource, light, glint, trim, material, trimAtlas, player)
}