package com.spicymemes.runes.blocks;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Spencer on 5/23/18.
 */
public class SunshineController {

    private static HashMap<Integer, SunshineController> controllers = new HashMap<Integer, SunshineController>();

    private SunshineController(){

    }

    public static SunshineController getController(int dimensionID){
        if(!controllers.containsKey(dimensionID)){
            controllers.put(dimensionID, new SunshineController());
        }
        return controllers.get(dimensionID);
    }

    private ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
    public void addBottle(BlockPos pos){
        System.out.println("Added bottle at " + pos);
        if(!positions.contains(pos))
            positions.add(pos);
    }

    public void removeBottle(BlockPos pos){
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

    public boolean inBottleRange(BlockPos p){
        for(int i = 0; i < positions.size(); i++){
            if(p.distanceSq(positions.get(i)) < BlockBottledSunshine.R2){
                return true;
            }
        }
        return false;
    }
}
