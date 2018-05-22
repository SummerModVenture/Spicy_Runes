package com.spicymemes.runes.worldgen;

import com.spicymemes.runes.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import com.google.common.base.Predicate;

import java.util.Random;

/**
 * Created by Spencer on 5/22/18.
 */
public class OreGenerator implements IWorldGenerator {
    Block b;
    Predicate<IBlockState> repl;
    int did, vs, vc, miny, maxy;

    public OreGenerator(Block ore, Predicate<IBlockState> replacement, int dimensionID, int veinSize, int veinCount, int maxY, int minY){
        b = ore;
        did = dimensionID;
        vs = veinSize;
        vc = veinCount;
        miny = minY;
        maxy = maxY;
        repl = replacement;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()){
            case 0: genSurface(world, random, chunkX, chunkZ);
        }
    }

    private void genSurface(World world, Random random, int chunkX, int chunkZ){
        addOreSpawn(ModBlocks.testBlock.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16, vs, vc, miny, maxy, this.repl);
    }

    private void addOreSpawn(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chance, int minY, int maxY, Predicate<IBlockState> blockToSpawnIn){
        int diffMinMaxY = maxY - minY;
        for(int x = 0; x < chance; x++){
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(diffMinMaxY);
            int posZ = blockZPos + random.nextInt(maxZ);
            (new WorldGenMinable(block, maxVeinSize, blockToSpawnIn)).generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }
}
