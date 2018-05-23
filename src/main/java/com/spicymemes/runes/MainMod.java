package com.spicymemes.runes;

import com.spicymemes.runes.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;

@Mod(modid = MainMod.MODID, name = MainMod.NAME, version = MainMod.VERSION, dependencies = "required-after:spicycore@[1.0,)")
public class MainMod
{
    public static final String MODID = "spicyrunes";
    public static final String NAME = "Spicy Runes";
    public static final String VERSION = "1.0";

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
        ArrayList list = new ArrayList();
        try {
            Method add = list.getClass().getMethod("add", Object.class);

            System.out.println(add);
            System.out.println(add.getDeclaringClass());
            //System.out.println(add.);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        ModBlocks.init();
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
