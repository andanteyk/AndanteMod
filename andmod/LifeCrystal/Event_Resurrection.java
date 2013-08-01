package andmod.LifeCrystal;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Event_Resurrection {

	private ArrayList<Integer> IDList = new ArrayList<Integer>();
	
	public Event_Resurrection() {
	}
	
	
	public void addItem( int itemID ) {
		IDList.add( itemID );
	}
	
	
	@ForgeSubscribe
	public void onDeath( LivingDeathEvent e ) {
		if ( e.entity instanceof EntityPlayer ) {
			EntityPlayer eplayer = (EntityPlayer) e.entity;
			
			for ( int i = 0; i < eplayer.inventory.getSizeInventory(); i ++ ) {
				int j;
				
				ItemStack items = eplayer.inventory.getStackInSlot( i );
				if ( items == null ) continue;
				
				for ( j = 0; j < IDList.size(); j ++ ) {
					
					if ( items.itemID == IDList.get( j ) && items.getItemDamage() < items.getMaxDamage() ) {
						
						eplayer.setEntityHealth( items.getMaxDamage() - items.getItemDamage() );
						if ( eplayer.worldObj.rand.nextFloat() < 0.25 + 0.25 * items.getItemDamage() / items.getMaxDamage() ) {
							eplayer.inventory.mainInventory[i] = null;
							eplayer.worldObj.playSoundAtEntity( eplayer, "random.break", 0.8F, 0.8F + eplayer.worldObj.rand.nextFloat() * 0.4F );
						}
						
						items.setItemDamage( items.getMaxDamage() );
						
						e.setCanceled( true );
						break;
					}
					
				}
				
				if ( j < IDList.size() ) break;
			}
			
		}
	}

}
