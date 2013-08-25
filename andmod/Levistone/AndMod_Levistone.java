package andmod.Levistone;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		modid	= "AndanteMod_Levistone",
		name	= "Levistone",
		version	= "1.6.2.1"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)


public class AndMod_Levistone {

	public int aitemID;
	public Item aitem;
	public String[] aitemname = new String[3];
	public int aitemIDdefault = 23570;
	public static String ObjectHeader = "Levistone:";
	private int fm = OreDictionary.WILDCARD_VALUE;

	private boolean isBreakable = true;
	
	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {
		aitemname[0] = "Levistone";
		aitemname[1] = "Levistone";
		aitemname[2] = "飛行石";


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			Property ItemProp = cfg.getItem( "ItemID_" + aitemname[0], aitemIDdefault );
			ItemProp.comment = "ItemID - " + aitemname[1] ;
			aitemID = ItemProp.getInt();

			Property prop = cfg.get( "General", "isBreakable", isBreakable );
			prop.comment = "Set false to disable terrain damage." ;
			isBreakable = prop.getBoolean( isBreakable );

		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured" );
		} finally {	cfg.save();	}


		aitemname[0] = ObjectHeader + aitemname[0];

	}

	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {
		
		EnumArmorMaterial AMLevistone = EnumHelper.addArmorMaterial( "LEVISTONE", 16, new int[]{ 0, 0, 0, 0 }, 0 );
		
		aitem = new Item_Levistone( aitemID, ObjectHeader.toLowerCase() + "textures/armor/levistone.png", AMLevistone, 1 ).setIsBreakable( isBreakable )
		.setUnlocalizedName( aitemname[0] ).func_111206_d( aitemname[0] ).setCreativeTab( CreativeTabs.tabTools );

		GameRegistry.registerItem( aitem, aitemname[0] );
		LanguageRegistry.addName( aitem, aitemname[1] );
		LanguageRegistry.instance().addNameForObject( aitem, "ja_JP", aitemname[2] );

		GameRegistry.addRecipe( new ItemStack( aitem, 1, 0 ),
				" t ",
				"tdt",
				" t ",
				'd', new ItemStack( Block.blockDiamond, 1, 0 ),
				't', new ItemStack( Block.blockLapis, 1, 0 ) );
		
	}

}
