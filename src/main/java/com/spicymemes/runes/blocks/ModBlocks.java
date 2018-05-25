package com.spicymemes.runes.blocks;

import com.spicymemes.runes.MainMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Spencer on 5/22/18.
 */
@Mod.EventBusSubscriber(modid= MainMod.MODID)
public class ModBlocks {

    public static Block bottledSunshine, sunstoneBricks;

    public static void init() {
        bottledSunshine = new BlockBottledSunshine();
        sunstoneBricks = new BlockSunstoneBricks();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(bottledSunshine);
        event.getRegistry().register(sunstoneBricks);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(bottledSunshine).setRegistryName(bottledSunshine.getRegistryName()));
        event.getRegistry().register(new ItemBlock(sunstoneBricks).setRegistryName(sunstoneBricks.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(Item.getItemFromBlock(bottledSunshine));
        registerRender(Item.getItemFromBlock(sunstoneBricks));
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}