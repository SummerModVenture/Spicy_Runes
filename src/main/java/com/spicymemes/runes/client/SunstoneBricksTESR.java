package com.spicymemes.runes.client;

import com.spicymemes.runes.blocks.ModBlocks;
import com.spicymemes.runes.tileentities.TileEntitySunstoneBricks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.opengl.*;

import java.io.InputStream;
import java.nio.file.Files;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Spencer on 5/24/18.
 */
public class SunstoneBricksTESR extends TileEntitySpecialRenderer<TileEntitySunstoneBricks>{

    private IModel block;
    private IBakedModel bakedBlock;
    //ResourceLocation ploc = new ResourceLocation("spicyrunes:textures/blocks/sunstone_stupid.png");
    ResourceLocation ploc = new ResourceLocation("spicyrunes:textures/blocks/sunstone_bricks.png");

    private IBakedModel getBakedModelBlock() {

        if (bakedBlock == null) {
            //bakedBlock = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(new ItemStack(ModBlocks.sunstoneBricks));
            try {
                block = ModelLoaderRegistry.getModel(new ResourceLocation("spicyrunes:block/sunstone_bricks_stupid"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            bakedBlock = block.bake(TRSRTransformation.identity(), DefaultVertexFormats.BLOCK,
                    location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
        }
        return bakedBlock;
    }

    private int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if (shader == 0)
                return 0;
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(filename)).getInputStream();

            ArrayList<Byte> buff = new ArrayList<Byte>();
            while(is.available() != 0){
                byte[] toAdd = new byte[is.available()];
                is.read(toAdd);
                for(byte b : toAdd){
                    buff.add(b);
                }
            }
            byte[] read = new byte[buff.size()];
            for(int i = 0; i < read.length; i++){
                read[i] = buff.get(i);
            }
            String sh = new String(read);
            System.out.println("loaded shader file");
            System.out.println(sh);
            ARBShaderObjects.glShaderSourceARB(shader, sh);
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
            return shader;
        } catch (Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }

    private int vs, fs;
    private int getVertShader(){
        if(vs == 0){
            try {
                vs = createShader("spicyrunes:shaders/sunstone_bricks.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vs;
    }

    private int getFragShader(){
        if(fs == 0){
            try {
                fs = createShader("spicyrunes:shaders/sunstone_bricks.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fs;
    }

    private int program = -1;
    private int widthLoc, heightLoc, sunLoc;
    public SunstoneBricksTESR(){
        super();
        getVertShader();
        getFragShader();
        program = ARBShaderObjects.glCreateProgramObjectARB();
        ARBShaderObjects.glAttachObjectARB(program, getVertShader());
        ARBShaderObjects.glAttachObjectARB(program, getFragShader());

        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }

        widthLoc = GL20.glGetUniformLocation(program, "width");
        heightLoc = GL20.glGetUniformLocation(program, "height");
        sunLoc = GL20.glGetUniformLocation(program, "sunVec");

        //GL20.glUniform1f(widthLoc, Display.getWidth());
        //GL20.glUniform1f(heightLoc, Display.getHeight());
        //updateSunLoc();

        System.out.println("Program ID is " + program);

    }

    private void updateSunLoc(){
        World w = Minecraft.getMinecraft().world;
        if(w != null){
            float ang = (float) ((w.getCelestialAngle(0) + 0.25f) * 2 * Math.PI);
            GL20.glUniform3f(sunLoc, (float)Math.cos(ang), (float)Math.sin(ang), 0);
        }
        else{
            GL20.glUniform3f(sunLoc, 0, 1, 0);
        }
    }

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    @Override
    public void render(TileEntitySunstoneBricks te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        /*if(Display.wasResized()){
            GL20.glUniform1f(widthLoc, Display.getWidth());
            GL20.glUniform1f(heightLoc, Display.getHeight());
        }*/
        ARBShaderObjects.glUseProgramObjectARB(program);

        updateSunLoc();

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        RenderHelper.disableStandardItemLighting();

        //GlStateManager.enableBlend();

        //Minecraft.getMinecraft().getTextureManager().bindTexture(ploc);
        this.bindTexture(ploc);
        //GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_DST_COLOR);

        World world = getWorld();
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        //Minecraft.getMinecraft().getTextureManager().bindTexture(ploc);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        //Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(te.getWorld(), getBakedModelBlock(), te.getWorld().getBlockState(te.getPos()), te.getPos(), buffer, false);

        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                world,
                getBakedModelBlock(),
                world.getBlockState(te.getPos()),
                te.getPos(),
                Tessellator.getInstance().getBuffer(), false);
        //Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel
        tessellator.draw();


        //GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();

        GlStateManager.translate(-x, -y, -z);
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
        ARBShaderObjects.glUseProgramObjectARB(0);
    }
}
