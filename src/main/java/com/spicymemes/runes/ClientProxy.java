package com.spicymemes.runes;

import com.spicymemes.runes.network.SunshineClientUpdater;
import com.spicymemes.runes.network.SunshineUpdate;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Spencer on 5/23/18.
 */
public class ClientProxy extends CommonProxy {
    public void init(){
        super.init();
        MainMod.NETWORK.registerMessage(SunshineClientUpdater.class, SunshineUpdate.class, 0, Side.CLIENT);
        //Minecraft.getMinecraft().getItemColors().registerItemColorHandler();
    }
}
