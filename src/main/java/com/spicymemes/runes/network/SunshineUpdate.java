package com.spicymemes.runes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by Spencer on 5/23/18.
 */
public class SunshineUpdate implements IMessage{

    public enum Action{
        ADD, REMOVE
    }

    private BlockPos p;
    private Action a;
    private int d;

    public SunshineUpdate(BlockPos pos, int dim, Action action){
        this.p = pos;
        this.a = action;
        this.d = dim;
    }

    public SunshineUpdate(){
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.a = Action.values()[buf.readByte()];
        this.p = BlockPos.fromLong(buf.readLong());
        this.d = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.a.ordinal());
        buf.writeLong(this.p.toLong());
        buf.writeInt(this.d);
    }

    public BlockPos getPos(){
        return p;
    }

    public Action getAction(){
        return a;
    }

    public int getDim(){
        return d;
    }
}
