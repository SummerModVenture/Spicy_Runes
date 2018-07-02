package com.spicymemes.runes.items;

import com.spicymemes.runes.MainMod;
import com.spicymemes.runes.items.tools.sunstone.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

    public static Item sunstoneAxe, sunstonePick, sunstoneShovel, sunstoneHoe, sunstoneSword, sunstone;

    public static void init(){
        sunstoneAxe = new SunstoneAxe();
        sunstonePick = new SunstonePickaxe();
        sunstoneShovel = new SunstoneShovel();
        sunstoneHoe = new SunstoneHoe();
        sunstoneSword = new SunstoneSword();
        sunstone = new ItemSunstone();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(sunstoneAxe);
        event.getRegistry().register(sunstoneShovel);
        event.getRegistry().register(sunstonePick);
        event.getRegistry().register(sunstoneHoe);
        event.getRegistry().register(sunstoneSword);
        event.getRegistry().register(sunstone);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(sunstoneAxe);
        registerRender(sunstoneHoe);
        registerRender(sunstonePick);
        registerRender(sunstoneShovel);
        registerRender(sunstoneSword);
        registerRender(sunstone);
    }

    public static void registerRender(Item item) {
        if(item.getHasSubtypes()){
            NonNullList<ItemStack> stacks = NonNullList.create();
            item.getSubItems(item.getCreativeTab(), stacks);
            for(ItemStack s : stacks){
                ModelLoader.setCustomModelResourceLocation(item, s.getMetadata(), new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
        else{
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
