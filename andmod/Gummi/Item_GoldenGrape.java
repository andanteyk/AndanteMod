package andmod.Gummi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Item_GoldenGrape extends Item_GoldenFruit {

	public Item_GoldenGrape( int itemID, int maxstack, int heal, float saturation ) {
		super( itemID, maxstack, heal, saturation );
	}

	
	@Override
	protected void tryToApplyFoodEffect( ItemStack items, World world, EntityPlayer eplayer ) {
	
		if ( !world.isRemote ) {
			
			switch ( items.getItemDamage() ) {
			case 0:
				eplayer.addPotionEffect( new PotionEffect( Potion.regeneration.id, 5 * 20, 0 ) );
				break;
			case 1:
				eplayer.addPotionEffect( new PotionEffect( Potion.regeneration.id, 12 * 20 + 10, 0 ) );
				eplayer.addPotionEffect( new PotionEffect( Potion.damageBoost.id, 5 * 60 * 20, 0 ) );
				break;
			case 2:
				eplayer.addPotionEffect( new PotionEffect( Potion.regeneration.id, 30 * 20, 1 ) );
				eplayer.addPotionEffect( new PotionEffect( Potion.damageBoost.id, 5 * 60 * 20, 2 ) );
				eplayer.addPotionEffect( new PotionEffect( Potion.moveSpeed.id, 5 * 60 * 20, 1 ) );
				break;
			}
		}
	}
	
	
	
	
}
