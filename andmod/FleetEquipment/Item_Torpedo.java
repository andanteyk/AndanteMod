package andmod.FleetEquipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import andmod.AndCore.Entity_Arrow;
import andmod.AndCore.IArrow;
import andmod.AndCore.Item_Arrow;

public class Item_Torpedo extends Item_Arrow implements IArrow {

	public int raynum;
	//public double strength;
	
	public Item_Torpedo( int itemID, int ray ) {
		super( itemID, 64 );
		
		raynum = ray;
	}

	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {
		
		double accuracy = 2.0;
		double strength = 0.5;
		strength = ( strength * strength + strength * 2.0 ) / 3.0;
		if ( strength > 1.0 ) strength = 1.0;
		
		
		for ( int i = 0; i < raynum; i ++ ) {
		
			Entity_Arrow earrow = new Entity_Arrow( world, eplayer, strength * 2.0 * property.velocity, Item.bow.itemID, this.itemID );
			earrow.setDamage( property.power );
			earrow.setIsCritical( strength == 1.0 );
			
			earrow.setKnockbackStrength( property.knockback );
			
			
			earrow.posX += world.rand.nextDouble() * accuracy - accuracy / 2.0;
			earrow.posY += world.rand.nextDouble() * accuracy - accuracy / 2.0;
			earrow.posZ += world.rand.nextDouble() * accuracy - accuracy / 2.0;
			
			
			if ( !eplayer.capabilities.isCreativeMode ) items.stackSize --;
			else earrow.canBePickedUp = 2;
			
			//world.playSoundAtEntity( eplayer, "random.bow", 1.0F, 1.0F / ( itemRand.nextFloat() * 0.4F + 1.2F ) + (float)strength * 0.5F );
			world.playSoundAtEntity( eplayer, "fireworks.launch", 3.0F, 1.0F );
			
			if ( !world.isRemote ) world.spawnEntityInWorld( earrow );
			
		}
		
		return items;
	}
	
	
	

}
