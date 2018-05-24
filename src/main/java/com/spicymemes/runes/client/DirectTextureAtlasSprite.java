package com.spicymemes.runes.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Spencer on 5/23/18.
 */
public class DirectTextureAtlasSprite extends TextureAtlasSprite{
    protected DirectTextureAtlasSprite(ResourceLocation loc) {
        super(loc.toString());
    }
}
