package com.spicymemes.runes.magic;

import com.spicymemes.runes.MainMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Set;

/**
 * Created by Spencer on 5/29/18.
 */
@Mod.EventBusSubscriber(modid= MainMod.MODID)

public class RechargeHandler{

    private static final BiomeDictionary.Type[][] recordedTypes = {{BiomeDictionary.Type.COLD, BiomeDictionary.Type.HOT}, {BiomeDictionary.Type.WET, BiomeDictionary.Type.DRY}, {BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.DENSE}};

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            return;
        }
        if(event.player.getEntityWorld().isRemote){
            return;
        }
        NBTTagCompound data = event.player.getEntityData();
        World w = event.player.world;
        BlockPos pos = event.player.getPosition();
        Biome b = w.getBiome(pos);

        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(b);
        for(BiomeDictionary.Type[] t : recordedTypes){

        }
    }

    private static void initializePlayerData(NBTTagCompound compound){
        if(!compound.hasKey("spicyrunes_magic")){
            NBTTagCompound comp = new NBTTagCompound();

        }
    }
}
