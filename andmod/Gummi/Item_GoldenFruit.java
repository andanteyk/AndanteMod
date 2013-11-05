package andmod.Gummi;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import andmod.AndCore.Item_Food;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class Item_GoldenFruit extends Item_Food {

	public Item_GoldenFruit( int itemID, int maxstack, int heal, float saturation ) {
		super( itemID, maxstack, heal, saturation, false, false );
		
		setMaxDamage( 0 );
		setHasSubtypes( true );
		setAlwaysEdible();
	}
	
	
	@Override
	@SideOnly( Side.CLIENT )
	public boolean hasEffect( ItemStack items ) {

		return items.getItemDamage() >= 2;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity( ItemStack items ) {

		switch( items.getItemDamage() ) {
		case 0:
			return EnumRarity.rare;
		case 1:
			return EnumRarity.uncommon;
		case 2:
			return EnumRarity.epic;
		default:
			return EnumRarity.common;
		}
	}


	@Override
	public void getSubItems( int id, CreativeTabs tabs, List list ) {
		for ( int i = 0; i < 3; i ++ )
			list.add( new ItemStack( id, 1, i ) );
	}
	
	
}
