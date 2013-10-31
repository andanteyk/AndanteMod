package andmod.Levistone;

import java.lang.reflect.Field;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.ChunkProviderServer;

public class Item_Levistone extends ItemArmor {
	
	private String armortex;
	private boolean isBreakable = true;
	
	public Item_Levistone( int itemID, String texture, EnumArmorMaterial material, int armorType ) {
		super( itemID, material, 1, armorType );
		armortex = texture;
	}

	
	public Item_Levistone setIsBreakable( boolean flag ) {
		isBreakable = flag;
		return this;
	}
	
	
	@Override
	public void onArmorTickUpdate( World world, EntityPlayer eplayer, ItemStack items ) {
		
		if ( !eplayer.onGround && eplayer.motionY <= -0.1 ) {
			if ( !eplayer.isSneaking() ) eplayer.motionY = -0.1;
			eplayer.fallDistance = 0.0F;
		}
	}

	
	
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {
		
		if ( !world.isRemote ) {
			
			int bx = MathHelper.floor_double( eplayer.posX ), by = MathHelper.floor_double( eplayer.posY ), bz = MathHelper.floor_double( eplayer.posZ );
			
			
			if ( items.getItemDamage() + 16 > items.getMaxDamage() ) {
				//バルス!!
				eplayer.addChatMessage( "バルス!" );
				
				double r = 8.0;
				float power = 4.0F;
				for ( int x = 0; x < 360; x += 45 )
					for ( int y = 0; y < 360; y += 45 )
						world.newExplosion( eplayer, eplayer.posX - r * MathHelper.sin( x * (float)Math.PI / 180.0F ) * MathHelper.cos( y * (float)Math.PI / 180.0F ) , eplayer.posY - r * MathHelper.sin( y * (float)Math.PI / 180.0F ), eplayer.posZ + r * MathHelper.cos( x * (float)Math.PI / 180.0F ) * MathHelper.cos( y * (float)Math.PI / 180.0F ), power, false, isBreakable );
					
				
				world.newExplosion( eplayer, eplayer.posX, eplayer.posY, eplayer.posZ, 5.0F, false, isBreakable );
				if ( !eplayer.capabilities.isCreativeMode )
					eplayer.addPotionEffect( new PotionEffect( Potion.blindness.id, 20 * 180, 0 ) );
				
				items.damageItem( 16, eplayer );
			} else {			
				
				
	            ChunkPosition cpos = world.findClosestStructure( "Stronghold", bx, by, bz );
	            //cpos = null;	//DEBUG
	            
	            
	            if ( cpos == null && world.provider.dimensionId == -1 && world.getChunkProvider() instanceof ChunkProviderServer ) {
	            	Class<ChunkProviderServer> c = ChunkProviderServer.class;
	            	try {
	            		Field f = c.getDeclaredField( "currentChunkProvider" );
	            		f.setAccessible( true );
	            		ChunkProviderHell cph = (ChunkProviderHell)f.get( world.getChunkProvider() );
		            	cpos = cph.genNetherBridge.getNearestInstance( world, bx, by, bz );
	            		
	            	} catch ( Exception e ) {
						//nothing
					}
	            	
	            }
	            
	            /*
	            //dungeon detection( jungle/desert temple, witch's house )
	            if ( cpos == null && world.getChunkProvider() instanceof ChunkProviderServer ) {
	            	Class<ChunkProviderServer> c = ChunkProviderServer.class;
	            	try {
	            		Field f = c.getDeclaredField( "currentChunkProvider" );
	            		f.setAccessible( true );
	            		ChunkProviderGenerate cph = (ChunkProviderGenerate) f.get( world.getChunkProvider() );
	            		f = ChunkProviderGenerate.class.getDeclaredField( "scatteredFeatureGenerator" );
	            		f.setAccessible( true );
	            		MapGenScatteredFeature mgsf = (MapGenScatteredFeature) f.get( cph );
	            		cpos = mgsf.getNearestInstance( world, (int)eplayer.posX, (int)eplayer.posY, (int)eplayer.posZ );
	            		
	            	} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} finally {}
	            	
	            }
	            */
	            
	            
	            if ( cpos != null ) {
	            	
	            	double px = cpos.x - eplayer.posX;
	            	double pz = cpos.z - eplayer.posZ;
	            	
	            	double angle = Math.atan2( pz, px ) / Math.PI * 180.0 - eplayer.rotationYaw - 90.0;
	            	double distance = Math.sqrt( px * px + pz * pz );
	            	
	            	while ( angle >= 180.0 ) angle -= 360.0;
	            	while ( angle < -180.0 ) angle += 360.0;
	            	
	            	eplayer.addChatMessage( "あの光の差す方向にラピュタがあるのだ : " + String.format( "%1$.0f", distance ) + "m, " + String.format( "%1$.0f", angle ) + "°" );
	            
	            
	            	items.damageItem( 16, eplayer );
	            } else {
	            	
	            	eplayer.addChatMessage( "ラピュタなどこの世界には存在しない!" );
	            	//eplayer.addChatMessage( "Stronghold is nowhere" );
	            }
			}
			
			
			
		}
		
		return items;
	}
	
	
	@Override
	public String getArmorTexture( ItemStack items, Entity entity, int slot, int layer ) {
		return armortex;
	}

	
	
	

}
