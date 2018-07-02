package com.spicymemes.runes.client;

import com.spicymemes.runes.items.tools.sunstone.SunstoneTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Spencer on 5/25/18.
 */
public class SunstoneToolColor implements IItemColor {

    Random r = new Random();

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if(tintIndex == 0){
            return 0xffffff;
        }
        if(stack != null && stack.hasTagCompound()){
            SunstoneTypes[] types = SunstoneTypes.getTypesFromItemStack(stack);
            if(types != null && types.length > tintIndex - 1){
                //System.out.println(types[tintIndex - 1]);
                return types[tintIndex - 1].getColorValue();
            }
        }
        return 0xffffff;
    }
}
