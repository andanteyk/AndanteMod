package andmod.AndCore;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Item_Armor extends ItemArmor {

	protected String[] armortex;

	/**
	 * 新しい鎧を定義します。
	 * @param itemID		アイテムのID。
	 * @param texture		鎧のテクスチャへのパス。[0]にレギンス以外の、[1]にレギンスのテクスチャを指定してください。
	 * @param material		素材。
	 * @param armorType		鎧のタイプ。0=ヘルメット, 1=チェストプレート, 2=レギンス, 3=ブーツ となります。
	 */
	public Item_Armor( int itemID, String[] texture, EnumArmorMaterial material, int armorType ) {
		super( itemID, material, 1, armorType );
		armortex = texture;
	}


	/**
	 * 新しい鎧を定義します。
	 * @param itemID		アイテムのID。
	 * @param topTexture	鎧のテクスチャへのパス。レギンス以外のテクスチャを指定します。
	 * @param legsTexture	鎧のテクスチャへのパス。レギンスのテクスチャを指定します。
	 * @param material		素材。
	 * @param armorType		鎧のタイプ。0=ヘルメット, 1=チェストプレート, 2=レギンス, 3=ブーツ となります。
	 */
	public Item_Armor( int itemID, String topTexture, String legsTexture, EnumArmorMaterial material, int armorType ) {
		super( itemID, material, 1, armorType );
		armortex = new String[] { topTexture, legsTexture };
	}


	@Override
	public String getArmorTexture( ItemStack items, Entity entity, int slot, int layer ) {
		return armortex[ slot == 2 ? 1 : 0 ];
	}


	//*/

	//鎧を「交換」出来るようにもします
	//note: クリエイティブで交換しようとしても、既存の鎧が上書きされるだけになります。(既存書き換えをしなければ)修正不能なので仕様です。
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		int i = EntityLiving.getArmorPosition( items ) - 1;
		ItemStack current = eplayer.getCurrentArmor( i );

		eplayer.setCurrentItemOrArmor( i + 1, items.copy() );

		if ( current == null ) {	//直接 null を返すことはできません
			items.stackSize = 0;
			return items;

		} else return current;
	}


	/*/

	//クリエイティブでも交換可能になりますが、空中クリックにまでは対応しきれないので断念
	@Override
	public boolean onItemUseFirst( ItemStack items, EntityPlayer eplayer, World world, int bx, int by, int bz, int side, float hitX, float hitY, float hitZ ) {

		int i = EntityLiving.getArmorPosition( items ) - 1;
		ItemStack current = eplayer.getCurrentArmor( i );

		eplayer.setCurrentItemOrArmor( i + 1, items.copy() );

		if ( current == null ) {	//直接 null を返すことはできません
			items.stackSize = 0;

		} else {
			items.itemID = current.itemID;
			//items.stackSize = current.stackSize;
			items.setItemDamage( current.getItemDamage() );

			items.stackTagCompound = null;
			if ( current.stackTagCompound != null ) {
				items.stackTagCompound = (NBTTagCompound) current.stackTagCompound.copy();
			}


		}

		return true;
	}

	/*/
}
