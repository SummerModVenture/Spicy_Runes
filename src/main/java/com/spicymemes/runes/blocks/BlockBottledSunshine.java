package com.spicymemes.runes.blocks;

import com.spicymemes.runes.MainMod;
import com.spicymemes.runes.client.ParticleBottledSunshine;
import com.spicymemes.runes.network.SunshineUpdate;
import com.spicymemes.runes.tileentities.TileEntitySunshine;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by Spencer on 5/22/18.
 */
public class BlockBottledSunshine extends Block implements ITileEntityProvider{

    public BlockBottledSunshine() {
        super(Material.ROCK, MapColor.EMERALD);
        this.setUnlocalizedName("bottledsunshine");
        this.setRegistryName("bottledsunshine");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.markBlockRangeForRenderUpdate(pos.getX() - 5, pos.getY() - 5, pos.getZ() - 5, pos.getX() + 5, pos.getY() + 5, pos.getZ() + 5);
        worldIn.getChunkFromBlockCoords(pos).generateSkylightMap();
        worldIn.getChunkFromBlockCoords(pos).markDirty();

        if(!worldIn.isRemote){
            MainMod.NETWORK.sendToAll(new SunshineUpdate(pos, SunshineUpdate.Action.REMOVE));
            SunshineController.removeBottle(pos);
        }
        updateLocalLighting(worldIn, pos);
    }

    public static void updateLocalLighting(World worldIn, BlockPos pos){
        for(int i = -5; i <= 5; i++){
            for(int j = -5; j <= 5; j++){
                for(int k = -5; k <= 5; k++){
                    BlockPos p = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
                    worldIn.checkLight(p);
                }
            }
        }
        //TODO sync world state here.
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return false;
    }

    /*@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.3D;
        double d2 = (double)pos.getZ() + 0.5D;

        Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBottledSunshine(worldIn, d0, d1, d2));
    }*/

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.25, 0, 0.25, 0.75, 11f / 16f, 0.75);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySunshine();
    }
}
