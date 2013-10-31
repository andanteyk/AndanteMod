package andmod.Pendulum;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;

public class Item_Pendulum extends Item {
	
	public static final int TARGET_MINESHAFT = 0x1;			//廃坑
	public static final int TARGET_STRONGHOLD = 0x2;		//要塞
	public static final int TARGET_NETHERBRIGDE = 0x4;		//ネザー砦
	public static final int TARGET_VILLAGE = 0x8;			//村
	public static final int TARGET_SCATTERED = 0x10;		//小規模オブジェクト（砂漠の神殿、ジャングルの神殿、魔女の小屋）
	
	/** 似非英語に訳すかどうかを指定します。 */
	public static boolean canTranslateEnglish = true;
	public boolean isDevelopmentEnvironment = true;
	protected int target;
	
	/**
	 * 新しいペンデュラムを定義します。
	 * @param itemID	アイテムのID。
	 * @param target	探知対象。TARGET定数を指定します。ORで複数指定できます。
	 */
	public Item_Pendulum( int itemID, int target ) {
		super( itemID );
		
		this.target = target;
		setMaxStackSize( 1 );
		setMaxDamage( 0 );
		setHasSubtypes( true );
		setCreativeTab( CreativeTabs.tabTools );
		
		Class<ChunkProviderServer> c = ChunkProviderServer.class;
    	try {
    		Field f = c.getDeclaredField( "currentChunkProvider" );
    		isDevelopmentEnvironment = true;
    	} catch ( Exception e ) {
    		isDevelopmentEnvironment = false;
		}
	}
	

	@Override
	public EnumAction getItemUseAction( ItemStack items ) {
		return EnumAction.block;
	}

	
	@Override
	public int getMaxItemUseDuration( ItemStack items ) {
		return 72000;
	}


	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {
		eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, (  6 - 12 ) / 12.0D ) );
		eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, ( 10 - 12 ) / 12.0D ) );
		eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, ( 13 - 12 ) / 12.0D ) );
		eplayer.setItemInUse( items, getMaxItemUseDuration( items ) );
		return items;
	}


	@Override
	public void onUsingItemTick( ItemStack items, EntityPlayer eplayer, int tick ) {
		/*
		if ( this.getMaxItemUseDuration( items ) - tick >= 40 )
			eplayer.stopUsingItem();
		*/
		
		tick = this.getMaxItemUseDuration( items ) - tick;
		if ( tick == 20 ) {
		    eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, (  8 - 12 ) / 12.0D ) );
		    eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, ( 12 - 12 ) / 12.0D ) );
		    eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, ( 15 - 12 ) / 12.0D ) );
		    
		}
		if ( tick == 40 ) { 
			eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, ( 10 - 12 ) / 12.0D ) );
		    eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, ( 14 - 12 ) / 12.0D ) );
		    eplayer.worldObj.playSoundEffect( eplayer.posX, eplayer.posY, eplayer.posZ, "note.harp", 3.0F, (float)Math.pow( 2.0D, ( 17 - 12 ) / 12.0D ) );
		}
	}

	
	@Override
	public void onPlayerStoppedUsing( ItemStack items, World world, EntityPlayer eplayer, int tick ) {
	
		int strength;
		int bx = MathHelper.floor_double( eplayer.posX ), by = MathHelper.floor_double( eplayer.posY ), bz = MathHelper.floor_double( eplayer.posZ );
		int px, pz;
		double angle = 0.0, distance = 99999999;
		
		tick = getMaxItemUseDuration( items ) - tick;
		
		if ( world.isRemote ) return;
		
		if ( eplayer.capabilities.isCreativeMode ) {
			strength = 3;
		} else if ( tick >= 40 && eplayer.experienceLevel >= 2 ) {
			strength = 2;
			eplayer.addExperienceLevel( -2 );
		} else if ( tick >= 20 && eplayer.experienceLevel >= 1 ) {
			strength = 1;
			eplayer.addExperienceLevel( -1 );
		} else {
			strength = 0;
		}
		
		strength += items.getItemDamage();
		if ( strength > 3 ) strength = 3;
		
		
		
		ChunkPosition cpos = null;
		
		if ( cpos == null && ( target & TARGET_MINESHAFT ) != 0 ) {
			if ( world.getChunkProvider() instanceof ChunkProviderServer ) {
				
				Class<ChunkProviderServer> c = ChunkProviderServer.class;
            	try {
            		Field f = c.getDeclaredField( isDevelopmentEnvironment ? "currentChunkProvider" : "field_73246_d" );
            		f.setAccessible( true );
            		ChunkProviderGenerate cp = (ChunkProviderGenerate) f.get( world.getChunkProvider() );
            		f = ChunkProviderGenerate.class.getDeclaredField( isDevelopmentEnvironment ? "mineshaftGenerator" : "field_73223_w" );
            		f.setAccessible( true );
            		MapGenMineshaft mg = (MapGenMineshaft) f.get( cp );
            		cpos = mg.getNearestInstance( world, bx, by, bz );
            		
            		if ( cpos != null ) {
            			px = cpos.x - bx;
            			pz = cpos.z - bz;
            			double d = getDistance( px, pz );
                    	if ( d < distance ) {
                    		distance = d;
                    		angle = getAngle( pz, px, eplayer.rotationYaw );
                    	}
            		}
            		
            	} catch ( Exception e ) {		
				}
			}
		}
		
		if ( cpos == null && ( target & TARGET_STRONGHOLD ) != 0 ) {
			
			/*/
			//これでも検索はできますが、精度が劣ります（最も近い要塞を指しません）
			cpos = world.findClosestStructure( "Stronghold", bx, by, bz );   
			
			if ( cpos != null ) {
    			px = cpos.x - bx;
    			pz = cpos.z - bz;
    			double d = getDistance( px, pz );
            	if ( d < distance ) {
            		distance = d;
            		angle = getAngle( pz, px, eplayer.rotationYaw );
            	}
    		}
    		/*/
			
			if ( world.getChunkProvider() instanceof ChunkProviderServer ) {
				
				Class<ChunkProviderServer> c = ChunkProviderServer.class;
            	try {
            		Field f = c.getDeclaredField( isDevelopmentEnvironment ? "currentChunkProvider" : "field_73246_d" );
            		f.setAccessible( true );
            		ChunkProviderGenerate cp = (ChunkProviderGenerate) f.get( world.getChunkProvider() );
            		f = ChunkProviderGenerate.class.getDeclaredField( isDevelopmentEnvironment ? "strongholdGenerator" : "field_73225_u" );
            		f.setAccessible( true );
            		MapGenStronghold mg = (MapGenStronghold) f.get( cp );
            		cpos = mg.getNearestInstance( world, bx, by, bz );
            		
            		if ( cpos != null ) {
            			px = cpos.x - bx;
            			pz = cpos.z - bz;
            			double d = getDistance( px, pz );
                    	if ( d < distance ) {
                    		distance = d;
                    		angle = getAngle( pz, px, eplayer.rotationYaw );
                    	}
            		}
            		
            	} catch ( Exception e ) {		
				}
			}
			//*/
		}
		
		if ( cpos == null && ( target & TARGET_NETHERBRIGDE ) != 0 ) {
			if ( world.getChunkProvider() instanceof ChunkProviderServer ) {
				
				Class<ChunkProviderServer> c = ChunkProviderServer.class;
            	try {
            		Field f = c.getDeclaredField( isDevelopmentEnvironment ? "currentChunkProvider" : "field_73246_d" );
            		f.setAccessible( true );
            		ChunkProviderHell cp = (ChunkProviderHell) f.get( world.getChunkProvider() );
            		cpos = cp.genNetherBridge.getNearestInstance( world, bx, by, bz );
            		
            		if ( cpos != null ) {
            			px = cpos.x - bx;
            			pz = cpos.z - bz;
            			double d = getDistance( px, pz );
                    	if ( d < distance ) {
                    		distance = d;
                    		angle = getAngle( pz, px, eplayer.rotationYaw );
                    	}
            		}
            		
            	} catch ( Exception e ) {		
				}
			}
		}
		
		if ( cpos == null && ( target & TARGET_VILLAGE ) != 0 ) {
			if ( world.getChunkProvider() instanceof ChunkProviderServer ) {
				
				Class<ChunkProviderServer> c = ChunkProviderServer.class;
            	try {
            		Field f = c.getDeclaredField( isDevelopmentEnvironment ? "currentChunkProvider" : "field_73246_d" );
            		f.setAccessible( true );
            		ChunkProviderGenerate cp = (ChunkProviderGenerate) f.get( world.getChunkProvider() );
            		f = ChunkProviderGenerate.class.getDeclaredField( isDevelopmentEnvironment ? "villageGenerator" : "field_73224_v" );
            		f.setAccessible( true );
            		MapGenVillage mg = (MapGenVillage) f.get( cp );
            		cpos = mg.getNearestInstance( world, bx, by, bz );
            		
            		if ( cpos != null ) {
            			px = cpos.x - bx;
            			pz = cpos.z - bz;
            			double d = getDistance( px, pz );
                    	if ( d < distance ) {
                    		distance = d;
                    		angle = getAngle( pz, px, eplayer.rotationYaw );
                    	}
            		}
            		
            	} catch ( Exception e ) {		
				}
			}
		}
		
		if ( cpos == null && ( target & TARGET_SCATTERED ) != 0 ) {
			if ( world.getChunkProvider() instanceof ChunkProviderServer ) {
				
				Class<ChunkProviderServer> c = ChunkProviderServer.class;
            	try {
            		Field f = c.getDeclaredField( isDevelopmentEnvironment ? "currentChunkProvider" : "field_73246_d" );
            		f.setAccessible( true );
            		ChunkProviderGenerate cp = (ChunkProviderGenerate) f.get( world.getChunkProvider() );
            		f = ChunkProviderGenerate.class.getDeclaredField( isDevelopmentEnvironment ? "scatteredFeatureGenerator" : "field_73233_x" );
            		f.setAccessible( true );
            		MapGenScatteredFeature mg = (MapGenScatteredFeature) f.get( cp );
            		cpos = mg.getNearestInstance( world, bx, by, bz );
            		
            		if ( cpos != null ) {
            			px = cpos.x - bx;
            			pz = cpos.z - bz;
            			double d = getDistance( px, pz );
                    	if ( d < distance ) {
                    		distance = d;
                    		angle = getAngle( pz, px, eplayer.rotationYaw );
                    	}
            		}
            		
            	} catch ( Exception e ) {		
				}
			}
		}
		
		
		if ( cpos != null ) {
			String msg = isJapanese() ? "ペンデュラムは頼りなげに揺れている" : "Unexpected processing! You should report to author.";
			
			switch ( strength ) {
			case 0:
				if ( distance <= 50.0 )
					msg = getDistanceColor( distance ) + ( isJapanese() ? "すぐ近くになにかあるようだ" : "There seems to be something very close!" );
				else if ( distance <= 100.0 )
					msg = getDistanceColor( distance ) + ( isJapanese() ? "近くになにかあるようだ" : "There seems to be something near" );
				else if ( distance <= 200.0 )
					msg = getDistanceColor( distance ) + ( isJapanese() ? "あたりになにかあるようだ" : "There seems to be something in the area" );
				else
					msg = getDistanceColor( distance ) + ( isJapanese() ? "遠くになにかあるようだ" : "There seems to be something in the distance" );
				break;
				
			case 1:
				
				if ( distance < 20.0 ) {
					msg = getDistanceColor( distance ) + ( isJapanese() ? "すぐ近くになにかあるようだ" : "There seems to be something very close!" );
					break;
				}
				
				if ( -45.0 <= angle && angle <= 45.0 )
					msg = isJapanese() ? "前方の" : "to the front";
				else if ( -135.0 <= angle && angle <= -45.0 )
					msg = isJapanese() ? "左の" : "on the left";
				else if ( 45.0 <= angle && angle <= 135.0 )
					msg = isJapanese() ? "右の" : "on the right";
				else
					msg = isJapanese() ? "後ろの" : "to the back";
				
				if ( distance <= 50.0 )
					msg = getDistanceColor( distance ) + ( isJapanese() ? msg + "すぐ近くになにかあるようだ" : "There seems to be something very close " + msg );
				else if ( distance <= 100.0 )
					msg = getDistanceColor( distance ) + ( isJapanese() ? msg + "近くになにかあるようだ" : "There seems to be something near " + msg );
				else if ( distance <= 200.0 )
					msg = getDistanceColor( distance ) + ( isJapanese() ? msg + "あたりになにかあるようだ" : "There seems to be something in the area " + msg );
				else
					msg = getDistanceColor( distance ) + ( isJapanese() ? msg + "遠くになにかあるようだ" : "There seems to be something in the distance " + msg );
				
				break;
				
			case 2:
				{
					int clk;
					angle -= 15.0;
					if ( angle < 0.0 ) angle += 360.0;
					
					clk = MathHelper.floor_double( angle / ( 360.0 / 12.0 ) );
					if ( clk <= 0 ) clk += 12;
					
					distance = Math.floor( distance / 20.0 ) * 20.0;
					
					
					if ( distance < 20.0 ) {
						msg = getDistanceColor( distance ) + ( isJapanese() ? "すぐ近くになにかあるようだ" : "There seems to be something very close!" );
					} else {
						if ( isJapanese() )
							msg = getDistanceColor( distance ) + clk + "時方向 約" + String.format( "%1$.0f", distance ) + "m 先になにかあるようだ";
						else 
							msg = getDistanceColor( distance ) + "There seems to be something at about " + String.format( "%1$.0f", distance ) +  "m ahead in the direction of " + clk + " o'clock.";
					}	
				}
				break;
				
			case 3:
				if ( isJapanese() )
					msg = getDistanceColor( distance ) + ( ( angle < 0.0 ? "左に" : "右に" ) + String.format( "%1$.0f", Math.abs( angle ) ) + "°, " + String.format( "%1$.0f", distance ) + "m 先になにかあるようだ" );
				else 
					msg = getDistanceColor( distance ) + "There seems to be something to " + String.format( "%1$.0f", distance ) + "m ahead, " + String.format( "%1$.0f", Math.abs( angle ) ) + "° to the " + ( angle < 0.0 ? "left" : "right" );
				break;
			}
		
			eplayer.addChatMessage( msg + EnumChatFormatting.RESET );
			
		} else {
			eplayer.addChatMessage( EnumChatFormatting.DARK_PURPLE + ( isJapanese() ? "反応はないようだ" : "There seems to be nothing near..." ) + EnumChatFormatting.RESET );
			
		}
		
	}

	
	private double getAngle( double pz, double px, double yaw ) {
		double angle = Math.atan2( pz, px ) / Math.PI * 180.0 - yaw - 90.0;
		while ( angle >= 180.0 ) angle -= 360.0;
		while ( angle < -180.0 ) angle += 360.0;
		return angle;
	}
	
	
	private double getDistance( double px, double pz ) {
		return Math.sqrt( px * px + pz * pz );
	}

	
	private String getDistanceColor( double distance ) {
		if ( distance <= 50.0 )
			return "" + EnumChatFormatting.RED;
		else if ( distance <= 100.0 )
			return "" + EnumChatFormatting.YELLOW;
		else if ( distance <= 200.0 )
			return "" + EnumChatFormatting.GREEN;
		else
			return "" + EnumChatFormatting.BLUE;
	}

	
	@Override
	public boolean hasEffect( ItemStack items, int pass ) {
		return items.getItemDamage() > 0;
	}
	
	
	private boolean isJapanese() {
		return !canTranslateEnglish || Minecraft.getMinecraft().gameSettings.language.equals( "ja_JP" );
	}
}
