package com.spicymemes.runes.blocks;

import com.spicymemes.runes.tileentities.TileEntitySunPedestal;
import com.spicymemes.runes.tileentities.TileEntitySunstoneBricks;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Spencer on 5/24/18.
 */
public class BlockSunStatue extends Block {
    public BlockSunStatue() {
        super(Material.ROCK);
        this.setUnlocalizedName("sun_statue");
        this.setRegistryName("sun_statue");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }


    /*@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }*/

}
