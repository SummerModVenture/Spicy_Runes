package com.spicymemes.runes.tileentities;

import com.spicymemes.runes.MainMod;
import com.spicymemes.runes.blocks.BlockBottledSunshine;
import com.spicymemes.runes.blocks.SunshineController;
import com.spicymemes.runes.client.ParticleBottledSunshine;
import com.spicymemes.runes.network.SunshineUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.Random;

/**
 * Created by Spencer on 5/23/18.
 */
public class TileEntitySunshine extends TileEntity implements ITickable{
    @Override
    public void onChunkUnload()
    {
        if(!this.world.isRemote){
            SunshineController.getController(world.provider.getDimension()).removeBottle(this.getPos());
            MainMod.NETWORK.sendToAll(new SunshineUpdate(pos, world.provider.getDimension(), SunshineUpdate.Action.REMOVE));
            BlockBottledSunshine.updateLocalLighting(this.world, this.pos);
        }
    }
    @Override
    public void onLoad()
    {
        if(!this.world.isRemote){
            SunshineController.getController(world.provider.getDimension()).addBottle(this.getPos());
            MainMod.NETWORK.sendToAll(new SunshineUpdate(pos, world.provider.getDimension(), SunshineUpdate.Action.ADD));
            BlockBottledSunshine.updateLocalLighting(this.world, this.pos);
        }
    }
    private int tickCount = 3;
    private static final Random r = new Random();
    @Override
    public void update() {

        if(world.isRemote){
            tickCount--;
            if(tickCount == 0){
                double d0 = (double)pos.getX() + 0.5D;
                double d1 = (double)pos.getY() + 0.3D;
                double d2 = (double)pos.getZ() + 0.5D;

                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBottledSunshine(this.world, d0, d1, d2));
                tickCount = 2 + r.nextInt(3);
            }
        }
    }
}
