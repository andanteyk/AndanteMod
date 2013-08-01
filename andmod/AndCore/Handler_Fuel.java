package andmod.AndCore;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

/**
 * Fuel handler ver. 1.01
 * @author Andante
 *
 */
public class Handler_Fuel implements IFuelHandler {

	private ArrayList<Integer> IDList = new ArrayList<Integer>();
	private ArrayList<Integer> MetaList = new ArrayList<Integer>();
	private ArrayList<Integer> VaList = new ArrayList<Integer>();

	/**
	 * 燃料となるアイテムのデータを追加します。
	 * @param id		アイテムのID。
	 * @param meta		アイテムのメタデータ。指定が不要なら -1 に、耐久度に応じて変化させるなら -2 を指定してください。
	 * @param burntime	燃焼時間。
	 */
	public void addFuel( int id, int meta, int burntime ) {
		IDList.add( id );
		MetaList.add( meta );
		VaList.add( burntime );
	}


	@Override
	public int getBurnTime( ItemStack fuel ) {
		int id = fuel.itemID;

		for ( int i = 0; i < IDList.size(); i++ )
			if ( id == IDList.get(i) )
				if ( MetaList.get(i) == -2 )
					return VaList.get(i) * ( fuel.getMaxDamage() - fuel.getItemDamage() ) / fuel.getMaxDamage();

				else if ( MetaList.get(i) == -1 || MetaList.get(i) == fuel.getItemDamage() )
					return VaList.get(i);

		return 0;
	}

}
