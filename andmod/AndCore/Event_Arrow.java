package andmod.AndCore;

import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class Event_Arrow {

	public static ArrayList<Integer> IDList = new ArrayList<Integer>();
	protected final Struct_Arrow defaultArrow = new Struct_Arrow( Item.arrow.itemID, 1.0, 2.0, 0.0, 0.05, "textures/entity/arrow.png" );



	public void addArrow( int arrowID ) {
		IDList.add( arrowID );
	}

	protected int hasArrow( EntityPlayer eplayer ) {
		for ( int i = 0; i < eplayer.inventory.getSizeInventory() - 4; i ++ ) {
			ItemStack items = eplayer.inventory.getStackInSlot( i );
			if ( items == null ) continue;
			for ( int j = 0; j < IDList.size(); j ++ )
				if ( items.itemID == IDList.get( j ) )
					return items.itemID;

		}

		return 0;
	}


	protected void consumeArrow( int itemID, EntityPlayer eplayer ) {
		eplayer.inventory.consumeInventoryItem( itemID );
	}


	@ForgeSubscribe
	public void onArrowNock( ArrowNockEvent e ) {

		if ( e.entityPlayer.capabilities.isCreativeMode || hasArrow( e.entityPlayer ) != 0 ) {
			e.entityPlayer.setItemInUse( e.result, e.result.getItem().getMaxItemUseDuration( e.result ) );
			e.setCanceled( true );
		}
	}


	@ForgeSubscribe
	public void onArrowLoose( ArrowLooseEvent e ) {

		double chargeSpeed, velocity, power, knockback;
		boolean isInfinite = e.entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel( Enchantment.infinity.effectId, e.bow ) > 0;

		int arrowID = hasArrow( e.entityPlayer );
		Struct_Arrow arrow;
		
		
		e.setCanceled( true );
		

		if( isInfinite && ( arrowID == Item.arrow.itemID || arrowID == 0 ) ) {
			arrowID = Item.arrow.itemID;
			arrow = defaultArrow;

		} else {
			//arrowID = hasArrow( e.entityPlayer );
			arrow = getArrowProperty( arrowID );
		}



		if ( e.bow.getItem() instanceof Item_Bow ) {
			Item_Bow bow = (Item_Bow)e.bow.getItem();
			chargeSpeed = bow.chargeSpeed;
			velocity = bow.velocity;
			power = bow.power;
			knockback = bow.knockback;

		} else {
			chargeSpeed = 1.0;
			velocity = 1.0;
			power = 0.0;
			knockback = 0.0;
		}


		if ( arrowID != 0 ) {

			double strength = e.charge * chargeSpeed / 20.0;
			strength = ( strength * strength + strength * 2.0 ) / 3.0;

			if ( strength < 0.1 ) return;
			if ( strength > 1.0 ) strength = 1.0;

			Entity_Arrow earrow = new Entity_Arrow( e.entityPlayer.worldObj, e.entityPlayer, strength * 2.0 * velocity * arrow.velocity, e.bow.itemID, arrowID );
			earrow.setDamage( power + arrow.power );
			earrow.setIsCritical( strength == 1.0 );


			//enchantment
			{
				int lv = EnchantmentHelper.getEnchantmentLevel( Enchantment.power.effectId, e.bow );
				if ( lv > 0 )
					earrow.setDamage( earrow.getDamage() + lv * 0.5D + 0.5D );

				lv = EnchantmentHelper.getEnchantmentLevel( Enchantment.punch.effectId, e.bow );
				if( lv > 0 )
					knockback += lv;

				if ( EnchantmentHelper.getEnchantmentLevel( Enchantment.flame.effectId, e.bow ) > 0 )
					earrow.setFire( 100 );
			}
			
			earrow.setKnockbackStrength( knockback );
			

			if ( e.bow.getItem() instanceof Item_Bow )
				e.bow.damageItem( ( (Item_Bow)e.bow.getItem() ).itemDamageOnUse, e.entityPlayer );
			else
				e.bow.damageItem( 1, e.entityPlayer );


			e.entityPlayer.worldObj.playSoundAtEntity( e.entityPlayer, "random.bow", 1.0F, 1.0F / ( e.entityPlayer.worldObj.rand.nextFloat() * 0.4F + 1.2F ) + (float)strength * 0.5F );


			if ( isInfinite )
				earrow.canBePickedUp = 2;
			else
				consumeArrow( arrowID, e.entityPlayer );


			if ( !e.entityPlayer.worldObj.isRemote ) e.entityPlayer.worldObj.spawnEntityInWorld( earrow );


			//e.setCanceled( true );
		}

	}




	/**
	 * 矢の情報を取得します。
	 * @return	矢の情報(を格納したItem)。
	 */
	protected Struct_Arrow getArrowProperty( int arrowID ) {
		if ( arrowID == 0 || arrowID == Item.arrow.itemID || !( Item.itemsList[arrowID] instanceof Item_Arrow ) ) return defaultArrow;
		return ( (Item_Arrow)Item.itemsList[arrowID] ).property;
	}
}
