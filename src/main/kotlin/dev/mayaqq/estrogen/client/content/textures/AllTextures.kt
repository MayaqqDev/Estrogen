package dev.mayaqq.estrogen.client.content.textures

import dev.mayaqq.estrogen.MOD_ID
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation

enum class RecipeTextures(namespace: String = MOD_ID, location: String, val startX: Int = 0, val startY: Int = 0, val width: Int, val height: Int) {
    JEI_SLOT("recipe/widgets", 18, 18),
    JEI_CHANCE_SLOT("recipe/widgets", 20, 156, 18, 18),
    JEI_CATALYST_SLOT("recipe/widgets", 0, 156, 18, 18),
    JEI_ARROW("recipe/widgets", 19, 10, 42, 10),
    JEI_LONG_ARROW("recipe/widgets", 19, 0, 71, 10),
    JEI_DOWN_ARROW("recipe/widgets", 0, 21, 18, 14),
    JEI_LIGHT("recipe/widgets", 0, 42, 52, 11),
    JEI_QUESTION_MARK("recipe/widgets", 0, 178, 12, 16),
    JEI_SHADOW("recipe/widgets", 0, 56, 52, 11),
    BLOCKZAPPER_UPGRADE_RECIPE("recipe/widgets", 0, 75, 144, 66),
    JEI_HEAT_BAR("recipe/widgets", 0, 201, 169, 19),
    JEI_NO_HEAT_BAR("recipe/widgets", 0, 221, 169, 19);

    val textureLocation = ResourceLocation(namespace, "textures/gui/$location.png")

    constructor(location: String, startX: Int, startY: Int, width: Int, height: Int) : this(MOD_ID, location, startX, startY, width, height)
    constructor(location: String, width: Int, height: Int) : this(location, 0, 0, width, height)

    fun render(graphics: GuiGraphics, x: Int, y: Int) {
        graphics.blit(textureLocation, x, y, startX, startY, width, height)
    }
}