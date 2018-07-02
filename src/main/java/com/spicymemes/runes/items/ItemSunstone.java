package com.spicymemes.runes.items;

import com.spicymemes.runes.items.tools.sunstone.SunstoneTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Created by Spencer on 5/28/18.
 */
public class ItemSunstone extends Item {
    public ItemSunstone(){
        setCreativeTab(CreativeTabs.MATERIALS);
        setRegistryName("item_sunstone");
        setUnlocalizedName("item_sunstone");
        setHasSubtypes(true);
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for(int i = 0; i < 16; i++){
                ItemStack is = new ItemStack(this, 1, i);
                items.add(is);
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName(par1ItemStack) + par1ItemStack.getItemDamage();
    }
}
