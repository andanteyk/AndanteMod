package andmod.AndCore;

import net.minecraft.item.Item;

public class Item_Base extends Item {

	/**
	 * 無機能のアイテムを追加します。
	 * @param itemID	アイテムのID。
	 */
	public Item_Base( int itemID, int maxStack ) {
		super( itemID );

		setMaxStackSize( maxStack );
	}

}
