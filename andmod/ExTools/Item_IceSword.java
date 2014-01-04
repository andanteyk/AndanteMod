package andmod.ExTools;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import andmod.AndCore.Item_Sword;

/**
 * メ几
 * 木又してでも　うばいとる
 */
public class Item_IceSword extends Item_Sword {

	private final int effectSpan = 5;
	
	public Item_IceSword( int itemID, EnumToolMaterial material ) {
		super( itemID, material );
		
		setItemDamageVsEntity( 0 );
		setItemDamageVsBlock( 0 );
	}


	@Override
	public void onPlayerStoppedUsing( ItemStack items, World world,	EntityPlayer eplayer, int tick ) {
		int time = this.getMaxItemUseDuration( items ) - tick;
		//items.damageItem( (int)( time / effectSpan ), eplayer );
	}


	@Override
	public void onUsingItemTick( ItemStack items, EntityPlayer eplayer, int tick ) {
		int time = getMaxItemUseDuration( items ) - tick;
		World world = eplayer.worldObj;
		List elist;
		
		/* if ( time / effectSpan < items.getMaxDamage() - items.getItemDamage() ) */ {
		
			if ( !world.isRemote ) {
				if ( time % effectSpan == 0 && 20 <= time ) {
					AxisAlignedBB aabb = eplayer.boundingBox.expand( 5.5, 2.0, 5.5 );
					elist = world.getEntitiesWithinAABB( EntityLivingBase.class, aabb );
					
					if ( elist != null && !elist.isEmpty() ) {
						Iterator itr = elist.iterator();
						
						while ( itr.hasNext() ) {
							EntityLivingBase target = (EntityLivingBase) itr.next();
							if ( target.equals( eplayer ) || target.isDead ) continue;
							
							double d = eplayer.getDistanceSqToEntity( target );
							
							
							if ( d <= 36.0 ) {
								
								if ( time >= 80 && time % ( effectSpan * 2 ) == 0 ) {
									//*/
									target.attackEntityFrom( DamageSource.drown, 2.0F );
									/*/
									target.attackEntityFrom( new EntityDamageSource( "drown", eplayer ), 2.0F );
									//*/
									
									
									world.playAuxSFX( 2001, MathHelper.floor_double( target.posX ), MathHelper.floor_double( target.posY ), MathHelper.floor_double( target.posZ ), 79 + ( 0 << 12 ) );
								}
								
								target.addPotionEffect( new PotionEffect( Potion.moveSlowdown.id, 20, 7 ) );
								//凄い勢いで飛び跳ねるようになるのでやめます
								//target.addPotionEffect( new PotionEffect( Potion.jump.id, 20, 128 ) );
								
							}
					       
						}
						
					}
					
				}
			}
			
			
			for ( int c = 0; c < 8; c ++ ) {
	        	float length = itemRand.nextFloat() * 4.0F, angle = itemRand.nextFloat() * 2.0F * (float)Math.PI;
				world.spawnParticle( time >= 80 ? "tilecrack_79_0" : "tilecrack_80_0", eplayer.posX + length * MathHelper.cos( angle ), eplayer.posY + length * itemRand.nextFloat(), eplayer.posZ + length * MathHelper.sin( angle ), 0.5 * MathHelper.cos( angle ) , 0.0, 0.5 * MathHelper.sin( angle ) );
	   		}
			
			/*
			if ( world.isRemote )
				Minecraft.getMinecraft().effectRenderer.addEffect( new EntityRainFX( world, eplayer.posX, eplayer.posY + 2.0, eplayer.posZ ) );
			*/
		}
	}

	
	@Override
	public EnumRarity getRarity( ItemStack items ) {
		return EnumRarity.rare;
	}

	
	@Override
	public boolean hasEffect( ItemStack items, int pass ) {
		return true;
	}


	@Override
	public boolean hitEntity( ItemStack items, EntityLivingBase defender, EntityLivingBase attacker ) {
		
		World world = attacker.worldObj;
		
		if ( defender instanceof EntityBlaze || defender instanceof EntityMagmaCube ) {
			defender.attackEntityFrom( new EntityDamageSource( "drown", attacker ), weaponDamage * 4.0F );
		}
		
		
		if ( world.rand.nextFloat() < 0.1F ) {
			if ( !defender.isDead ) {
				
				if ( !world.isRemote ) {
					/*
					if ( attacker instanceof EntityPlayer )
						( (EntityPlayer)attacker ).onEnchantmentCritical( defender );
					
					//or
					for ( int c = 0; c < 32; c ++ ) {
			        	float length = itemRand.nextFloat() * 1.0F, angle = itemRand.nextFloat() * 2.0F * (float)Math.PI;
			        	attacker.worldObj.spawnParticle( "tilecrack_79_0", defender.posX + length * MathHelper.cos( angle ), defender.posY + 2.0 + length * itemRand.nextFloat(), defender.posZ + length * MathHelper.sin( angle ), 0.5 * MathHelper.cos( angle ) , 0.0, 0.5 * MathHelper.sin( angle ) );
			   		}
					*/
					
					
					defender.setEntityHealth( 0 );
					world.playAuxSFX( 2001, MathHelper.floor_double( defender.posX ), MathHelper.floor_double( defender.posY ), MathHelper.floor_double( defender.posZ ), 79 + ( 0 << 12 ) );
				}
			}
		}
		
		return super.hitEntity( items, defender, attacker );
	}


	@Override
	public void onUpdate( ItemStack items, World world,	Entity entity, int index, boolean onhand ) {
	
		if ( items.getItemDamage() > 0 && world.getWorldTime() % 5 == 0 ) {
			for ( int y = -1; y <= 0; y ++ ) {
				
				int bid = world.getBlockId( MathHelper.floor_double( entity.posX ), MathHelper.floor_double( entity.posY ) + y, MathHelper.floor_double( entity.posZ ) );
				if ( ( bid == Block.ice.blockID || bid == Block.snow.blockID || bid == Block.blockSnow.blockID ) ) {
					items.setItemDamage( items.getItemDamage() - 1 );
					break;
				}	
			}
		}
	}
	
	
}
