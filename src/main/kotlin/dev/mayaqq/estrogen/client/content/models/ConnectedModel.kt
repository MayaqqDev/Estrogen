package dev.mayaqq.estrogen.client.content.models

import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel

expect class ConnectedModel(original: BakedModel, connectedSprite: TextureAtlasSprite) : BakedModel