package com.spicymemes.runes.items.tools.sunstone;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

/**
 * Created by Spencer on 5/27/18.
 */
public class SunstoneAxe extends ItemAxe{
    public SunstoneAxe() {
        super(ToolMaterial.IRON);
        this.setRegistryName("sunstone_axe");
        this.setUnlocalizedName("sunstone_axe");
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @javax.annotation.Nullable
    private String toolClass;
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player, @javax.annotation.Nullable IBlockState blockState)
    {
        return super.getHarvestLevel(stack, toolClass, player, blockState);
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return super.getDestroySpeed(stack, state) * 5;
    }
}
