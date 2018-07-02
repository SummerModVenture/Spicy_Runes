package com.spicymemes.runes.items.tools.sunstone;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum SunstoneTypes
{
    NORMAL("white", 16383998),
    ORANGE("orange", 16351261),
    MAGENTA("magenta", 13061821),
    LIGHT_BLUE("lightBlue", 3847130),
    YELLOW("yellow", 16701501),
    LIME("lime", 8439583),
    PINK("pink", 15961002),
    GRAY("gray", 4673362),
    SILVER("silver", 10329495),
    CYAN("cyan", 1481884),
    PURPLE("purple", 8991416),
    BLUE("blue", 3949738),
    BROWN("brown", 8606770),
    GREEN("green", 6192150),
    RED("red", 11546150),
    BLACK("black", 1908001);

    private final String name;
    /** An int containing the corresponding RGB color for this dye color. */
    private final int colorValue;
    /**
     * An array containing 3 floats ranging from 0.0 to 1.0: the red, green, and blue components of the corresponding
     * color.
     */
    private SunstoneTypes(String nameIn, int colorValueIn)
    {
        this.name = nameIn;
        this.colorValue = colorValueIn;
    }

    @SideOnly(Side.CLIENT)
    public String getColorName()
    {
        return this.name;
    }

    /**
     * Gets the RGB color corresponding to this dye color.
     */
    @SideOnly(Side.CLIENT)
    public int getColorValue()
    {
        return this.colorValue;
    }

    public static SunstoneTypes[] getTypesFromItemStack(ItemStack stack){
        SunstoneTypes[] out = null;
        if(stack != null && stack.getTagCompound() != null) {
            NBTTagCompound compound = stack.getTagCompound();
            int[] types = compound.getIntArray("sunstone_types");
            out = new SunstoneTypes[types.length];
            if (types != null) {
                for(int i = 0; i < types.length; i++){
                    out[i] = SunstoneTypes.values()[types[i]];
                }
            }
        }
        return out;
    }

    public static void setStackTypes(ItemStack stack, SunstoneTypes[] types){
        if(!stack.hasTagCompound()){
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound compound = stack.getTagCompound();
        int[] encoded = new int[types.length];
        for(int i = 0; i < encoded.length; i++){
            encoded[i] = types[i].ordinal();
        }
        compound.setIntArray("sunstone_types", encoded);
    }
}