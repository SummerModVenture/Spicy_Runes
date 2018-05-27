package com.spicymemes.runes.items;

import com.spicymemes.runes.MainMod;
import com.spicymemes.runes.items.tools.sunstone.SunstoneAxe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Spencer on 5/23/18.
 */

@Mod.EventBusSubscriber(modid= MainMod.MODID)
public class ModItems {

    public static Item sunstoneAxe;

    public static void init(){
        sunstoneAxe = new SunstoneAxe();
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(sunstoneAxe);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(sunstoneAxe);
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
