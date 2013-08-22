package andmod.Quiver;

import java.util.logging.Level;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Event_Arrow;
import andmod.AndCore.Event_DispenserArrow;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid	= "AndanteMod_Quiver",
		name	= "Quiver",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_Quiver {

	//point: class

	public static int aitemqty = 1;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23613;

	public static String ObjectHeader = "Quiver:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "Quiver";
		aitemname[id][1] = "Quiver";
		aitemname[id][2] = "矢筒";


		//point: add new item name


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property ItemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				ItemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = ItemProp.getInt();
			}

		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];

	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;
		Event_Arrow e = new Event_Arrow();
		

		//Quiver
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Quiver( aitemID[id], 256, new ItemStack( Item.arrow ) ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( "quiver" );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow( true ) );
			GameRegistry.addRecipe( (IRecipe)aitem[id] );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, aitem[id].getMaxDamage() - 1 ),
					"l l",
					"ltl",
					" l ",
					'l', new ItemStack( Item.leather, 1, 0 ),
					't', new ItemStack( Item.arrow, 1, 0 ) );
			
			e.addArrow( aitem[id].itemID );
		
		}

	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}