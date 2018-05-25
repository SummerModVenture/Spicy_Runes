package com.spicymemes.runes.blocks;

import com.spicymemes.runes.tileentities.TileEntitySunstoneBricks;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Spencer on 5/24/18.
 */
public class BlockSunstoneBricks extends Block implements ITileEntityProvider {
    public BlockSunstoneBricks() {
        super(Material.ROCK);
        this.setUnlocalizedName("sunstone_bricks");
        this.setRegistryName("sunstone_bricks");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySunstoneBricks();
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
