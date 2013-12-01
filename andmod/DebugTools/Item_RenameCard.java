package andmod.DebugTools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Item_RenameCard extends Item {

	private boolean isDevEnv;
	
	public Item_RenameCard( int itemID ) {
		super( itemID );
		
		this.setCreativeTab( CreativeTabs.tabTools );
		
		try {
			Field field = EntityPlayer.class.getDeclaredField( "username" ); 
			isDevEnv = true;
		} catch ( Exception e ) {
			isDevEnv = false;
		}
	}

	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {
		
		if ( items.hasDisplayName() ) {
			
			try {
				Field f = EntityPlayer.class.getDeclaredField( isDevEnv ? "username" : "field_71092_bJ" ); //username
				f.setAccessible( true );
				
				Field m = Field.class.getDeclaredField( "modifiers" );
				m.setAccessible( true );
				m.setInt( f, f.getModifiers() & ~Modifier.FINAL );
				
				f.set( eplayer, items.getDisplayName() );
				
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
			
			
		return items;
	}

	
}
