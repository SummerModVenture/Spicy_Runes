package com.spicymemes.runes.crafting;

import com.spicymemes.runes.MainMod;
import com.spicymemes.runes.blocks.ModBlocks;
import com.spicymemes.runes.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Spencer on 5/28/18.
 */
@Mod.EventBusSubscriber(modid= MainMod.MODID)

public class Recipes {
    @SubscribeEvent
    public static void init(RegistryEvent.Register<IRecipe> event){
        event.getRegistry().register(new RecipeSunstoneTool("sunstone_axe", 2, 3, false, new ItemStack(ModItems.sunstoneAxe, 1), ModItems.sunstone, ModItems.sunstone, ModItems.sunstone, Items.STICK, null, Items.STICK));
        event.getRegistry().register(new RecipeSunstoneTool("sunstone_axe", 2, 3, true, new ItemStack(ModItems.sunstoneAxe, 1), ModItems.sunstone, ModItems.sunstone, Items.STICK, ModItems.sunstone, Items.STICK, null));
        event.getRegistry().register(new RecipeSunstoneTool("sunstone_hoe", 2, 3, false, new ItemStack(ModItems.sunstoneHoe, 1), ModItems.sunstone, ModItems.sunstone, null, Items.STICK, null, Items.STICK));
        event.getRegistry().register(new RecipeSunstoneTool("sunstone_hoe", 2, 3, true, new ItemStack(ModItems.sunstoneHoe, 1), ModItems.sunstone, ModItems.sunstone, Items.STICK, null, Items.STICK, null));
        event.getRegistry().register(new RecipeSunstoneTool("sunstone_pick", 3, 3, false, new ItemStack(ModItems.sunstonePick, 1), ModItems.sunstone, ModItems.sunstone, ModItems.sunstone, null, Items.STICK, null, null, Items.STICK, null));
        event.getRegistry().register(new RecipeSunstoneTool("sunstone_shovel", 1, 3, false, new ItemStack(ModItems.sunstoneShovel, 1), ModItems.sunstone, Items.STICK, Items.STICK));
        event.getRegistry().register(new RecipeSunstoneTool("sunstone_sword", 1, 3, false, new ItemStack(ModItems.sunstoneSword, 1), ModItems.sunstone, ModItems.sunstone, Items.STICK));
    }
}
