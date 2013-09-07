package andmod.MagicalArmor;

import java.util.logging.Level;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid	= "AndanteMod_MagicalArmor",
		name	= "Magical Armor",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_MagicalArmor {

	//point: class

	public static int aitemqty = 1;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 24062;

	public static String ObjectHeader = "MagicalArmor:";

	private int fm = OreDictionary.WILDCARD_VALUE;

	
	private boolean canConsumeXP = true;

	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;


		id++;
		aitemname[id][0] = "MagicalArmor";
		aitemname[id][1] = "Magical Armor";
		aitemname[id][2] = "魔法の鎧";
		

		//point: add new item name


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property itemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				itemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = itemProp.getInt();
			}
			
			Property prop = cfg.get( "General", "canConsumeXP", canConsumeXP );
			prop.comment = "Specifies whether to consume the experience value over time." ;
			canConsumeXP = prop.getBoolean( canConsumeXP );


		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];

	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;

		//Magical Armor
		id ++;
		if ( aitemID[id] != 0 ) {
			
			EnumArmorMaterial AMMagic = EnumHelper.addArmorMaterial( "MAGICALARMOR", 15, new int[]{ 1, 1, 1, 1 }, 0 );
			
			aitem[id] = new Item_MagicalArmor( aitemID[id], ObjectHeader.toLowerCase() + "textures/armor/magicalarmor.png", "", AMMagic, 1 )
			.setCanConsumeXP( canConsumeXP )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		}


	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}