package dev.mayaqq.estrogen.client.content.entityRenderers.boobs

import com.mojang.blaze3d.vertex.PoseStack
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.cynosure.utils.currentTime
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.features.boobs.Boob
import dev.mayaqq.estrogen.client.features.boobs.Boob.boobSize
import dev.mayaqq.estrogen.client.features.boobs.BoobPhysicsManager.getPhysicsForPlayer
import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.injection.chestConfig
import dev.mayaqq.estrogen.injection.renderBoobArmor
import dev.mayaqq.estrogen.injection.renderBoobArmorTrim
import dev.mayaqq.estrogen.injection.renderBoobs
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.client.model.EntityModel
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.Sheets
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.resources.model.ModelManager
import net.minecraft.core.component.DataComponents
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem

class BoobFeatureLayer(
    renderer: RenderLayerParent<Player, EntityModel<Player>>,
    val modelManager: ModelManager,
    val armorTrimAtlas: TextureAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET)
) : RenderLayer<Player, EntityModel<Player>>(renderer) {
    override fun render(stack: PoseStack, bufferSource: MultiBufferSource, i: Int, entity: Player, f: Float, g: Float, h: Float, j: Float, k: Float, l: Float) {
        val chestConfig: ChestConfig? = entity.chestConfig
        val entity = entity as AbstractClientPlayer
        if (Boob.shouldShow(entity) && chestConfig != null && entity.playerInfo?.skin != null && !entity.isInvisible) {
            // Armor that straight up disables Chest Feature and Armor and everything.
            if (entity.getItemBySlot(EquipmentSlot.CHEST) in EstrogenTags.Items.CHEST_FEATURE_DISABLED) return

            val vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(entity.skin.texture()))
            val m = LivingEntityRenderer.getOverlayCoords(entity, 0.0f)
            stack.pushPop {
                var size: Float
                val startTime: Double = entity.getAttributeValue(EstrogenAttributes.BoobGrowingStartTime.holder)
                val currentTime: Double = currentTime(entity.level())
                if (startTime >= 0.0) {
                    val initialSize = entity.getAttributeValue(EstrogenAttributes.BoobInitialSize.holder).toFloat()
                    size = boobSize(startTime, currentTime, initialSize, h)
                } else {
                    size = 0.0f
                }
                var yOffset = 0f
                // Physics check (global setting checked in physics manager
                if (chestConfig.physicsEnabled) {
                    val physics = getPhysicsForPlayer(entity)
                    if (physics.active) {
                        size += physics.interpolate(currentTime, h.toDouble())!!.x
                        yOffset = physics.interpolate(currentTime, h.toDouble())!!.y
                    }
                }
                parentModel.renderBoobs(stack, vertexConsumer, i, m, entity, size, yOffset)

                // Armor Check
                if (Boob.shouldShowArmor(entity)) {
                    val itemStack = entity.getItemBySlot(EquipmentSlot.CHEST)
                    if (itemStack.item is ArmorItem) {
                        val item = itemStack.item as ArmorItem
                        if (item.equipmentSlot == EquipmentSlot.CHEST) {
                            val glint = itemStack.hasFoil()
                            if (itemStack.get(DataComponents.DYED_COLOR) != null) {
                                val dyeColor: Int = itemStack.get(DataComponents.DYED_COLOR)!!.rgb()
                                val o = (dyeColor shr 16 and 255).toFloat() / 255.0f
                                val p = (dyeColor shr 8 and 255).toFloat() / 255.0f
                                val q = (dyeColor and 255).toFloat() / 255.0f

                                parentModel.renderBoobArmor(stack, bufferSource, i, glint, o, p, q, false, entity, size, yOffset)
                                parentModel.renderBoobArmor(stack, bufferSource, i, glint, 1.0f, 1.0f, 1.0f, true, entity, size, yOffset)
                            } else {
                                parentModel.renderBoobArmor(stack, bufferSource, i, glint, 1.0f, 1.0f, 1.0f, false, entity, size, yOffset)
                            }
                            itemStack.get(DataComponents.TRIM)?.let { trim ->
                                parentModel.renderBoobArmorTrim(
                                    stack,
                                    bufferSource,
                                    i,
                                    glint,
                                    trim,
                                    item.getMaterial(),
                                    armorTrimAtlas,
                                    entity
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}