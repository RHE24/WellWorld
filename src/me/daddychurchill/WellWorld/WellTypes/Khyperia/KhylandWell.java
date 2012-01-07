package me.daddychurchill.WellWorld.WellTypes.Khyperia;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.WellWorld.WellArchetype;
import me.daddychurchill.WellWorld.Support.ByteChunk;

public class KhylandWell extends WellArchetype {

	// Port of Khyperia's TrippyTerrain/Khyland for testing purposes
	
	public KhylandWell(World world, long seed, int wellX, int wellZ) {
		super(world, seed, wellX, wellZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateChunk(ByteChunk chunk, int chunkX, int chunkZ) {

		SimplexNoiseGenerator simplex = new SimplexNoiseGenerator(randseed);
		//EC: use the one passed in instead
		//int chunkX = chunk.getX();
		//int chunkZ = chunk.getZ();
		Material[][][] blocks = new Material[16][128][16];

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 128; y++) {
					double noise = simplex.noise((chunkX * 16 + x) / 50.0f, y / 50.0f, (chunkZ * 16 + z) / 50.0f);
					noise += (64 - y) * (1 / 32f);
					blocks[x][y][z] = (noise > 0) ? Material.STONE : Material.AIR;
				}
			}
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 5; y < 127; y++) {
					if (blocks[x][y][z].equals(Material.STONE) && blocks[x][y + 1][z].equals(Material.AIR)) {
						blocks[x][y][z] = Material.GRASS;
						for (int height = 1; height < 5; height++)
							if (blocks[x][y - height][z].equals(Material.AIR) == false)
								blocks[x][y - height][z] = Material.DIRT;
					}
				}
			}
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 128; y++) {
					double noise = Math.abs(simplex.noise((chunkX * 16 + x) / 50.0f, y / 40.0f + 200,
							(chunkZ * 16 + z) / 50.0f));
					noise += Math.abs(simplex.noise((chunkX * 16 + x) / 50.0f, y / 40.0f + 400, (chunkZ * 16 + z) / 50.0f));
					if (noise < 0.2)
						blocks[x][y][z] = Material.AIR;
				}
			}
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				blocks[x][0][z] = Material.BEDROCK;
				for (int y = 1; y < 4; y++) {
					if (random.nextDouble() > y * 0.25)
						blocks[x][y][z] = Material.BEDROCK;
				}
			}
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 127; y++) {
					if (blocks[x][y][z].equals(Material.DIRT) && blocks[x][y + 1][z].equals(Material.AIR))
						blocks[x][y][z] = Material.GRASS;
				}
			}
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int xz = (x * 16 + z) * 128; //EC: small optimization
				for (int y = 0; y < 128; y++) {
					chunk.blocks[xz + y] = (byte) blocks[x][y][z].getId();
				}
			}
		}
	}

	@Override
	public void populateBlocks(Chunk chunk, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub

	}

}