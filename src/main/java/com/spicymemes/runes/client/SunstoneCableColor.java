package com.spicymemes.runes.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

/**
 * Created by Spencer on 5/25/18.
 */
public class SunstoneCableColor implements IBlockColor, IItemColor {
    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        if(tintIndex == 0){
            return 0xffffff;
        }
        return 0xff2222;
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if(tintIndex == 0){
            return 0xffffff;
        }
        return 0xff2222;
    }
}
