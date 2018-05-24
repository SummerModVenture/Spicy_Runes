package com.spicymemes.runes.coremod;

import com.spicymemes.runes.blocks.SunshineController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Spencer on 5/22/18.
 */
public class WorldPatches{

    static {
        System.out.println("LOADED");
    }

    public static boolean seeSkyCondition(World o, BlockPos pos){
        return true;
    }

    public static boolean seeBottle(World w, BlockPos pos){
        return SunshineController.getController(w.provider.getDimension()).inBottleRange(pos);
    }

    public static boolean isOpaqueCube(Block gs, IBlockState bs, int x, Random pos){
        System.out.println("SPICY MEMES");
        return true;
    }

    public static int quantityDropped(Block gs, IBlockState bs, int x, Random random)
    {
        System.out.println("ALSO SPICY MEMES");
        return 32;
    }


    public boolean canSeeSky(BlockPos pos)
    {
        return true;//this.getChunkFromBlockCoords(pos).canSeeSky(pos);
    }

}
