package andmod.MysteriousDungeon;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class World_WorldProvider extends WorldProvider {

	@Override
	public String getDimensionName() {
		return "MysteriousDungeon";
	}

	@Override
	public boolean canCoordinateBeSpawn( int x, int z ) {
		return false;
	}

	@Override
	public float[] calcSunriseSunsetColors( float par1, float par2 ) {
		return null;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public float getCloudHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return super.getCloudHeight();
	}

	@Override
	public boolean isSkyColored() {
		return false;
	}

	@Override
	public int getAverageGroundLevel() {
		return 128;	//checkme
	}

	@Override
	public double getVoidFogYFactor() {
		// TODO 自動生成されたメソッド・スタブ
		return super.getVoidFogYFactor();
	}

	@Override
	public String getWelcomeMessage() {
		return "Entering the Mysterious Dungeon...";
	}
	
	
	@Override
	public String getDepartMessage() {
		return "Leaving the Mysterious Dungeon...";
	}
	

	@Override
	public boolean isDaytime() {
		return false;
	}
	

	@Override
	public Vec3 getSkyColor( Entity cameraEntity, float partialTicks ) {
		return worldObj.getWorldVec3Pool().getVecFromPool( 0.0, 0.0, 0.0 );
	}
	

	@Override
	public float getStarBrightness( float par1 ) {
		return 0.0F;
	}

	
	@Override
	public void calculateInitialWeather() {
	}
	

	@Override
	public boolean canBlockFreeze( int x, int y, int z, boolean byWater ) {
		return false;
	}

	
	@Override
	public ChunkCoordinates getSpawnPoint() {
		// TODO 自動生成されたメソッド・スタブ
		return super.getSpawnPoint();
	}


	@Override
	public boolean canDoLightning( Chunk chunk ) {
		return false;
	}
	

	@Override
	public IRenderHandler getSkyRenderer() {
		// TODO 自動生成されたメソッド・スタブ
		return new Render_Empty();
	}

	
	@Override
	public IRenderHandler getCloudRenderer() {
		return new Render_Empty();
	}

	@Override
	public Vec3 drawClouds( float partialTicks ) {
		return worldObj.getWorldVec3Pool().getVecFromPool( 0.0, 0.0, 0.0 );
	}

	
	@Override
	public Vec3 getFogColor( float angle, float par2 ) {
		return worldObj.getWorldVec3Pool().getVecFromPool( 0.5, 0.5, 0.5 );
	}

	
	@Override
	public boolean getWorldHasVoidParticles() {
		return false;
	}

	
	@Override
	public void updateWeather() {
	}

	
	@Override
	public long getSeed() {
		return Long.reverse( worldObj.getWorldInfo().getSeed() );	//checkme
	}

	
	@Override
	public double getHorizon() {
		return 127.0;		//checkme
	}

	
	@Override
	protected void registerWorldChunkManager() {
		worldChunkMgr = new World_ChunkManager( new World_BiomeGen( AndMod_MysteriousDungeon.biomeID ) );
		dimensionId = AndMod_MysteriousDungeon.dimensionID;
		hasNoSky = true;
	}

	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new World_ChunkProvider( worldObj, worldObj.getSeed() );
	}

	
	@Override
	public int getActualHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return 256;
	}

}
