package com.spicymemes.runes;

import com.spicymemes.runes.client.SunstoneBricksTESR;
import com.spicymemes.runes.network.SunshineClientUpdater;
import com.spicymemes.runes.network.SunshineUpdate;
import com.spicymemes.runes.tileentities.TileEntitySunstoneBricks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Spencer on 5/23/18.
 */
public class ClientProxy extends CommonProxy {
    public void init(){
        super.init();
        MainMod.NETWORK.registerMessage(SunshineClientUpdater.class, SunshineUpdate.class, 0, Side.CLIENT);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySunstoneBricks.class, new SunstoneBricksTESR());
        //Minecraft.getMinecraft().getItemColors().registerItemColorHandler();
    }
}
