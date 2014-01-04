package andmod.AndCore;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Item_Replacer extends Item {

	private ItemStack replacedItem;
	
	
	/**
	 * 新しい「アイテムを置換するアイテム」（バージョンアップによるID移動用）を定義します。
	 * @param itemID		（旧）アイテムのID。
	 * @param replacedItem	新しいアイテム。メタデータがWILDCARD_VALUEの場合、メタデータとNBTは保持されます。そうでない場合、完全にこのアイテムに置換されます。
	 */
	public Item_Replacer( int itemID, ItemStack replacedItem ) {
		super( itemID );
		
		this.replacedItem = replacedItem.copy();
	}
	

	@Override
	public void onUpdate( ItemStack items, World world, Entity entity, int index, boolean onhand ) {
		
		if ( replacedItem.itemID <= 0 ) {
			items.stackSize = 0;		//消滅させます。IDの移動先がなかった場合に対応します。
			
		} else if ( replacedItem.getItemDamage() == OreDictionary.WILDCARD_VALUE ) {
			items.itemID = replacedItem.itemID;
		
		} else {
			items = replacedItem.copy();
		}
		
	}

	
}
