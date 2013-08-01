package andmod.LifeCrystal;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Item_LifeCrystal extends Item {

	protected float healingLimit;
	protected float drainAmount;
	
	public Item_LifeCrystal( int itemID, int durability, float healingLimit, float drainAmount ) {
		super( itemID );
		
		setCreativeTab( CreativeTabs.tabMisc );
		setMaxStackSize( 1 );
		setMaxDamage( durability );
		this.healingLimit = healingLimit;
		this.drainAmount = drainAmount;
	}

	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {
		if ( drainAmount > 0.0 && items.getItemDamage() > 0 && ( healingLimit >= 0.0 ? ( eplayer.func_110143_aJ() - drainAmount > healingLimit ) : ( eplayer.func_110143_aJ() > drainAmount ) ) && eplayer.hurtResistantTime <= 0 ) {
			eplayer.attackEntityFrom( DamageSource.magic, drainAmount );
			items.setItemDamage( items.getItemDamage() - 1 );
		}
			
		return items;
	}

	
	@Override
	public void onUpdate( ItemStack items, World world, Entity entity, int slot, boolean onhand ) {
		if ( entity instanceof EntityLivingBase ) {
			EntityLivingBase eliv = (EntityLivingBase)entity;
			
			if ( healingLimit < 0.0 || eliv.func_110143_aJ() <= healingLimit ) {
				float heal = eliv.func_110138_aP() - eliv.func_110143_aJ();
				
				if ( heal > 0.0 ) {
					if ( items.getItemDamage() + heal > items.getMaxDamage() )
						heal = items.getMaxDamage() - items.getItemDamage();
					
					eliv.heal( heal );
					items.damageItem( MathHelper.ceiling_float_int( heal ), eliv );
				}
				
			}
		}
	}
	
	
	

}
