package com.spicymemes.runes.network;

import com.spicymemes.runes.MainMod;
import com.spicymemes.runes.blocks.BlockBottledSunshine;
import com.spicymemes.runes.blocks.SunshineController;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sun.security.provider.Sun;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Spencer on 5/23/18.
 */
@Mod.EventBusSubscriber(modid= MainMod.MODID)
public class SunshineClientUpdater implements IMessageHandler<SunshineUpdate, IMessage>{
    //private static Method regenHeightmap = null;
    private static CopyOnWriteArrayList<SunshineUpdate> updates = new CopyOnWriteArrayList<>();
    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(SunshineUpdate message, MessageContext ctx) {
        /*if(regenHeightmap == null){
            for(Method m : Chunk.class.getMethods()){
                System.out.println(m.getName());
            }
            try {
                regenHeightmap = Chunk.class.getMethod("generateHeightMap");
                regenHeightmap.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }*/
        switch (message.getAction()){
            case ADD:
                SunshineController.addBottle(message.getPos()); break;
            case REMOVE:
                SunshineController.removeBottle(message.getPos()); break;
        }
        updates.add(message);
        //BlockBottledSunshine.updateLocalLighting(Minecraft.getMinecraft().world, message.getPos());
        System.out.println("Sunshine packet for " + message.getAction());
        return null;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            while(updates.size() > 0){
                SunshineUpdate up = updates.remove(0);
                BlockBottledSunshine.updateLocalLighting(Minecraft.getMinecraft().world, up.getPos());
            }
        }
    }
}
