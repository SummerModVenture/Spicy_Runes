package com.spicymemes.runes.crafting;

import com.spicymemes.runes.items.ModItems;
import com.spicymemes.runes.items.tools.sunstone.SunstoneTypes;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

public class RecipeSunstoneTool extends ShapedRecipes
{

    private boolean reversed;

    public RecipeSunstoneTool(String group, int width, int height, boolean reverseSearch, ItemStack result, Object ... in) {
        super(group, width, height, NonNullList.from(Ingredient.EMPTY, toIngredients(in)), result);
        this.setRegistryName("recipe_" + group);
        reversed = reverseSearch;
    }

    private static Ingredient[] toIngredients(Object[] in){
        Ingredient[] out = new Ingredient[in.length];
        for(int i = 0; i < in.length; i++){
            if(in[i] == null){
                out[i] = Ingredient.EMPTY;
            }
            else if(in[i] instanceof ItemStack){
                out[i] = Ingredient.fromStacks(new ItemStack[]{(ItemStack) in[i]});
            }
            else if(in[i] instanceof ItemStack[]){
                out[i] = Ingredient.fromStacks((ItemStack[]) in[i]);
            }
            else if(in[i] instanceof Item){
                out[i] = Ingredient.fromItem((Item) in[i]);
            }
            else if(in[i] instanceof Item[]){
                out[i] = Ingredient.fromItems((Item[]) in[i]);
            }
            else if(in[i] instanceof Block){
                out[i] = Ingredient.fromItem(Item.getItemFromBlock((Block) in[i]));
            }
            else if(in[i] instanceof Ingredient){
                out[i] = (Ingredient) in[i];
            }
        }
        return out;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ArrayList<SunstoneTypes> types = new ArrayList<>();
        for(int i = 0; i < inv.getHeight(); i++){
            for(int j = 0; j < inv.getWidth(); j++){
                ItemStack stack;
                //FIXME
                if(reversed){
                    stack = inv.getStackInRowAndColumn(inv.getWidth() - j - 1, i);
                }
                else {
                    stack = inv.getStackInRowAndColumn(j, i);
                }
                if(stack != null && stack.getItem() == ModItems.sunstone){
                    if(stack.getItemDamage() < 16 && stack.getItemDamage() >= 0){
                        types.add(SunstoneTypes.values()[stack.getItemDamage()]);
                    }
                }
            }
        }
        ItemStack out = new ItemStack(this.getRecipeOutput().getItem(), 1);
        SunstoneTypes[] t = new SunstoneTypes[types.size()];
        for(int i = 0; i < t.length; i++){
            t[i] = types.get(i);
        }
        SunstoneTypes.setStackTypes(out, t);
        return out;
    }
}