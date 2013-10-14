package andmod.AndCore;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Item_Base extends Item {

	protected String[] info = new String[]{ "", "" };
	
	/**
	 * 無機能のアイテムを追加します。
	 * @param itemID	アイテムのID。
	 */
	public Item_Base( int itemID ) {
		super( itemID );
	}
	
	/**
	 * 無機能のアイテムを追加します。
	 * @param itemID	アイテムのID。
	 * @param maxStack	最大スタック数。
	 */
	public Item_Base( int itemID, int maxStack ) {
		super( itemID );

		setMaxStackSize( maxStack );
	}
	
	
	/**
	 * マウスオーバー時に出るメッセージを追加します。
	 * @param english	英語時のメッセージ。
	 * @param japanese	日本語時のメッセージ。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Base setInformation( String english, String japanese ) {
		info[0] = english;
		info[1] = japanese;
		return this;
	}
	
	
	@Override
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4 ) {
		
		if ( AndMod_AndCore.isJapanese() && !info[1].equals( "" ) ) list.add( info[1] );
		else if ( !info[0].equals( "" ) ) list.add( info[0] );
		
	}
	

}
