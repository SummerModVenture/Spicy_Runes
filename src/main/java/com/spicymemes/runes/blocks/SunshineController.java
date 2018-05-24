package com.spicymemes.runes.blocks;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

/**
 * Created by Spencer on 5/23/18.
 */
public class SunshineController {
    private static ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
    public static void addBottle(BlockPos pos){
        System.out.println("Added bottle at " + pos);
        if(!positions.contains(pos))
            positions.add(pos);
    }

    public static void removeBottle(BlockPos pos){
        for(int i = 0; i < positions.size(); i++){
            if(positions.get(i).equals(pos)){
                int diff = positions.size();
                positions.remove(i);
                diff = diff - positions.size();
                i -= diff;
                System.out.println("Removed bottle at " + pos);
            }
        }
    }

    public static boolean inBottleRange(BlockPos p){
        for(int i = 0; i < positions.size(); i++){
            if(p.distanceSq(positions.get(i)) < 5 * 5){
                return true;
            }
        }
        return false;
    }
}
