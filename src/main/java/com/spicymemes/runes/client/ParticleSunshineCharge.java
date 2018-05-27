package com.spicymemes.runes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/**
 * Created by Spencer on 5/23/18.
 */
public class ParticleSunshineCharge extends Particle {
    ResourceLocation ploc = new ResourceLocation("spicyrunes:textures/particles/sun.png");
    private double inX, inY, inZ, outX, outY, outZ;
    private static final double IN_JITTER = 0.05;
    private static final double OUT_JITTER = 0.05;
    private static final double ARC_JITTER = 0.25;
    private static final Random r = new Random();

    private double arcOff = r.nextDouble() * ARC_JITTER;

    public ParticleSunshineCharge(World worldIn, double posXIn, double posYIn, double posZIn, double posXOut, double posYOut, double posZOut, int duration) {
        super(worldIn, posXIn, posYIn, posZIn);
        inX = posXIn + 2 * (r.nextDouble() - 0.5) * IN_JITTER;
        inY = posYIn + 2 * (r.nextDouble() - 0.5) * IN_JITTER;
        inZ = posZIn + 2 * (r.nextDouble() - 0.5) * IN_JITTER;
        outX = posXOut + 2 * (r.nextDouble() - 0.5) * OUT_JITTER;
        outY = posYOut + 2 * (r.nextDouble() - 0.5) * OUT_JITTER;
        outZ = posZOut + 2 * (r.nextDouble() - 0.5) * OUT_JITTER;
        this.particleMaxAge = duration;
        this.particleAge = 0;
        this.canCollide = false;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ)
    {
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        //GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        //float percent = (this.particleAge + partialTicks) / this.particleMaxAge;
        //float f3 = (float) (percent * this.outX + (1.0 - percent) * this.inX);
        //float f4 = (float) (percent * this.outY + (1.0 - percent) * this.inY);
        //float f5 = (float) (percent * this.outZ + (1.0 - percent) * this.inZ);
        float f3 =(float)((this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX) - (Minecraft.getMinecraft().player.motionX / 2f));
        float f4 = (float)((this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY) - (Minecraft.getMinecraft().player.motionY / 2f));
        float f5 = (float)((this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ) - (Minecraft.getMinecraft().player.motionZ / 2f));
        //int i = this.getBrightnessForRender(partialTicks);
        //int i = 0xffffff;
        //int j11 = i >> 16 & 65535;
        //int k11 = i & 65535;
        float size = 0.04f;//directScale > 0 ? directScale : (0.1F * (isCauldronTop ? 3.15f : this.particleScale));
        Minecraft.getMinecraft().getTextureManager().bindTexture(ploc);
        float k = (float)this.particleTextureIndexX / 16.0F;
        float k1 = 1;
        float k2 = (float)this.particleTextureIndexY / 16.0F;
        float k3 = 1;
        double[] t = {k1, k3, k1, k2, k, k2, k, k3};
        //float f6 = MathHelper.clamp( world.getLight(new BlockPos(posX, posY, posZ)) / 16f, 0.3f, 1f);
        float f6 = 1.0f;
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        //buffer.color(1,1,1, (float) Math.sqrt(this.particleAge / this.particleMaxAge));
        buffer.pos((double)(f3 - rotX * size - rotXY * size), (double)(f4 - rotZ * size), (double)(f5 - rotYZ * size - rotXZ * size)).tex(t[0], t[1]).color(f6, f6, f6, this.particleAlpha).endVertex();
        buffer.pos((double)(f3 - rotX * size + rotXY * size), (double)(f4 + rotZ * size), (double)(f5 - rotYZ * size + rotXZ * size)).tex(t[2], t[3]).color(f6, f6, f6, this.particleAlpha).endVertex();
        buffer.pos((double)(f3 + rotX * size + rotXY * size), (double)(f4 + rotZ * size), (double)(f5 + rotYZ * size + rotXZ * size)).tex(t[4], t[5]).color(f6, f6, f6, this.particleAlpha).endVertex();
        buffer.pos((double)(f3 + rotX * size - rotXY * size), (double)(f4 - rotZ * size), (double)(f5 + rotYZ * size - rotXZ * size)).tex(t[6], t[7]).color(f6, f6, f6, this.particleAlpha).endVertex();
        Tessellator.getInstance().draw();
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
        //GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();



    }

    /*public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ploc);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        Tessellator.getInstance().draw();
    }*/

    public void onUpdate()
    {
        //super.onUpdate();
        //this.particleAlpha = (float)Math.sqrt(1 - (float)this.particleAge / (float)this.particleMaxAge);
        double percent = Math.pow(((double) this.particleAge) / ((double)this.particleMaxAge), 2.0/3.0);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.posX = percent * this.outX + (1.0 - percent) * this.inX;
        this.posY = percent * this.outY + (1.0 - percent) * this.inY + (percent) * (1.0 - percent) * 2 * (1 + arcOff);
        this.posZ = percent * this.outZ + (1.0 - percent) * this.inZ;
        this.particleAge++;
        if(this.particleAge == this.particleMaxAge){
            this.setExpired();
        }
    }


    public int getFXLayer()
    {
        return 3;
    }
}
