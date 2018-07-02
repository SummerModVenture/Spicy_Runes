package com.spicymemes.runes;

import com.spicymemes.runes.blocks.ModBlocks;
import com.spicymemes.runes.items.ModItems;
import com.spicymemes.runes.tileentities.ModTileEntities;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

@Mod(modid = MainMod.MODID, name = MainMod.NAME, version = MainMod.VERSION, dependencies = "required-after:spicycore@[1.0,)")
public class MainMod
{
    public static final String MODID = "spicyrunes";
    public static final String NAME = "Spicy Runes";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "com.spicymemes.runes.ClientProxy", serverSide = "com.spicymemes.runes.CommonProxy", modId = MODID)
    static CommonProxy proxy;
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //System.out.println(this.getClass().getResourceAsStream("/com/spicymemes/runes/coremod/World.class"));
        /*ClassReplacer cr = new ClassReplacer("net.minecraft.world.World");
        try {
            cr.loadClass(World.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        ModBlocks.init();
        ModItems.init();
        ModTileEntities.init();
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        proxy.init();
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
