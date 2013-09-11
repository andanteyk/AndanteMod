package andmod.MysteriousDungeon;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class World_ChunkProvider implements IChunkProvider {

	private World worldObj;
	private final Random rand;
	
	
	
	public World_ChunkProvider( World world, long seed ) {
		worldObj = world;
		rand = new Random( seed );
	}

	@Override
	public boolean chunkExists( int cx, int cz ) {
		return true;	//checkme
	}

	@Override
	public Chunk provideChunk( int cx, int cz ) {
		rand.setSeed( cx * 341873128712L + cz * 132897987541L );
		short[] blocks = new short[16 * 16 * 256];
		byte[] meta = new byte[16 * 16 * 256];
		
		for ( int x = 0; x < 16; x ++ ) { 
			for ( int z = 0; z < 16; z ++ ) {
				for ( int y = 0; y < 256; y ++ ) {
					
					int id = y << 8 | z << 4 | x;
					int h = y % 8;
					
					if ( h == 7 )
						blocks[id] = (short)Block.bedrock.blockID;
					else if ( ( x == 0 || z == 0 || x == 15 || z == 15 || h == 0 || h == 6 ) &&
							 !( 1 <= h && h <= 3 && ( ( ( x == 0 || x == 15 ) && ( 7 <= z && z <= 8 ) ) || ( z == 0 || z == 15 ) && ( 7 <= x && x <= 8 ) ) ) )
						blocks[id] = (short)Block.stone.blockID;
					else if ( h == 3 && ( ( ( x == 1 || x == 14 ) && ( z == 6 || z == 9 ) ) || ( ( ( z == 1 || z == 14 ) && ( x == 6 || x == 9 ) ) ) ) ) {
						blocks[id] = (short)Block.torchWood.blockID;
						meta[id] = (byte)( x == 1 ? 1 : ( x == 14 ? 2 : ( z == 1 ? 3 : 4 ) ) );
					} else blocks[id] = 0;
						
				}
			}	
		}
		
		
		return new Chunk( worldObj, blocks, meta, cx, cz );
	}

	@Override
	public Chunk loadChunk( int cx, int cz ) {
		return provideChunk( cx, cz );
	}

	@Override
	public void populate( IChunkProvider icp, int cx, int cz ) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean saveChunks( boolean flag, IProgressUpdate iprogressupdate ) {
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() {
		//checkme
		return false;
	}

	@Override
	public boolean canSave() {
		
		return true;
	}

	@Override
	public String makeString() {
		return "MysteriousDungeon_ChunkProvider";
	}

	@Override
	public List getPossibleCreatures( EnumCreatureType enumcreaturetype, int bx, int by, int bz ) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ChunkPosition findClosestStructure( World world, String name, int bx, int by, int bz ) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		//checkme
		return 0;
	}

	@Override
	public void recreateStructures( int bx, int bz ) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void func_104112_b() {
		
	}

}
