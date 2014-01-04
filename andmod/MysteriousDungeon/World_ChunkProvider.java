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
	private int fieldSize;		//迷路の広さ。標準16
	private int corwidth;		//通路の太さ。標準2, 1-6
	private int floornum;		//階層数。標準32, 2の累乗で指定
	
	
	public World_ChunkProvider( World world, long seed ) {
		worldObj = world;
		rand = new Random( seed );
		fieldSize = 16;
		corwidth = 2;
		floornum = 32;
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
		
		
		//if ( true ) {
			generateTerrain( cx, cz, blocks, meta );
		
		/*
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
		*/
			
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
		
		int height = 256 / floornum;	//1階層当たりの高さ
		int mazex = MathHelper.floor_double( (double)cx / fieldSize );	//迷路固有座標 x
		int mazez = MathHelper.floor_double( (double)cz / fieldSize );	//迷路固有座標 z
		long seedm = worldObj.getSeed() ^ ( mazex * 341873128712L + mazez * 132897987541L );
		
		byte field[][] = new byte[fieldSize][fieldSize];	//chunkの迷路状態
		RandomXor randm = new RandomXor( seedm );	//迷路固有の乱数
		
		randm.consume();
		
		
		for ( int f = 0; f < floornum; f ++ ) {	//フロアごとの処理
			
			int inx = bound( cx, fieldSize );	//迷路内部のchunk座標 x
			int inz = bound( cz, fieldSize );	//迷路内部のchunk座標 z
			int fheight = f * height;			//フロアの高さ
			RandomXor randfc = new RandomXor( seedm ^ ( f * 0xDEADBEAFL ) );			//現在のフロアの乱数（階段処理用）
			RandomXor randft = new RandomXor( seedm ^ ( ( f + 1 ) * 0xDEADBEAFL ) );	//上のフロアの乱数（階段処理用）
			
			randfc.consume();
			randft.consume();
			
			//迷路データの初期化
			for ( int z = 0; z < fieldSize; z ++ )
				for ( int x = 0; x < fieldSize; x ++ )
					field[z][x] = 3;
			
			
			//迷路データの生成
			generateMaze( field, randm.nextInt( fieldSize ), randm.nextInt( fieldSize ), randm );
			
			//ちょっとつながりを増やす
			for ( int i = 0; i < fieldSize * 2; i ++ )
				field[randm.nextInt( fieldSize )][randm.nextInt( fieldSize )] &= ( randm.nextBoolean() ? 0xFE : 0xFD );
		
			
			//chunk初期化
			for ( int y = 0; y < height; y ++ ) {
				for ( int z = 0; z < 16; z ++ ) {
					for ( int x = 0; x < 16; x ++ ) {
						
						int blockID, blockMT;
						if ( y == height - 1 ) {
							blockID = Block.bedrock.blockID;
							blockMT = 0;
						} else {
							blockID = Block.stone.blockID;
							blockMT = 0;
						}
						
						setBlock( block, meta, x, fheight + y, z, blockID, blockMT );
					}
				}
			}
			
			
			{
				RandomXor randc = new RandomXor( seedm ^ ( inx * 341873128712L + inz * 132897987541L ) );
				RandomXor randr = new RandomXor( seedm ^ ( ( inx + 1 ) * 341873128712L + inz * 132897987541L ) );
				RandomXor randb = new RandomXor( seedm ^ ( inx * 341873128712L + ( inz + 1 ) * 132897987541L ) );
				
				randc.consume();
				randr.consume();
				randb.consume();
				randb.consume( 1 );
				
				//接続部座標
				int connectLeft   = randc.nextInt( 1 + corwidth, 16 - 2 - corwidth * 2 );
				int connectTop    = randc.nextInt( 1 + corwidth, 16 - 2 - corwidth * 2 );
				int connectRight  = randr.nextInt( 1 + corwidth, 16 - 2 - corwidth * 2 );
				int connectBottom = randb.nextInt( 1 + corwidth, 16 - 2 - corwidth * 2 );
				
				int isRoom = randc.nextBoolean( 0.4 ) ? 1 : 0;
				
				int rpath = 0;		//開通時1; 12時から時計回り(1=上, 2=右, 4=下, 8=左)
				
				if ( inz > 0 && ( field[inz][inx] & 2 ) == 0 )
					rpath += 1;
				if ( inx < fieldSize - 1 && ( field[inz][inx + 1] & 1 ) == 0 )
					rpath += 2;
				if ( inz < fieldSize - 1 && ( field[inz + 1][inx] & 2 ) == 0 )
					rpath += 4;
				if ( inx > 0 && ( field[inz][inx] & 1 ) == 0 )
					rpath += 8;
				
				
				//通路の行き止まりを減らします。
				if ( ( rpath == 1 || rpath == 2 || rpath == 4 || rpath == 8 ) && randc.nextBoolean( 0.8 ) )
					isRoom = 1;
				
				
				//十字路未実装のため、強制的に部屋にします。
				if ( rpath == 15 )
					isRoom = 1;
				
				
				if ( inx == randfc.nextInt( fieldSize ) ) {
					if ( inz == randfc.nextInt( fieldSize ) ) {
						//下り階段
						if ( f > 0 )
							isRoom |= 3;
					}
				} else randfc.consume( 1 );
				
				if ( inx == randft.nextInt( fieldSize ) ) {
					if ( inz == randft.nextInt( fieldSize ) ) {
						//上り階段
						if ( f < floornum - 1 )
							isRoom |= 5;
					}
				} else randft.consume( 1 );
				
				
				if ( ( isRoom & 1 ) != 0 ) {	//部屋生成
					
					int roomTop, roomBottom, roomLeft, roomRight;
					
					int rlimTop = Math.min( connectLeft, connectRight );
					int rlimBottom = Math.max( connectLeft, connectRight );
					int rlimLeft = Math.min( connectTop, connectBottom );
					int rlimRight = Math.max( connectTop, connectBottom );
					
					roomLeft = randc.nextInt( 1, rlimLeft - 1 );
					roomTop = randc.nextInt( 1, rlimTop - 1 );
					roomRight = randc.nextInt( rlimRight + corwidth, 16 - 2 - corwidth );
					roomBottom = randc.nextInt( rlimBottom + corwidth, 16 - 2 - corwidth );
					
					
					//階段部屋の処理 部屋を最大限広げます
					if ( ( isRoom & 6 ) != 0 ) {
						roomLeft = 1;
						roomTop = 1;
						roomRight = 16 - 2 - corwidth;
						roomBottom = 16 - 2 - corwidth;
					}
					
					
					//部屋生成
					for ( int y = 2; y < height - 2; y ++ ) {
						for ( int z = roomTop; z <= roomBottom; z ++ ) {
							for ( int x = roomLeft; x <= roomRight; x ++ ) {
								setBlock( block, meta, x, fheight + y, z, 0, 0 );
							}
						}
					}
					
					
					//階段部屋の処理
					if ( ( isRoom & 2 ) != 0 ) {	//下り階段
						
						//generateDownstairs( block, meta, ( roomRight - roomLeft + 1 ) / 2 - 3, fheight, ( roomBottom - roomTop + 1 ) / 2 - 3 );
						generateDownstairs( block, meta, randfc.nextInt( roomLeft + 2, roomRight - 2 - 6 ), fheight, randfc.nextInt( roomTop + 2, roomBottom - 2 - 6 ) );
					}
					
					if ( ( isRoom & 4 ) != 0 ) {	//上り階段
						
						//generateUpstairs( block, meta, ( roomRight - roomLeft + 1 ) / 2 - 3, fheight, ( roomBottom - roomTop + 1 ) / 2 - 3 );
						generateUpstairs( block, meta, randft.nextInt( roomLeft + 2, roomRight - 2 - 6 ), fheight, randft.nextInt( roomTop + 2, roomBottom - 2 - 6 ) );
					}
					
					
					
					
					//通路生成
					if ( ( rpath & 1 ) != 0 ) {		//上に通路
						for ( int y = 2; y < height - 3; y ++ ) {
							for ( int z = 0; z < roomTop; z ++ ) {
								for ( int x = connectTop; x < connectTop + corwidth; x ++ ) {
									setBlock( block, meta, x, fheight + y, z, 0, 0 );
								}
							}
						}
					}
					
					if ( ( rpath & 2 ) != 0 ) {		//右に通路
						int tx = randc.nextInt( roomRight + 2, 16 - corwidth );			//通路が曲がる点
						int tz = randc.nextInt( roomTop + 1, roomBottom - corwidth );	//部屋と通路の交点
						
						for ( int y = 2; y < height - 3; y ++ ) {
							
							for ( int z = tz; z < tz + corwidth; z ++ ) {
								for ( int x = roomRight + 1; x < tx; x ++ ) {
									setBlock( block, meta, x, fheight + y, z, 0, 0 );
								}
							}
							
							if ( tz < connectRight ) {
								for ( int z = tz; z < connectRight + corwidth; z ++ ) {
									for ( int x = tx; x < tx + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							} else {
								for ( int z = connectRight; z < tz + corwidth; z ++ ) { 
									for ( int x = tx; x < tx + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
							
							for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
								for ( int x = tx; x < 16; x ++ ) {
									setBlock( block, meta, x, fheight + y, z, 0, 0 );
								}
							}
						}
						
					}
					
					if ( ( rpath & 4 ) != 0 ) {		//下に通路
						int tx = randc.nextInt( roomLeft + 1, roomRight - corwidth );	//部屋と通路の交点
						int tz = randc.nextInt( roomBottom + 2, 16 - corwidth );		//通路が曲がる点
						
						for ( int y = 2; y < height - 3; y ++ ) {
							
							for ( int z = roomBottom + 1; z < tz; z ++ ) {
								for ( int x = tx; x < tx + corwidth; x ++ ) {
									setBlock( block, meta, x, fheight + y, z, 0, 0 );
								}
							}
							
							if ( tx < connectBottom ) {
								for ( int z = tz; z < tz + corwidth; z ++ ) {
									for ( int x = tx; x < connectBottom + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							} else {
								for ( int z = tz; z < tz + corwidth; z ++ ) { 
									for ( int x = connectBottom; x < tx + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
							
							for ( int z = tz; z < 16; z ++ ) {
								for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) {
									setBlock( block, meta, x, fheight + y, z, 0, 0 );
								}
							}
						}
						
					}
					
					if ( ( rpath & 8 ) != 0 ) {		//左に通路
						for ( int y = 2; y < height - 3; y ++ ) {
							for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) {
								for ( int x = 0; x < roomLeft; x ++ ) {
									setBlock( block, meta, x, fheight + y, z, 0, 0 );
								}
							}
						}
					}
					
					
				} else {	//通路生成
					
					switch ( rpath ) {
					case 0:		//・
						break;
						
					case 1:		//┸
						{
							int tz = randc.nextInt( corwidth, 15 - corwidth );
							for ( int y = 2; y < height - 3; y ++ ) {
								for ( int z = 0; z < tz; z ++ ) {
									for ( int x = connectTop; x < connectTop + corwidth; x ++ ) { 
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 2:		//┝
						{
							int tx = randc.nextInt( corwidth, 15 - corwidth );
							for ( int y = 2; y < height - 3; y ++ ) {
								for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
									for ( int x = tx; x < 16; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 3:		//┗
						{
							for ( int y = 2; y < height - 3; y ++ ) {
								
								for ( int z = 0; z < connectRight + corwidth; z ++ ) {
									for ( int x = connectTop; x < connectTop + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
									for ( int x = connectTop; x < 16; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 4:		//┰
						{
							int tz = randc.nextInt( corwidth, 15 - corwidth );
							for ( int y = 2; y < height - 3; y ++ ) {
								for ( int z = tz; z < 16; z ++ ) {
									for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) { 
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
					
					case 5:		//┃
						{
							int tz = randc.nextInt( corwidth, 15 - corwidth );
							for ( int y = 2; y < height - 3; y ++ ) {
								
								for ( int z = 0; z < tz + corwidth; z ++ ) {
									for ( int x = connectTop; x < connectTop + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int z = tz; z < tz + corwidth; z ++ ) {
									
									if ( connectTop < connectBottom ) {
										for ( int x = connectTop; x < connectBottom + corwidth; x ++ ) {
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									} else {
										for ( int x = connectBottom; x < connectTop + corwidth; x ++ ) { 
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									}
								}
								
								for ( int z = tz; z < 16; z ++ ) {
									for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 6:		//┏
						{
							for ( int y = 2; y < height - 3; y ++ ) {
								
								for ( int z = connectRight; z < 16; z ++ ) {
									for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
									for ( int x = connectBottom; x < 16; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 7:		//┣
						{
							for ( int y = 2; y < height - 3; y ++ ) {
								
								for ( int z = 0; z < connectRight + corwidth; z ++ ) {
									for ( int x = connectTop; x < connectTop + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
									if ( connectTop < connectBottom ) {
										for ( int x = connectTop; x < 16; x ++ ) {
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									} else {
										for ( int x = connectBottom; x < 16; x ++ ) {
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									}
								}
								
								for ( int z = connectRight; z < 16; z ++ ) {
									for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) { 
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 8:		//┥
						{
							int tx = randc.nextInt( corwidth, 15 - corwidth );
							for ( int y = 2; y < height - 3; y ++ ) {
								for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) {
									for ( int x = 0; x < tx; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
					
					case 9:		//┛
						{
							for ( int y = 2; y < height - 3; y ++ ) {
								
								for ( int z = 0; z < connectLeft + corwidth; z ++ ) {
									for ( int x = connectTop; x < connectTop + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) {
									for ( int x = 0; x < connectTop + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 10:	//━
						{
							int tx = randc.nextInt( corwidth, 15 - corwidth );
							
							for ( int y = 2; y < height - 3; y ++ ) {
							
								for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) {
									for ( int x = 0; x < tx + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int x = tx; x < tx + corwidth; x ++ ) {
									if ( connectLeft < connectRight ) {
										for ( int z = connectLeft; z < connectRight + corwidth; z ++ ) {
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									} else {
										for ( int z = connectRight; z < connectLeft + corwidth; z ++ ) {
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									}
								}
							
								for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
									for ( int x = tx; x < 16; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
							
						} break;
						
					case 11:	//┻
						{
							for ( int y = 2; y < height - 3; y ++ ) {
							
								for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) {
									for ( int x = 0; x < connectTop + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int x = connectTop; x < connectTop + corwidth; x ++ ) {
									if ( connectLeft < connectRight ) {
										for ( int z = 0; z < connectRight + corwidth; z ++ ) { 
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									} else {
										for ( int z = 0; z < connectLeft + corwidth; z ++ ) { 
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									}
								}
								
								for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
									for ( int x = connectTop; x < 16; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
							
						} break;
						
					case 12:	//┓
						{
							for ( int y = 2; y < height - 3; y ++ ) {
								
								for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) { 
									for ( int x = 0; x < connectBottom + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int z = connectLeft; z < 16; z ++ ) {
									for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
							
						} break;
						
					case 13:	//┫
						{
							for ( int y = 2; y < height - 3; y ++ ) {
								
								for ( int z = 0; z < connectLeft + corwidth; z ++ ) {
									for ( int x = connectTop; x < connectTop + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) { 
									if ( connectTop < connectBottom ) {
										for ( int x = 0; x < connectBottom + corwidth; x ++ ) {
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									} else {
										for ( int x = 0; x < connectTop + corwidth; x ++ ) {
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									}
								}
								
								for ( int z = connectLeft; z < 16; z ++ ) {
									for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
						} break;
						
					case 14:	//┳
						{
							for ( int y = 2; y < height - 3; y ++ ) {
							
								for ( int z = connectLeft; z < connectLeft + corwidth; z ++ ) {
									for ( int x = 0; x < connectBottom + corwidth; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
								
								for ( int x = connectBottom; x < connectBottom + corwidth; x ++ ) {
									if ( connectLeft < connectRight ) {
										for ( int z = connectLeft; z < 16; z ++ ) { 
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									} else {
										for ( int z = connectRight; z < 16; z ++ ) { 
											setBlock( block, meta, x, fheight + y, z, 0, 0 );
										}
									}
								}
								
								for ( int z = connectRight; z < connectRight + corwidth; z ++ ) {
									for ( int x = connectBottom; x < 16; x ++ ) {
										setBlock( block, meta, x, fheight + y, z, 0, 0 );
									}
								}
							}
							
						} break;
						
					case 15:		//╋
						{
							//FIXME: 十字路は出現確率が低いうえ、実装が面倒なので強制的に部屋になります。
						} break;
					}
				}
				
				
			}
			
		}
		
	}
	
	
	/**
	 * 下り階段を生成します。 
	 */
	protected void generateDownstairs( short[] block, byte[] meta, int bx, int by, int bz ) {
		
		int blockBase = Block.stoneBrick.blockID;
		int blockStairs = Block.stairsStoneBrick.blockID;
		
		//階段の向きメタデータ
		final int dirxn = 0;	//x負方向
		final int dirxp = 1;	//x正方向
		final int dirzn = 2;	//z負方向
		final int dirzp = 3;	//z正方向
		final int diry  = 4;	//上下反転
		
		//空間確保
		for ( int y = by; y < by + 2; y ++ ) 
			for ( int z = bz; z < bz + 6; z ++ ) 
				for ( int x = bx; x < bx + 6; x ++ ) 
					setBlock( block, meta, x, y, z, 0, 0 );
		
		
		//柱
		for ( int z = bz + 2; z <= bz + 3; z ++ ) 
			for ( int x = bx + 2; x <= bx + 3; x ++ )
				setBlock( block, meta, x, by, z, blockBase, 0 );
	
		//上部床1
		for ( int z = bz + 2; z < bz + 6; z ++ )
			for ( int x = bx; x < bx + 4; x ++ )
				setBlock( block, meta, x, by + 1, z, blockBase, 0 );
		
		//上部床2
		for ( int z = bz + 4; z < bz + 6; z ++ ) 
			for ( int x = bx + 4; x < bx + 6; x ++ )
				setBlock( block, meta, x, by + 1, z, blockBase, 0 );
		
		//階段1
		for ( int z = bz + 2; z <= bz + 3; z ++ )
			for ( int x = bx + 4; x <= bx + 5; x ++ )
				setBlock( block, meta, x, by + z - ( bz + 2 ), z, blockStairs, dirzn );
		
		//階段2
		for ( int x = bx + 4; x <= bx + 5; x ++ )
			setBlock( block, meta, x, by, bz + 3, blockStairs, dirzp | diry );
		
	}
	
	
	/**
	 * 上り階段を生成します。とりあえず8ブロックで考えます。
	 */
	protected void generateUpstairs( short[] block, byte[] meta, int bx, int by, int bz ) {
		
		int blockBase = Block.stoneBrick.blockID;
		int blockStairs = Block.stairsStoneBrick.blockID;
		
		//階段の向きメタデータ
		final int dirxn = 0;	//x負方向
		final int dirxp = 1;	//x正方向
		final int dirzn = 2;	//z負方向
		final int dirzp = 3;	//z正方向
		final int diry  = 4;	//上下反転
	
		
		//空間確保
		for ( int y = by + 2; y < by + 8; y ++ ) 
			for ( int z = bz; z < bz + 6; z ++ ) 
				for ( int x = bx; x < bx + 6; x ++ ) 
					setBlock( block, meta, x, y, z, 0, 0 );
		
		//柱
		for ( int y = by + 2; y < by + 8; y ++ ) 
			for ( int z = bz + 2; z <= bz + 3; z ++ ) 
				for ( int x = bx + 2; x <= bx + 3; x ++ )
					setBlock( block, meta, x, y, z, blockBase, 0 );
		
		
		//階段1
		for ( int z = 4; z < 6; z ++ )
			for ( int x = 2; x < 4; x ++ ) 
				setBlock( block, meta, bx + x, by + 3 - ( x - 2 ), bz + z, blockStairs, dirxp );
		
		for ( int z = 4; z < 6; z ++ )
			setBlock( block, meta, bx + 2, by + 2, bz + z, blockStairs, dirxn | diry );
		
		
		//踊り場1
		for ( int z = 4; z < 6; z ++ )
			for ( int x = 0; x < 2; x ++ )
				setBlock( block, meta, bx + x, by + 3, bz + z, blockBase, 0 );
		
		
		//階段2
		for ( int z = 2; z < 4; z ++ ) {
			for ( int x = 0; x < 2; x ++ ) {
				setBlock( block, meta, bx + x, by + 5 - ( z - 2 ), bz + z, blockStairs, dirzp );
				setBlock( block, meta, bx + x, by + 4 - ( z - 2 ), bz + z, blockStairs, dirzn | diry );
			}
		}
		
		
		//踊り場2
		for ( int z = 0; z < 2; z ++ )
			for ( int x = 0; x < 2; x ++ )
				setBlock( block, meta, bx + x, by + 5, bz + z, blockBase, 0 );
		
		
		//階段3
		for ( int z = 0; z < 2; z ++ ) {
			for ( int x = 2; x < 4; x ++ ) {
				setBlock( block, meta, bx + x, by + x - 2 + 6, bz + z, blockStairs, dirxn );
				setBlock( block, meta, bx + x, by + x - 2 + 5, bz + z, blockStairs, dirxp | diry );
			}
		}
		
		
		//踊り場3
		for ( int z = 0; z < 2; z ++ )
			for ( int x = 4; x < 6; x ++ )
				setBlock( block, meta, bx + x, by + 7, bz + z, blockBase, 0 );
		
		
		//階段4
		for ( int x = 4; x < 6; x ++ )
			setBlock( block, meta, bx + x, by + 7, bz + 2, blockStairs, dirzp | diry );
		
		
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
	
	
	protected void setBlock( short[] block, byte[] meta, int x, int y, int z, int blockID, int blockMeta ) {
		block[getChunkIndex( x, y, z )] = (short)blockID;
		meta [getChunkIndex( x, y, z )] = (byte) blockMeta;
	}
	
	/**
	 * 0 ～ max-1 の範囲内に収めます。
	 * @param value		値。
	 * @param max		最大値。
	 * @return			変換後の値。
	 */
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
