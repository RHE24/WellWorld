package me.daddychurchill.WellWorld.WellTypes.NotUsed;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.WellWorld.WellArchetype;
import me.daddychurchill.WellWorld.Support.ByteChunk;

public class SimplexNoiseWell extends WellArchetype {

	private float xFactor = 50.0f;
	private float yFactor = 25.0f;
	private float zFactor = 50.0f;
	private byte stoneId = (byte) Material.STONE.getId();
	private byte airId = (byte) Material.AIR.getId();
	private SimplexNoiseGenerator generator;
	
	public SimplexNoiseWell(World world, long seed, int wellX, int wellZ) {
		super(world, seed, wellX, wellZ);
		generator = new SimplexNoiseGenerator(randseed);
	}

	@Override
	public void generateChunk(ByteChunk chunk, int chunkX, int chunkZ) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 128; y++) {
					double noise = generator.noise((chunkX * 16 + x) / xFactor, y / yFactor, (chunkZ * 16 + z) / zFactor);
					noise += (64 - y) * (1 / 32f);
					chunk.setBlock(x, y, z, noise > 0 ? stoneId : airId);
				}
			}
		}
	}

	@Override
	public void populateBlocks(Chunk chunk, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub

	}

}
