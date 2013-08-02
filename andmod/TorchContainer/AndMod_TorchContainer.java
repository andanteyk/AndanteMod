package andmod.TorchContainer;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
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
		modid	= "AndanteMod_TorchContainer",
		name	= "Torch Container",
		version	= "1.6.2.1"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)


//なぜこれだけ互換性のない仕様にしたのでしょう…？
public class AndMod_TorchContainer {

	public int aitemID;
	public Item aitem;
	public String[] aitemname = new String[3];
	public int aitemIDdefault = 23603;
	public static String ObjectHeader = "TorchContainer:";
	private int fm = OreDictionary.WILDCARD_VALUE;

	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {
		aitemname[0] = "TorchContainer";
		aitemname[1] = "Torch Container";
		aitemname[2] = "松明入れ";


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			Property ItemProp = cfg.getItem( "ItemID_" + aitemname[0], aitemIDdefault );
			ItemProp.comment = "ItemID - " + aitemname[1] ;
			aitemID = ItemProp.getInt();


		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured" );
		} finally {	cfg.save();	}


		aitemname[0] = ObjectHeader + aitemname[0];

	}

	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {
		aitem = new Item_Container( aitemID, 256, new ItemStack( Block.torchWood, 1, 0 ) )
		.setUnlocalizedName( aitemname[0] ).func_111206_d( aitemname[0] ).setCreativeTab( CreativeTabs.tabTools );

		GameRegistry.registerItem( aitem, aitemname[0] );
		LanguageRegistry.addName( aitem, aitemname[1] );
		LanguageRegistry.instance().addNameForObject( aitem, "ja_JP", aitemname[2] );

		GameRegistry.addRecipe( new ItemStack( aitem, 1, aitem.getMaxDamage() - 1 ),
				"l l",
				"ltl",
				" l ",
				'l', new ItemStack( Item.leather, 1, 0 ),
				't', new ItemStack( Block.torchWood, 1, 0 ) );
		
		GameRegistry.addRecipe( (Item_Container)aitem );

	}

}
