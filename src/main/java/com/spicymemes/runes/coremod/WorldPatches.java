package com.spicymemes.runes.coremod;

import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * Created by Spencer on 5/22/18.
 */
public class WorldPatches{
    public static boolean seeSkyCondition(Object o, BlockPos pos){

        //System.out.println("SPOOPY");
        return true;
    }
    public static boolean isOpaqueCube(Object gs, IBlockState bs, int x, Random pos){
        System.out.println("SPICY MEMES");
        return true;
    }

    public static int quantityDropped(Object gs, IBlockState bs, int x, Random random)
    {
        System.out.println("ALSO SPICY MEMES");
        return 32;
    }


    public boolean canSeeSky(BlockPos pos)
    {
        return true;//this.getChunkFromBlockCoords(pos).canSeeSky(pos);
    }

}
