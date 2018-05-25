package com.spicymemes.runes.tileentities;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Spencer on 5/23/18.
 */
public class ModTileEntities {
    public static void init(){
        GameRegistry.registerTileEntity(TileEntitySunshine.class, "bottled_sunshine");
        GameRegistry.registerTileEntity(TileEntitySunstoneBricks.class, "sunstone_bricks");
    }
}
