package com.spicymemes.runes.blocks;

import com.spicymemes.runes.tileentities.TileEntitySunstoneBricks;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Spencer on 5/24/18.
 */
public class BlockSunstoneCables extends Block {

    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final IProperty<Boolean> POWERED = PropertyBool.create("powered");

    public BlockSunstoneCables() {
        super(Material.ROCK);
        this.setUnlocalizedName("sunstone_cables");
        this.setRegistryName("sunstone_cables");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    //@Override
    //public boolean isFullCube(IBlockState state) {
        //return false;
    //}

    //@Override
    //public boolean isOpaqueCube(IBlockState state) {
    //    return false;
    //}

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getPoweredState(facing, pos, worldIn);
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return getPoweredState(state.getValue(FACING), pos, worldIn);
    }

    private IBlockState getPoweredState(EnumFacing facing, BlockPos pos, IBlockAccess acc){
        return this.getDefaultState().withProperty(FACING, facing).withProperty(POWERED, getBlockPower(acc, pos.offset(facing.getOpposite()), facing.getOpposite()) > 0);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side)
    {
        return state.canProvidePower() && (side == (state.getValue(FACING)) || side == (state.getValue(FACING).getOpposite()));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockDirectional.FACING, POWERED);
    }

    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockState.getWeakPower(blockAccess, pos, side);
    }

    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockState.getValue(FACING) == side.getOpposite() ? getBlockPower(blockAccess, pos.offset(side), side) : 0;
    }

    private int getBlockPower(IBlockAccess acc, BlockPos pos, EnumFacing side){
        int power = 0;
        IBlockState neighbor = acc.getBlockState(pos);
        if(neighbor.getProperties().containsKey(BlockRedstoneWire.POWER))
            power = neighbor.getValue(BlockRedstoneWire.POWER);
        int strong = acc.getStrongPower(pos, side);
        if(strong > power){
            power = strong;
        }
        int weak = neighbor.getWeakPower(acc, pos, side);
        if(weak > power){
            power = weak;
        }
        return power;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
        worldIn.neighborChanged(pos.offset((EnumFacing)state.getValue(FACING)), this, pos);
    }

    /*@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }*/
}
