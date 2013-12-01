package andmod.MysteriousDungeon;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import andmod.AndCore.RandomXor;

public class World_ChunkProvider implements IChunkProvider {

	private World worldObj;
	private final Random rand;
	private int fieldSize;
	
	
	public World_ChunkProvider( World world, long seed ) {
		worldObj = world;
		rand = new Random( seed );
		fieldSize = 16;
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
		
		
		if ( true ) {
			generateTerrain( cx, cz, blocks, meta );
		
		} else { 
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
		}
		Chunk chunk = new Chunk( worldObj, blocks, meta, cx, cz );
		chunk.resetRelightChecks();
		return chunk;
	}

	
	@Override
	public Chunk loadChunk( int cx, int cz ) {
		return provideChunk( cx, cz );
	}

	
	@Override
	public void populate( IChunkProvider icp, int cx, int cz ) {
		BlockSand.fallInstantly = true;
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre( icp, worldObj, worldObj.rand, cx, cz, false ) );

        //TODO: biome decoration!
        
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post( icp, worldObj, worldObj.rand, cx, cz, false ) );
        BlockSand.fallInstantly = false;

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
	public String makeString() {	//checkme
		//return "MysteriousDungeon_ChunkProvider";
		return "RandomLevelSource";
	}

	
	@Override
	public List getPossibleCreatures( EnumCreatureType ecreature, int bx, int by, int bz ) {
		// TODO 自動生成されたメソッド・スタブ
		//return null;
		
		BiomeGenBase biomegen = worldObj.getBiomeGenForCoords( bx, bz );
		return biomegen.getSpawnableList( ecreature );
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

	
	protected void generateTerrain( int cx, int cz, short[] block, byte[] meta ) {
		
		int floor = 32;
		int height = 256 / 32;
		RandomXor randm = new RandomXor( MathHelper.floor_double( cx / 16 ) * 16 * 341873128712L + MathHelper.floor_double( cz / 16 ) * 132897987541L );
		byte field[][] = new byte[fieldSize][fieldSize];
		
		randm.consume( randm.nextInt( 64, 256 ) );
		
		
		for ( int f = 0; f < floor; f ++ ) {
			
			RandomXor randh = new RandomXor( cx * 341873128712L + cz * 132897987541L + f * 0xDEADBEAFL );
			RandomXor randt = new RandomXor( cx * 341873128712L + cz * 132897987541L + ( f + 1 ) * 0xDEADBEAFL );
			int hx = bound( cx, fieldSize ), hy = f * height, hz = bound( cz, fieldSize );
			int topstairx, topstairy, botstairx, botstairy;
			
			randh.consume( randh.nextInt( 64, 256 ) );
			randt.consume( randt.nextInt( 64, 256 ) );
			
			topstairx = randt.nextInt( fieldSize );
			topstairy = randt.nextInt( fieldSize );
			botstairx = randh.nextInt( fieldSize );
			botstairy = randh.nextInt( fieldSize );
			
			for ( int z = 0; z < fieldSize; z ++ )
				for ( int x = 0; x < fieldSize; x ++ )
					field[z][x] = 3;
			
			
			generateMaze( field, randm.nextInt( fieldSize ), randm.nextInt( fieldSize ), randm );
			
			for ( int i = 0; i < fieldSize * 2; i ++ ) 
				field[randm.nextInt( fieldSize )][randm.nextInt( fieldSize )] &= ( randm.nextBoolean() ? 0xFE : 0xFD );
			
			
			for ( int y = hy; y < hy + 256 / floor; y ++ )
				for ( int z = 0; z < 16; z ++ )
					for ( int x = 0; x < 16; x ++ ) {
						block[getChunkIndex( x, y, z )] = 0;
						meta[getChunkIndex( x, y, z )] = 0;
					}
			
			if ( topstairx != hx || topstairy != hy )
				fillChunk( block, 1, 0, 15, hy, hy, 0, 15 );
			if ( botstairx != hx || botstairy != hy )
				fillChunk( block, 1, 0, 15, hy + height - 1, hy + height - 1, 0, 15 );
			
			if ( hx == 0 || ( field[hz][hx] & 1 ) != 0 ) {
				fillChunk( block, 1, 0, 1, hy, hy + height - 1, 0, 15 );
			}
			if ( hz == 0 || ( field[hz][hx] & 2 ) != 0 ) {
				fillChunk( block, 1, 0, 15, hy, hy + height - 1, 0, 1 );
			}
			if ( hx == fieldSize - 1 || ( field[hz][hx + 1] & 1 ) != 0 ) {
				fillChunk( block, 1, 14, 15, hy, hy + height - 1, 0, 15 );
			}
			if ( hz == fieldSize - 1 || ( field[hz + 1][hx] & 2 ) != 0 ) {
				fillChunk( block, 1, 0, 15, hy, hy + height - 1, 14, 15 );
			}
		}
	}
	
	protected void generateMaze( byte[][] field, int x, int z, RandomXor rand ) {
		
		field[z][x] |= 4;
		
		
		//shuffle
		int dir[] = new int[4];
		int n = 4;
		
		for ( int i = 0; i < 4; i ++ )
			dir[i] = i;
		
		while ( n > 1 ) {
			n --;
			int k = rand.nextInt( n + 1 );
			int t = dir[k];
			dir[k] = dir[n];
			dir[n] = t;
		}
			
		
		for ( int i : dir ) {
			switch ( i ) {
			case 0:
				if ( x > 0 && field[z][x - 1] == 3 ) {
					field[z][x] = (byte)( field[z][x] & 0xFE );
					generateMaze( field, x - 1, z, rand );
				} break;
				
			case 1:
				if ( z > 0 && field[z - 1][x] == 3 ) {
					field[z][x] = (byte)( field[z][x] & 0xFD );
					generateMaze( field, x, z - 1, rand );
				} break;
				
			case 2:
				if ( x < fieldSize - 1 && field[z][x + 1] == 3 ) {
					field[z][x + 1] = (byte)( field[z][x + 1] & 0xFE );
					generateMaze( field, x + 1, z, rand );
				} break;
			
			case 3:
				if ( z < fieldSize - 1 && field[z + 1][x] == 3 ) {
					field[z + 1][x] = (byte)( field[z + 1][x] & 0xFD );
					generateMaze( field, x, z + 1, rand );
				} break;
			}
		}
	}
	
	
	protected int getChunkIndex( int x, int y, int z ) {
		return y << 8 | z << 4 | x ;
	}
	
	protected int bound( int value, int max ) {
		value %= max;
		return value < 0 ? value + max : value;
	}
	
	protected void fillChunk( short[] chunk, int value, int x1, int x2, int y1, int y2, int z1, int z2 ) {
		for ( int y = y1; y <= y2; y ++ )
			for ( int z = z1; z <= z2; z ++ )
				for ( int x = x1; x <= x2; x ++ )
					chunk[ getChunkIndex( x, y, z ) ] = (short)value;
	}
}
