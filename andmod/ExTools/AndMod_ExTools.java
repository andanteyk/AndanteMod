package andmod.ExTools;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Entity_Arrow;
import andmod.AndCore.Event_Arrow;
import andmod.AndCore.Event_DispenserArrow;
import andmod.AndCore.Event_MobEquipment;
import andmod.AndCore.Handler_Fuel;
import andmod.AndCore.Item_Arrow;
import andmod.AndCore.Item_Axe;
import andmod.AndCore.Item_Base;
import andmod.AndCore.Item_Bow;
import andmod.AndCore.Item_Hammer;
import andmod.AndCore.Item_Hoe;
import andmod.AndCore.Item_Pickaxe;
import andmod.AndCore.Item_Spade;
import andmod.AndCore.Item_SpecialArmor;
import andmod.AndCore.Item_SpecialSword;
import andmod.AndCore.Item_Sword;
import andmod.AndCore.Proxy_Common;
import andmod.AndCore.Struct_Arrow;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(
		modid	= "AndanteMod_ExTools",
		name	= "ExTools",
		version	= "1.6.2.0",
		dependencies = "required-after:AndanteMod_AndCore"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_ExTools {

	//point: class

	public static int aitemqty = 19;
	public static int[] aitemID = new int[aitemqty * 16];
	public static Item[] aitem = new Item[aitemqty * 16];
	public static String[][] aitemname = new String[aitemqty * 16][3];
	public int aitemIDdefault[] = new int[aitemqty];

	public static int ablockqty = 6;
	public static int[] ablockID = new int[ablockqty];
	public static Block[] ablock = new Block[ablockqty];
	public static String[][] ablockname = new String[ablockqty][3];
	public int ablockIDdefault = 2357;

	public static String[] atypename = new String[aitemqty];


	public static String ObjectHeader = "ExTools:";

	public static String[][] ArmorTexture = new String[aitemqty][2];
	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.Instance( "AndanteMod_ExTools" )
	public static AndMod_ExTools instance;


	@SidedProxy( clientSide = "andmod.AndCore.Proxy_Client", serverSide = "andmod.AndCore.Proxy_Common" )
	public static Proxy_Common proxy;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id ++;
		atypename[id] = "Glass";
		aitemIDdefault[id] = 23571;
		id ++;
		atypename[id] = "Slime";
		aitemIDdefault[id] = 23812;


		id = -1;
		id++;
		aitemname[id][0] = "GlassStick";
		aitemname[id][1] = "Glass Stick";
		aitemname[id][2] = "ガラスの棒";
		id++;
		aitemname[id][0] = "GlassEdge";
		aitemname[id][1] = "Glass Edge";
		aitemname[id][2] = "ガラスの刃";
		id++;
		aitemname[id][0] = "GlassSword";
		aitemname[id][1] = "Glass Sword";
		aitemname[id][2] = "ガラスのつるぎ";
		id++;
		aitemname[id][0] = "GlassShovel";
		aitemname[id][1] = "Glass Shovel";
		aitemname[id][2] = "ガラスのショベル";
		id++;
		aitemname[id][0] = "GlassPickaxe";
		aitemname[id][1] = "Glass Pickaxe";
		aitemname[id][2] = "ガラスのツルハシ";
		id++;
		aitemname[id][0] = "GlassAxe";
		aitemname[id][1] = "Glass Axe";
		aitemname[id][2] = "ガラスの斧";
		id++;
		aitemname[id][0] = "GlassHoe";
		aitemname[id][1] = "Glass Hoe";
		aitemname[id][2] = "ガラスのクワ";
		id++;
		aitemname[id][0] = "GlassHammer";
		aitemname[id][1] = "Glass Hammer";
		aitemname[id][2] = "ガラスのハンマー";
		id++;
		aitemname[id][0] = "GlassHelmet";
		aitemname[id][1] = "Glass Helmet";
		aitemname[id][2] = "ガラスのヘルメット";
		id++;
		aitemname[id][0] = "GlassChestplate";
		aitemname[id][1] = "Glass Chestplate";
		aitemname[id][2] = "ガラスのチェストプレート";
		id++;
		aitemname[id][0] = "GlassLeggings";
		aitemname[id][1] = "Glass Leggings";
		aitemname[id][2] = "ガラスのレギンス";
		id++;
		aitemname[id][0] = "GlassBoots";
		aitemname[id][1] = "Glass Boots";
		aitemname[id][2] = "ガラスのブーツ";
		id++;
		aitemname[id][0] = "GlassBow";
		aitemname[id][1] = "Glass Bow";
		aitemname[id][2] = "ガラスの弓";
		id++;
		aitemname[id][0] = "GlassArrow";
		aitemname[id][1] = "Glass Arrow";
		aitemname[id][2] = "ガラスの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "SlimeSword";
		aitemname[id][1] = "Slime Sword";
		aitemname[id][2] = "スライムの剣";
		id++;
		aitemname[id][0] = "SlimeShovel";
		aitemname[id][1] = "Slime Shovel";
		aitemname[id][2] = "スライムのショベル";
		id++;
		aitemname[id][0] = "SlimePickaxe";
		aitemname[id][1] = "Slime Pickaxe";
		aitemname[id][2] = "スライムのツルハシ";
		id++;
		aitemname[id][0] = "SlimeAxe";
		aitemname[id][1] = "Slime Axe";
		aitemname[id][2] = "スライムの斧";
		id++;
		aitemname[id][0] = "SlimeHoe";
		aitemname[id][1] = "Slime Hoe";
		aitemname[id][2] = "スライムのクワ";
		id++;
		aitemname[id][0] = "SlimeHammer";
		aitemname[id][1] = "Slime Hammer";
		aitemname[id][2] = "スライムのハンマー";
		id++;
		aitemname[id][0] = "SlimeHelmet";
		aitemname[id][1] = "Slime Helmet";
		aitemname[id][2] = "スライムのヘルメット";
		id++;
		aitemname[id][0] = "SlimeChestplate";
		aitemname[id][1] = "Slime Chestplate";
		aitemname[id][2] = "スライムのチェストプレート";
		id++;
		aitemname[id][0] = "SlimeLeggings";
		aitemname[id][1] = "Slime Leggings";
		aitemname[id][2] = "スライムのレギンス";
		id++;
		aitemname[id][0] = "SlimeBoots";
		aitemname[id][1] = "Slime Boots";
		aitemname[id][2] = "スライムのブーツ";
		id++;
		aitemname[id][0] = "SlimeBow";
		aitemname[id][1] = "Slime Bow";
		aitemname[id][2] = "スライムの弓";
		id++;
		aitemname[id][0] = "SlimeArrow";
		aitemname[id][1] = "Slime Arrow";
		aitemname[id][2] = "スライムの矢";


		//point: add new item name

		id = -1;

		id++;
		ablockname[id][0] = "CrystalGlass";
		ablockname[id][1] = "Crystal Glass";
		ablockname[id][2] = "クリスタルガラス";
		id++;
		ablockname[id][0] = "CrystalGlassPane";
		ablockname[id][1] = "Crystal Glass Pane";
		ablockname[id][2] = "クリスタル板ガラス";
		id++;
		ablockname[id][0] = "ObsidianGlass";
		ablockname[id][1] = "Obsidian Glass";
		ablockname[id][2] = "クリアオブシディアン";
		id++;
		ablockname[id][0] = "ObsidianGlassPane";
		ablockname[id][1] = "Obsidian Glass Pane";
		ablockname[id][2] = "クリアオブシディアンペイン";
		id++;
		ablockname[id][0] = "CharcoalBlock";
		ablockname[id][1] = "Block of Charcoal";
		ablockname[id][2] = "木炭ブロック";
		id++;
		ablockname[id][0] = "SlimeBlock";
		ablockname[id][1] = "Block of Slime";
		ablockname[id][2] = "スライムブロック";

		//point: add new block name


		id = -1;
		id ++;
		ArmorTexture[id][0] = ObjectHeader.toLowerCase() + "textures/armor/glass_1.png";
		ArmorTexture[id][1] = ObjectHeader.toLowerCase() + "textures/armor/glass_2.png";
		id ++;
		ArmorTexture[id][0] = ObjectHeader.toLowerCase() + "textures/armor/slime_1.png";
		ArmorTexture[id][1] = ObjectHeader.toLowerCase() + "textures/armor/slime_2.png";



		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );

		try	{
			cfg.load();


			cfg.addCustomCategoryComment( "ToolType", "ID of each tools. If you don't use these tools, set 0." );
			for ( int i = 0; i < atypename.length; i ++ ) {
				if ( atypename[i] == null ) continue;

				Property prop = cfg.get( "ToolType", atypename[i] + "Tools", aitemIDdefault[i] );
				//prop.comment = atypename[i] + " Tools" ;
				aitemIDdefault[i] = prop.getInt();
			}

			for ( int i = 0; i < aitemID.length; i ++ ) {
				if ( aitemname[i][0] == null || aitemIDdefault[i / 16] <= 0 ) continue;
				aitemID[i] = aitemIDdefault[i / 16] + i % 16;
			}

			for ( int i = 0; i < ablockID.length; i ++ )	{
				Property BlockProp = cfg.getBlock( "BlockID_" + ablockname[i][0], ablockIDdefault + i );
				BlockProp.comment = "BlockID - " + ablockname[i][1];
				ablockID[i] = BlockProp.getInt();
			}

		} catch ( Exception e ) {
			FMLLog.log(Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured");
		} finally {	cfg.save();	}


		//fixme:
		//Utility_ToolConfig.loadConfig( event.getSuggestedConfigurationFile() );



		for ( int i = 0; i < aitemID.length; i ++ )
			if ( aitemname[i][0] != null )
				aitemname[i][0] = ObjectHeader + aitemname[i][0];
		for ( int i = 0; i < ablockqty; i ++ )
			ablockname[i][0] = ObjectHeader + ablockname[i][0];

	}







	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		Handler_Fuel fuelh = new Handler_Fuel();
		Event_MobEquipment eequip = new Event_MobEquipment();
		//todo: master probability settings
		
		int id = -1;
		int armorid = -1;
		


		if ( isEnabled( "Glass" ) ) {
			//Crystal Glass
			id ++;
			ablock[id] = new Block_Glass( ablockID[id], ablockname[id][0], Material.glass, false )
			.setLightOpacity( 0 ).setHardness( 0.3F ).setResistance( 1.5F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					" s ",
					"sqs",
					" s ",
					's', new ItemStack( Block.glass ),
					'q', new ItemStack( Item.netherQuartz ) );
			
			
			//Crystal Glass Pane
			id ++;
			ablock[id] = new Block_Pane( ablockID[id], ablockname[id - 1][0], ablockname[id][0], Material.glass, false )
			.setLightOpacity( 0 ).setHardness( 0.3F ).setResistance( 1.5F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"sss",
					"sss",
					's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );

			
			//Obsidian Glass
			id ++;
			ablock[id] = new Block_Glass( ablockID[id], ablockname[id][0], Material.rock, false )
			.setLightOpacity( 0 ).setHardness( 50.0F ).setResistance( 2000.0F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			MinecraftForge.setBlockHarvestLevel( ablock[id], "pickaxe", 3 );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					" s ",
					"sos",
					" s ",
					's', new ItemStack( Block.glass ),
					'o', new ItemStack( Block.obsidian ) );
			
			
			//Obsidian Glass Pane
			id ++;
			ablock[id] = new Block_Pane( ablockID[id], ablockname[id - 1][0], ablockname[id][0], Material.rock, false )
			.setLightOpacity( 0 ).setHardness( 50.0F ).setResistance( 2000.0F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			MinecraftForge.setBlockHarvestLevel( ablock[id], "pickaxe", 3 );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"sss",
					"sss",
					's', new ItemStack( getBlockID( "ObsidianGlass" ), 1, 0 ) );

		} else id += 4;
		
		/*
		if ( isEnabled( "Coal" ) ) {
			
		} else*/ id += 1;
		
		
		if ( isEnabled( "Slime" ) ) {
			//Slime Block
			id++;
			ablock[id] = new Block_Spring( ablockID[id], Material.clay, 0.80F )
			.setTransparent( true ).setLightOpacity( 3 ).setHardness( 0.5F ).setResistance( 500.0F ).setStepSound( Block.soundClothFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );

			Block.setBurnProperties( ablockID[id], 5, 10 );
			fuelh.addFuel( ablockID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"ccc",
					"ccc",
					"ccc",
					'c', new ItemStack( Item.slimeBall, 1, 0 ) );
			GameRegistry.addRecipe( new ItemStack( Item.slimeBall, 9, 0 ),
					"c",
					'c', new ItemStack( ablock[id], 1, 0 ) );

		} else id += 1;

		//point: add new blocks




		id = -1;
		
		//Glass Tools
		if ( isEnabled( "Glass" ) ) {
			
			//Glass Stick
			id ++;

			aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"g",
					"g",
					'g', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );

			

			//Glass Edge
			id ++;

			aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" f ",
					"qgq",
					" o ",
					'f', new ItemStack( Item.flint ),
					'q', new ItemStack( Item.netherQuartz ),
					'o', new ItemStack( Block.obsidian ),
					'g', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
			
			
			
			EnumToolMaterial TMGlass = EnumHelper.addToolMaterial( "GLASS", 4, 1, 20.0F, 995.0F, 99 );
			TMGlass.customCraftingMaterial = Item.itemsList[ getItemIDforCraft( "GlassEdge" ) ];
			
			
			//Glass Sword
			id ++;

			aitem[id] = new Item_Sword( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_SWORDMAN, 0.15 );	//fixme
			
			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.looting, 3 );
				items.addEnchantment( Enchantment.unbreaking, 1 );
				GameRegistry.addRecipe( items,
						"e",
						"e",
						"s",
						'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
						's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
			}
			

			//Glass Shovel
			id ++;

			aitem[id] = new Item_Spade( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.silkTouch, 1 );
				items.addEnchantment( Enchantment.unbreaking, 1 );
				GameRegistry.addRecipe( items,
					"e",
					"s",
					"s",
					'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
					's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
			}
			

			//Glass Pickaxe
			id ++;

			aitem[id] = new Item_Pickaxe( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.fortune, 3 );
				items.addEnchantment( Enchantment.unbreaking, 1 );
				GameRegistry.addRecipe( items,
						"eee",
						" s ",
						" s ",
						'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
						's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
			}
			

			//Glass Axe
			id ++;

			aitem[id] = new Item_Axe( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.fortune, 3 );
				items.addEnchantment( Enchantment.unbreaking, 1 );
				GameRegistry.addRecipe( items,
						"ee",
						"es",
						" s",
						'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
						's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
			}

			
			//Glass Hoe
			id ++;

			aitem[id] = new Item_Hoe( aitemID[id], TMGlass )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.unbreaking, 1 );
				GameRegistry.addRecipe( items,
						"ee",
						"s ",
						"s ",
						'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
						's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
			}

			
			//Glass Hammer
			id ++;

			aitem[id] = new Item_Hammer( aitemID[id], TMGlass )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1 );
				items.addEnchantment( Enchantment.unbreaking, 3 );
				GameRegistry.addRecipe( items,
						" s ",
						"eee",
						" s ",
						'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
						's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
			}





			armorid ++;
			EnumArmorMaterial AMGlass = EnumHelper.addArmorMaterial( "GLASS", 1, new int[] { 6, 16, 12, 6 }, 99 );
			AMGlass.customCraftingMaterial = Item.itemsList[ getBlockID( "CrystalGlass" ) ];
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_ALLMOB, 0.15 );
			
			
			//Glass Helmet
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 0 ).addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.unbreaking, 3 );
				GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
			}


			//Glass Chestplate
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 1 ).addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.unbreaking, 3 );
				GameRegistry.addRecipe( items,
						"s s",
						"sss",
						"sss",
						's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
			}

			
			//Glass Leggings
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 2 ).addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.unbreaking, 3 );
				GameRegistry.addRecipe( items,
					"sss",
					"s s",
					"s s",
					's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
			}
			

			//Glass Boots
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 3 ).addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.unbreaking, 3 );
				GameRegistry.addRecipe( items,
						"s s",
						"s s",
						's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
			}
			

			//Glass Bow
			id ++;

			aitem[id] = new Item_Bow( aitemID[id], TMGlass ).setParameters( 18.0F, 6.0F, 8.0F, 2.5F )
			.setItemDamageOnUse( 2 ).setCraftingMaterial( getItemIDforCraft( "GlassStick" ) )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			{
				ItemStack items = new ItemStack( aitem[id], 1, 0 );
				items.addEnchantment( Enchantment.unbreaking, 1 );
				items.addEnchantment( Enchantment.infinity, 1 );
				GameRegistry.addRecipe( items,
						" st",
						"s t",
						" st",
						's', new ItemStack ( getItemIDforCraft( "GlassStick" ), 1, 0 ),
						't', Item.silk );
			}


			//Glass Arrow
			id++;

			aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 6.0, 8.0, 2.5, 0.00, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
			.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
					"h",
					"s",
					"f",
					'h', new ItemStack ( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
					's', new ItemStack ( getItemIDforCraft( "GlassStick" ), 1, 0 ),
					'f', Item.feather );
			
			id = id / 16 * 16 + 15;
		} else id += 16;

		
		
		//point: Slime Tools
		if ( isEnabled( "Slime" ) ) {

			EnumToolMaterial TMSlime = EnumHelper.addToolMaterial( "SLIME", 0, 96, 3.0F, 0.3F, 49 );
			TMSlime.customCraftingMaterial = new ItemStack( getBlockID( "SlimeBlock" ) + 256, 1, 0 ).getItem();//Item.itemsList[ getBlockID( "SlimeBlock" ) ];
			
			
			//Slime Sword
			id ++;

			aitem[id] = new Item_SpecialSword( aitemID[id], TMSlime ).addEffect( Potion.weakness.id, 0, 20 * 20, 0.1F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );
			eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_ZOMBIE, 0.15 );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"e",
					"s",
					'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );

			

			//Slime Shovel
			id ++;

			aitem[id] = new Item_Spade( aitemID[id], TMSlime )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"s",
					"s",
					'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			

			//Slime Pickaxe
			id ++;

			aitem[id] = new Item_Pickaxe( aitemID[id], TMSlime )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"eee",
					" s ",
					" s ",
					'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );

			

			//Slime Axe
			id ++;

			aitem[id] = new Item_Axe( aitemID[id], TMSlime )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"es",
					" s",
					'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );


			
			//Slime Hoe
			id ++;

			aitem[id] = new Item_Hoe( aitemID[id], TMSlime )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"s ",
					"s ",
					'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );


			
			//Slime Hammer
			id ++;

			aitem[id] = new Item_Hammer( aitemID[id], TMSlime ).setBurnTick( 200 * 8 ).setMaxDamage( (int)( 64 * 1.5 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );






			armorid ++;
			EnumArmorMaterial AMSlime = EnumHelper.addArmorMaterial( "SLIME", 5, new int[] { 2, 5, 3, 2 }, 49 );
			AMSlime.customCraftingMaterial = Item.itemsList[ getBlockID( "SlimeBlock" ) ];
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_OVERWORLD, 0.15 );
			
			
			//Slime Helmet
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 0 ).setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) + 100, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_FEATHERFALLING, 73, Item_SpecialArmor.FLAG_ANYTIME ).setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );



			//Slime Chestplate
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 1 ).setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) + 100, Item_SpecialArmor.FLAG_ANYTIME )
					.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"sss",
					"sss",
					's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );


			
			//Slime Leggings
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 2 ).setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) + 100, Item_SpecialArmor.FLAG_ANYTIME )
					.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );

			GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					"s s",
					's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );

			

			//Slime Boots
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 3 ).setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) + 100, Item_SpecialArmor.FLAG_ANYTIME )
					.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"s s",
					's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );



			//Slime Bow
			id ++;

			aitem[id] = new Item_Bow( aitemID[id], TMSlime ).setParameters( 2.0F, 0.8F, -0.2F, 0.0F ).setCraftingMaterial( getBlockID( "SlimeBlock" ) )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 8 );
				
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" st",
					"s t",
					" st",
					's', new ItemStack ( getBlockID( "SlimeBlock" ), 1, 0 ),
					't', Item.silk );



			//Slime Arrow
			id++;

			aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.9, 1.6, 0.0, 0.04, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
			.addEffect( Potion.weakness.id, 0, 12 * 20, 0.1F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 2 );
			BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
					"h",
					"s",
					"f",
					'h', new ItemStack ( Item.slimeBall, 1, 0 ),
					's', new ItemStack ( Item.stick, 1, 0 ),
					'f', Item.feather );

			
			id = id / 16 * 16 + 15;
		} else id += 16;


		











		//point: add new item




		//point: arrow register
		{
			Event_Arrow earrow = new Event_Arrow();

			earrow.addArrow( Item.arrow.itemID );
			if ( isEnabled( "Slime" ) ) earrow.addArrow( getItemIDforCraft( "SlimeArrow" ) );
			if ( isEnabled( "Glass" ) ) earrow.addArrow( getItemIDforCraft( "GlassArrow" ) );
			MinecraftForge.EVENT_BUS.register( earrow );

			EntityRegistry.registerModEntity( Entity_Arrow.class, ObjectHeader + "ExArrow", 0, instance, 250, 1, true );
			proxy.registerArrowRenderer( Entity_Arrow.class );
		}




		GameRegistry.registerFuelHandler( fuelh );
		MinecraftForge.EVENT_BUS.register( eequip );
		
	}









	public static int getItemID( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < aitemname.length; i ++ )
			if ( aitemname[i][0] != null && aitemname[i][0].equals( name ) ) return aitemID[i];
		return 0;
	}

	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < aitemname.length; i ++ )
			if ( aitemname[i][0] != null && aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		int x = 1 / 0;	//debug: for illegal access
		return 0;
	}

	public static int getItemArrayID ( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < aitemname.length; i ++ )
			if ( aitemname[i][0] != null && aitemname[i][0].equals( name ) ) return i;
		return 0;
	}

	public static int getBlockID ( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < ablockname.length; i ++ )
			if ( ablockname[i][0] != null && ablockname[i][0].equals( name ) ) return ablockID[i];
		int x = 1 / 0;	//debug: for illegal access
		return 0;
	}


	private boolean isEnabled( String typeName ) {

		for ( int i = 0; i < atypename.length; i ++ )
			if ( atypename[i].equals( typeName ) ) return true;

		return false;
	}
}
