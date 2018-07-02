package com.spicymemes.runes.client;

import com.spicymemes.runes.items.tools.sunstone.SunstoneTypes;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

import java.util.Random;

/**
 * Created by Spencer on 5/25/18.
 */
public class SunstoneItemColor implements IItemColor {

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if(0 <= stack.getItemDamage() && stack.getItemDamage() < 16){
            return SunstoneTypes.values()[stack.getItemDamage()].getColorValue();
        }
        return 0xffffff;
    }
}
