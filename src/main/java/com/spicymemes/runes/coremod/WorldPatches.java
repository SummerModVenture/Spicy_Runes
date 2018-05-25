package com.spicymemes.runes.coremod;

import com.spicymemes.runes.blocks.SunshineController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.material.Material;
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
        if(Math.random() > 0.5){
            System.out.println("SPICY MEMES");
            return true;
        }
        return false;
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

    public static boolean hasWater(BlockFarmland b, World worldIn, BlockPos pos)
    {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 1, 1)))
        {
            if (worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.WATER)
            {
                //System.out.println("Farmland at " + pos + " does have water");
                return true;
            }
        }
        //System.out.println("Farmland at " + pos + " does not have water");
        return false;
    }

    public static boolean hasWaterCondition(BlockFarmland b, World worldIn, BlockPos pos){
        //System.out.println("Called hasWaterCondition");
        return true;
    }
}
