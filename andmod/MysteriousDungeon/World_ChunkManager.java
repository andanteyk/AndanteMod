package andmod.MysteriousDungeon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

import com.google.common.collect.Lists;

public class World_ChunkManager extends WorldChunkManager {

	private final BiomeGenBase biome;
	
	public World_ChunkManager( BiomeGenBase biome ) {
		this.biome = biome;
	}

	@Override
	public List getBiomesToSpawnIn() {
		return Lists.newArrayList( biome );
	}

	@Override
	public BiomeGenBase getBiomeGenAt( int cx, int cz ) {
		return biome;
	}

	@Override
	public float[] getRainfall( float[] rainfalls, int cx, int cz, int width, int length ) {
		if ( rainfalls == null || rainfalls.length < width * length )
			rainfalls = new float[width * length];
		
		Arrays.fill( rainfalls, biome.getFloatRainfall() );
		return rainfalls;
	}

	@Override
	public float[] getTemperatures( float[] temperatures, int cx, int cz, int width, int length ) {
		if ( temperatures == null || temperatures.length < width * length )
			temperatures = new float[width * length];

		Arrays.fill( temperatures, biome.getFloatTemperature() );
		return temperatures;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration( BiomeGenBase[] biomes, int cx, int cz, int width, int length) {

		if ( biomes == null || biomes.length < width * length)
			biomes = new BiomeGenBase[width * length];
		Arrays.fill( biomes,  biome );
		return biomes;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int cx, int cz, int width, int length, boolean par6) {

		return getBiomesForGeneration( biomes, cx, cz, width, length );
	}

	@Override
	public boolean areBiomesViable( int x, int y, int z, List biomes ) {
		return biomes.contains( biome );
	}

	@Override
	public ChunkPosition findBiomePosition( int x, int y, int z, List biomes, Random random ) {
		return biomes.contains( biome ) ? new ChunkPosition( x - z + random.nextInt( z * 2 + 1 ), 0, y - z + random.nextInt( z * 2 + 1 ) ) : null;
	}

	
}
