package com.spicymemes.runes.tileentities;

import com.spicymemes.runes.blocks.ModBlocks;
import com.spicymemes.runes.client.ParticleBottledSunshine;
import com.spicymemes.runes.client.ParticleSunshineCharge;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

/**
 * Created by Spencer on 5/26/18.
 */
public class TileEntitySunPedestal extends TileEntity implements ITickable{
    private static final int RITUAL_DURATION = 200;
    private static final int PARTICLE_DURATION = 80;
    private static final int PARTICLE_JITTER = 40;
    private static final int ALTAR_RADIUS = 4;
    private static final Random r = new Random();

    private static final int delay = 2;

    private boolean wasInRangeLastTick = false;
    private int tickCount = 40;

    private boolean inRitual = false;
    private boolean pastRitualProbation = false;
    private int ritualProgress = 0;
    @Override
    public void update() {
        if(this.world.isRemote){
            if(this.inRitual && (RITUAL_DURATION - ritualProgress) > PARTICLE_DURATION && pastRitualProbation) {
                if (tickCount % delay == 0) {
                    if (Math.random() > 0.66)
                        spawnChargeParticleWhereApplicable(this.pos.add(ALTAR_RADIUS, 1, 0));
                    if (Math.random() > 0.66)
                        spawnChargeParticleWhereApplicable(this.pos.add(-ALTAR_RADIUS, 1, 0));
                    if (Math.random() > 0.66)
                        spawnChargeParticleWhereApplicable(this.pos.add(0, 1, ALTAR_RADIUS));
                    if (Math.random() > 0.66)
                        spawnChargeParticleWhereApplicable(this.pos.add(0, 1, -ALTAR_RADIUS));
                }
                tickCount++;
            }


            if(Minecraft.getMinecraft().player.getPosition().distanceSq(this.pos) < 100){
                if(!wasInRangeLastTick){
                    Minecraft.getMinecraft().ingameGUI.displayTitle("Altar of the Sun", null, 10, 20, 10);
                }
                wasInRangeLastTick = true;
            }
            else{
                wasInRangeLastTick = false;
            }
        }
        if(inRitual){
            if(shouldRitualContinue() || ritualProgress == 0){
                if(ritualProgress >= RITUAL_DURATION){
                    this.world.setBlockState(this.pos.add(0, 1, 0), ModBlocks.bottledSunshine.getDefaultState());
                    resetRitual();
                }
                if(ritualProgress == 1){
                    this.pastRitualProbation = true;
                    this.world.neighborChanged(pos.offset(EnumFacing.DOWN), this.blockType, pos);
                }
                ritualProgress++;
            }
            else {
                resetRitual();
            }
        }
    }

    private void spawnChargeParticleWhereApplicable(BlockPos pos){
        if(this.world.getBlockState(pos).getBlock() == ModBlocks.sunStatue){
            spawnChargeParticleFrom(pos);
        }
    }

    private boolean isAltarComplete(){
        int count = 0;
        //System.out.println(this.world.rayTraceBlocks(new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ()), new Vec3d(this.pos.getX() + ALTAR_RADIUS, this.pos.getY() + 2, this.pos.getZ())));
        //System.out.println(this.pos);
        if(this.world.getBlockState(pos.add(ALTAR_RADIUS, 1, 0)).getBlock() == ModBlocks.sunStatue)
            count++;
        if(this.world.getBlockState(pos.add(-ALTAR_RADIUS, 1, 0)).getBlock() == ModBlocks.sunStatue)
            count++;
        if(this.world.getBlockState(pos.add(0, 1, ALTAR_RADIUS)).getBlock() == ModBlocks.sunStatue)
            count++;
        if(this.world.getBlockState(pos.add(0, 1, -ALTAR_RADIUS)).getBlock() == ModBlocks.sunStatue)
            count++;
        return count == 3;
    }

    public void attemptRitual(){
        /*if(shouldRitualContinue() && !this.inRitual){
            System.out.println("Attempting ritual");
            this.inRitual = true;
            return true;
        }
        System.out.println("Attempt failed");*/
        inRitual = true;
    }

    public boolean isPastRitualProbation(){
        return this.pastRitualProbation;
    }

    private void resetRitual(){
        this.inRitual = false;
        this.ritualProgress = 0;
        this.pastRitualProbation = false;
        this.world.neighborChanged(pos.offset(EnumFacing.DOWN), this.blockType, pos);
    }

    private boolean shouldRitualContinue(){
        return isAltarComplete() && (this.world.getBlockState(pos.add(0, 1, 0)).getBlock() == ModBlocks.emptyBottle) && this.world.isDaytime() && !this.world.isRaining();
    }

    private void spawnChargeParticleFrom(BlockPos pos){
        double fromX = pos.getX() + 0.5D;
        double fromY = pos.getY() + 0.3D;
        double fromZ = pos.getZ() + 0.5D;
        double toX = this.pos.getX() + 0.5D;
        double toY = this.pos.getY() + 1.3D;
        double toZ = this.pos.getZ() + 0.5D;
        Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSunshineCharge(this.world, fromX, fromY, fromZ, toX, toY, toZ, PARTICLE_DURATION + r.nextInt(PARTICLE_JITTER) - PARTICLE_JITTER / 2));
        //Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBottledSunshine(this.world, fromX, fromY, fromZ));
    }
}
