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
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/**
 * Created by Spencer on 5/23/18.
 */
public class ParticleBottledSunshine extends Particle {
    ResourceLocation ploc = new ResourceLocation("spicyrunes:textures/particles/sun.png");
    TextureAtlasSprite sprite =Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(ploc.toString());
    private static final Random r = new Random();
    public ParticleBottledSunshine(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
        //TextureAtlasSprite sprite = new DirectTextureAtlasSprite(new ResourceLocation("spicyrunes:particles/particles"));
        //this.setParticleTexture(sprite);
        //this.setParticleTextureIndex(0);
        float speedMagnitude = 0.01f;
        float x = (float) r.nextGaussian();
        float y = (float) r.nextGaussian();
        float z = (float) r.nextGaussian();
        float mult = (float) (1/(Math.sqrt(x*x + y*y + z*z))) * speedMagnitude;
        x *= mult;
        y *= mult;
        z *= mult;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        this.canCollide = false;
        this.particleMaxAge = (int)(6.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
    }

    /*@Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ){
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().renderEngine.bindTexture(ploc);
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GL11.glDisable(GL11.GL_BLEND);
    }*/

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ)
    {
        //GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        float f3 =(float)((this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX) - (Minecraft.getMinecraft().player.motionX / 2f));
        float f4 = (float)((this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY) - (Minecraft.getMinecraft().player.motionY / 2f));
        float f5 = (float)((this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ) - (Minecraft.getMinecraft().player.motionZ / 2f));
        int i = this.getBrightnessForRender(partialTicks);
        int j11 = i >> 16 & 65535;
        int k11 = i & 65535;
        float size = 0.04f;//directScale > 0 ? directScale : (0.1F * (isCauldronTop ? 3.15f : this.particleScale));
        Minecraft.getMinecraft().getTextureManager().bindTexture(ploc);
        float k = (float)this.particleTextureIndexX / 16.0F;
        float k1 = 1;
        float k2 = (float)this.particleTextureIndexY / 16.0F;
        float k3 = 1;
        double[] t = {k1, k3, k1, k2, k, k2, k, k3};
        float f6 = MathHelper.clamp( world.getLight(new BlockPos(posX, posY, posZ)) / 16f, 0.3f, 1f);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        //buffer.color(1,1,1, (float) Math.sqrt(this.particleAge / this.particleMaxAge));
        buffer.pos((double)(f3 - rotX * size - rotXY * size), (double)(f4 - rotZ * size), (double)(f5 - rotYZ * size - rotXZ * size)).tex(t[0], t[1]).color(f6, f6, f6, this.particleAlpha).endVertex();
        buffer.pos((double)(f3 - rotX * size + rotXY * size), (double)(f4 + rotZ * size), (double)(f5 - rotYZ * size + rotXZ * size)).tex(t[2], t[3]).color(f6, f6, f6, this.particleAlpha).endVertex();
        buffer.pos((double)(f3 + rotX * size + rotXY * size), (double)(f4 + rotZ * size), (double)(f5 + rotYZ * size + rotXZ * size)).tex(t[4], t[5]).color(f6, f6, f6, this.particleAlpha).endVertex();
        buffer.pos((double)(f3 + rotX * size - rotXY * size), (double)(f4 - rotZ * size), (double)(f5 + rotYZ * size - rotXZ * size)).tex(t[6], t[7]).color(f6, f6, f6, this.particleAlpha).endVertex();
        Tessellator.getInstance().draw();
        //GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();

    }

    public void onUpdate()
    {
        super.onUpdate();
        this.particleAlpha = (float)Math.sqrt(1 - (float)this.particleAge / (float)this.particleMaxAge);
    }


    public int getFXLayer()
    {
        return 3;
    }
}
