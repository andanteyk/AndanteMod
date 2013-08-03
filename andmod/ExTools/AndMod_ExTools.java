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
import andmod.AndCore.Block_Base;
import andmod.AndCore.Entity_Arrow;
import andmod.AndCore.Event_Arrow;
import andmod.AndCore.Event_DispenserArrow;
import andmod.AndCore.Event_MobEquipment;
import andmod.AndCore.Handler_Fuel;
import andmod.AndCore.Item_Armor;
import andmod.AndCore.Item_Arrow;
import andmod.AndCore.Item_Axe;
import andmod.AndCore.Item_Base;
import andmod.AndCore.Item_Bow;
import andmod.AndCore.Item_Hammer;
import andmod.AndCore.Item_Hoe;
import andmod.AndCore.Item_Pickaxe;
import andmod.AndCore.Item_Shears;
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


	//fixme: ここに特殊オプション変数を挿入
	private boolean replaceVanillaTools = true;
	
	
	
	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id ++;
		atypename[id] = "Glass";
		aitemIDdefault[id] = 23571;
		id ++;
		atypename[id] = "Ancient";
		aitemIDdefault[id] = 23620;
		id ++;
		atypename[id] = "Coal";
		aitemIDdefault[id] = 23652;
		id ++;
		atypename[id] = "Obsidian";
		aitemIDdefault[id] = 23668;
		id ++;
		atypename[id] = "ExVanilla";
		aitemIDdefault[id] = 23684;
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
		aitemname[id][0] = "StoneStick";
		aitemname[id][1] = "Stone Stick";
		aitemname[id][2] = "石の棒";
		id++;
		aitemname[id][0] = "AncientSword";
		aitemname[id][1] = "Ancient Sword";
		aitemname[id][2] = "古代の剣";
		id++;
		aitemname[id][0] = "AncientShovel";
		aitemname[id][1] = "Ancient Shovel";
		aitemname[id][2] = "古代のショベル";
		id++;
		aitemname[id][0] = "AncientPickaxe";
		aitemname[id][1] = "Ancient Pickaxe";
		aitemname[id][2] = "古代のツルハシ";
		id++;
		aitemname[id][0] = "AncientAxe";
		aitemname[id][1] = "Ancient Axe";
		aitemname[id][2] = "古代の斧";
		id++;
		aitemname[id][0] = "AncientHoe";
		aitemname[id][1] = "Ancient Hoe";
		aitemname[id][2] = "古代のクワ";
		id++;
		aitemname[id][0] = "AncientHammer";
		aitemname[id][1] = "Ancient Hammer";
		aitemname[id][2] = "古代のハンマー";
		id++;
		aitemname[id][0] = "AncientHelmet";
		aitemname[id][1] = "Ancient Helmet";
		aitemname[id][2] = "古代のヘルメット";
		id++;
		aitemname[id][0] = "AncientChestplate";
		aitemname[id][1] = "Ancient Chestplate";
		aitemname[id][2] = "古代のチェストプレート";
		id++;
		aitemname[id][0] = "AncientLeggings";
		aitemname[id][1] = "Ancient Leggings";
		aitemname[id][2] = "古代のレギンス";
		id++;
		aitemname[id][0] = "AncientBoots";
		aitemname[id][1] = "Ancient Boots";
		aitemname[id][2] = "古代のブーツ";
		id++;
		aitemname[id][0] = "AncientBow";
		aitemname[id][1] = "Ancient Bow";
		aitemname[id][2] = "古代の弓";
		id++;
		aitemname[id][0] = "AncientArrow";
		aitemname[id][1] = "Ancient Arrow";
		aitemname[id][2] = "古代の矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "CoalSword";
		aitemname[id][1] = "Coal Sword";
		aitemname[id][2] = "石炭の剣";
		id++;
		aitemname[id][0] = "CoalShovel";
		aitemname[id][1] = "Coal Shovel";
		aitemname[id][2] = "石炭のショベル";
		id++;
		aitemname[id][0] = "CoalPickaxe";
		aitemname[id][1] = "Coal Pickaxe";
		aitemname[id][2] = "石炭のツルハシ";
		id++;
		aitemname[id][0] = "CoalAxe";
		aitemname[id][1] = "Coal Axe";
		aitemname[id][2] = "石炭の斧";
		id++;
		aitemname[id][0] = "CoalHoe";
		aitemname[id][1] = "Coal Hoe";
		aitemname[id][2] = "石炭のクワ";
		id++;
		aitemname[id][0] = "CoalHammer";
		aitemname[id][1] = "Coal Hammer";
		aitemname[id][2] = "石炭のハンマー";
		id++;
		aitemname[id][0] = "CoalHelmet";
		aitemname[id][1] = "Coal Helmet";
		aitemname[id][2] = "石炭のヘルメット";
		id++;
		aitemname[id][0] = "CoalChestplate";
		aitemname[id][1] = "Coal Chestplate";
		aitemname[id][2] = "石炭のチェストプレート";
		id++;
		aitemname[id][0] = "CoalLeggings";
		aitemname[id][1] = "Coal Leggings";
		aitemname[id][2] = "石炭のレギンス";
		id++;
		aitemname[id][0] = "CoalBoots";
		aitemname[id][1] = "Coal Boots";
		aitemname[id][2] = "石炭のブーツ";
		id++;
		aitemname[id][0] = "CoalBow";
		aitemname[id][1] = "Coal Bow";
		aitemname[id][2] = "石炭の弓";
		id++;
		aitemname[id][0] = "CoalArrow";
		aitemname[id][1] = "Coal Arrow";
		aitemname[id][2] = "石炭の矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "ObsidianSword";
		aitemname[id][1] = "Obsidian Sword";
		aitemname[id][2] = "黒曜石の剣";
		id++;
		aitemname[id][0] = "ObsidianShovel";
		aitemname[id][1] = "Obsidian Shovel";
		aitemname[id][2] = "黒曜石のショベル";
		id++;
		aitemname[id][0] = "ObsidianPickaxe";
		aitemname[id][1] = "Obsidian Pickaxe";
		aitemname[id][2] = "黒曜石のツルハシ";
		id++;
		aitemname[id][0] = "ObsidianAxe";
		aitemname[id][1] = "Obsidian Axe";
		aitemname[id][2] = "黒曜石の斧";
		id++;
		aitemname[id][0] = "ObsidianHoe";
		aitemname[id][1] = "Obsidian Hoe";
		aitemname[id][2] = "黒曜石のクワ";
		id++;
		aitemname[id][0] = "ObsidianHammer";
		aitemname[id][1] = "Obsidian Hammer";
		aitemname[id][2] = "黒曜石のハンマー";
		id++;
		aitemname[id][0] = "ObsidianHelmet";
		aitemname[id][1] = "Obsidian Helmet";
		aitemname[id][2] = "黒曜石のヘルメット";
		id++;
		aitemname[id][0] = "ObsidianChestplate";
		aitemname[id][1] = "Obsidian Chestplate";
		aitemname[id][2] = "黒曜石のチェストプレート";
		id++;
		aitemname[id][0] = "ObsidianLeggings";
		aitemname[id][1] = "Obsidian Leggings";
		aitemname[id][2] = "黒曜石のレギンス";
		id++;
		aitemname[id][0] = "ObsidianBoots";
		aitemname[id][1] = "Obsidian Boots";
		aitemname[id][2] = "黒曜石のブーツ";
		id++;
		aitemname[id][0] = "ObsidianShears";
		aitemname[id][1] = "Obsidian Shears";
		aitemname[id][2] = "黒曜石のハサミ";
		id++;
		aitemname[id][0] = "ObsidianBow";
		aitemname[id][1] = "Obsidian Bow";
		aitemname[id][2] = "黒曜石の弓";
		id++;
		aitemname[id][0] = "ObsidianArrow";
		aitemname[id][1] = "Obsidian Arrow";
		aitemname[id][2] = "黒曜石の矢";

		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "WoodenHammer";
		aitemname[id][1] = "Wooden Hammer";
		aitemname[id][2] = "木のハンマー";
		id++;
		aitemname[id][0] = "StoneHammer";
		aitemname[id][1] = "Stone Hammer";
		aitemname[id][2] = "石のハンマー";
		id++;
		aitemname[id][0] = "IronHammer";
		aitemname[id][1] = "Iron Hammer";
		aitemname[id][2] = "鉄のハンマー";
		id++;
		aitemname[id][0] = "GoldenHammer";
		aitemname[id][1] = "Golden Hammer";
		aitemname[id][2] = "金のハンマー";
		id++;
		aitemname[id][0] = "DiamondHammer";
		aitemname[id][1] = "Diamond Hammer";
		aitemname[id][2] = "ダイヤのハンマー";
		id++;
		aitemname[id][0] = "WoodenHelmet";
		aitemname[id][1] = "Wooden Helmet";
		aitemname[id][2] = "木のヘルメット";
		id++;
		aitemname[id][0] = "WoodenChestplate";
		aitemname[id][1] = "Wooden Chestplate";
		aitemname[id][2] = "木のチェストプレート";
		id++;
		aitemname[id][0] = "WoodenLeggings";
		aitemname[id][1] = "Wooden Leggings";
		aitemname[id][2] = "木のレギンス";
		id++;
		aitemname[id][0] = "WoodenBoots";
		aitemname[id][1] = "Wooden Boots";
		aitemname[id][2] = "木のブーツ";
		id++;
		aitemname[id][0] = "StoneHelmet";
		aitemname[id][1] = "Stone Helmet";
		aitemname[id][2] = "石のヘルメット";
		id++;
		aitemname[id][0] = "StoneChestplate";
		aitemname[id][1] = "Stone Chestplate";
		aitemname[id][2] = "石のチェストプレート";
		id++;
		aitemname[id][0] = "StoneLeggings";
		aitemname[id][1] = "Stone Leggings";
		aitemname[id][2] = "石のレギンス";
		id++;
		aitemname[id][0] = "StoneBoots";
		aitemname[id][1] = "Stone Boots";
		aitemname[id][2] = "石のブーツ";
		id++;
		aitemname[id][0] = "GoldenShears";
		aitemname[id][1] = "Golden Shears";
		aitemname[id][2] = "金のハサミ";
		id++;
		aitemname[id][0] = "DiamondShears";
		aitemname[id][1] = "Diamond Shears";
		aitemname[id][2] = "ダイヤのハサミ";
		
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
		ablockname[id][0] = "CoalBlock";
		ablockname[id][1] = "Block of Coal";
		ablockname[id][2] = "石炭ブロック";
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
		ArmorTexture[id][0] = ObjectHeader.toLowerCase() + "textures/armor/ancient_1.png";
		ArmorTexture[id][1] = ObjectHeader.toLowerCase() + "textures/armor/ancient_2.png";
		id ++;
		ArmorTexture[id][0] = ObjectHeader.toLowerCase() + "textures/armor/coal_1.png";
		ArmorTexture[id][1] = ObjectHeader.toLowerCase() + "textures/armor/coal_2.png";
		id ++;
		ArmorTexture[id][0] = ObjectHeader.toLowerCase() + "textures/armor/obsidian_1.png";
		ArmorTexture[id][1] = ObjectHeader.toLowerCase() + "textures/armor/obsidian_2.png";
		id ++;
		ArmorTexture[id][0] = ObjectHeader.toLowerCase() + "textures/armor/wood_1.png";
		ArmorTexture[id][1] = ObjectHeader.toLowerCase() + "textures/armor/wood_2.png";
		id ++;
		ArmorTexture[id][0] = ObjectHeader.toLowerCase() + "textures/armor/stone_1.png";
		ArmorTexture[id][1] = ObjectHeader.toLowerCase() + "textures/armor/stone_2.png";
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
		//バニラツールの置換？
		//Utility_ToolConfig.loadConfig( event.getSuggestedConfigurationFile() );



		for ( int i = 0; i < aitemID.length; i ++ )
			if ( aitemname[i][0] != null )
				aitemname[i][0] = ObjectHeader + aitemname[i][0];
		for ( int i = 0; i < ablockqty; i ++ )
			ablockname[i][0] = ObjectHeader + ablockname[i][0];

	}







	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

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
		
		
		if ( isEnabled( "Coal" ) ) {
			
			id ++;
			ablock[id] = new Block_Base( ablockID[id], Material.rock ).setHardness( 3.0F ).setResistance( 5.0F ).setStepSound( Block.soundStoneFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );

			
			Block.setBurnProperties( ablockID[id], 30, 2 );
			fuelh.addFuel( ablockID[id], -1, 200 * 80 );

			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"ccc",
					"ccc",
					"ccc",
					'c', new ItemStack( Item.coal, 1, fm ) );
			GameRegistry.addRecipe( new ItemStack( Item.coal, 9 ),
					"c",
					'c', new ItemStack( ablock[id], 1, 0 ) );
			GameRegistry.addRecipe( new ItemStack( Item.diamond, 1 ),
					"ccc",
					"ccc",
					"ccc",
					'c', new ItemStack( ablock[id], 1, 0 ) );
			
			
		} else id += 1;
		
		
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
		
		//point: Glass Tools
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


			eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
			
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
		} else { id += 16; armorid ++; }

		
		//point: Ancient Tools
		if ( isEnabled( "Ancient" ) ) {
			
			//Ancient Stick
			id ++;

			aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"g",
					"g",
					'g', new ItemStack( Block.stoneBrick, 1, fm ) );

			
			

			EnumToolMaterial TMAncient = EnumHelper.addToolMaterial( "ANCIENT", 1, 198, 4.2F, 1.2F, 15 );
			TMAncient.customCraftingMaterial = new ItemStack( Block.stoneBrick, 1, fm ).getItem();
			
			
			//Ancient Sword
			id ++;

			aitem[id] = new Item_SpecialSword( aitemID[id], TMAncient ).addEffect( Potion.moveSlowdown.id, 0, 20 * 20, 0.05F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"e",
					"s",
					'e', new ItemStack( Block.stoneBrick, 1, fm ),
					's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );

			

			//Ancient Shovel
			id ++;

			aitem[id] = new Item_Spade( aitemID[id], TMAncient )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"s",
					"s",
					'e', new ItemStack( Block.stoneBrick, 1, fm ),
					's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );

			

			//Ancient Pickaxe
			id ++;

			aitem[id] = new Item_Pickaxe( aitemID[id], TMAncient )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"eee",
					" s ",
					" s ",
					'e', new ItemStack( Block.stoneBrick, 1, fm ),
					's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );

			

			//Ancient Axe
			id ++;

			aitem[id] = new Item_Axe( aitemID[id], TMAncient )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"es",
					" s",
					'e', new ItemStack( Block.stoneBrick, 1, fm ),
					's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );


			
			//Ancient Hoe
			id ++;

			aitem[id] = new Item_Hoe( aitemID[id], TMAncient )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"s ",
					"s ",
					'e', new ItemStack( Block.stoneBrick, 1, fm ),
					's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );


			
			//Ancient Hammer
			id ++;

			aitem[id] = new Item_Hammer( aitemID[id], TMAncient ).setMaxDamage( (int)( 64 * 3 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( Block.stoneBrick, 1, fm ),
					's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );




			armorid ++;
			EnumArmorMaterial AMAncient = EnumHelper.addArmorMaterial( "ANCIENT", 5, new int[] { 2, 5, 3, 1 }, 15 );
			AMAncient.customCraftingMaterial = new ItemStack( Block.stoneBrick, 1, 0 ).getItem();
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_ALLMOB, 0.15 );
			
			
			//Ancient Helmet
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 0 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					's', new ItemStack( Block.stoneBrick, 1, fm ) );



			//Ancient Chestplate
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"sss",
					"sss",
					's', new ItemStack( Block.stoneBrick, 1, fm ) );


			
			//Ancient Leggings
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 2 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					"s s",
					's', new ItemStack( Block.stoneBrick, 1, fm ) );


			//Ancient Boots
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 3 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"s s",
					's', new ItemStack( Block.stoneBrick, 1, fm ) );



			//Ancient Bow
			id ++;
			
			aitem[id] = new Item_Bow( aitemID[id], TMAncient ).setParameters( 0.6F, 1.0F, 0.4F, 0.0F ).setCraftingMaterial( getItemIDforCraft( "StoneStick" ) )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" st",
					"s t",
					" st",
					's', new ItemStack ( getItemIDforCraft( "StoneStick" ), 1, 0 ),
					't', Item.silk );



			//Ancient Arrow
			id ++;
			
			aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.7, 2.0, 0.0, 0.08, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
			.addEffect( Potion.moveSlowdown.id, 2, 6 * 20, 0.2F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
					"h",
					"s",
					"f",
					'h', new ItemStack ( Block.stoneBrick, 1, fm ),
					's', new ItemStack ( getItemIDforCraft( "StoneStick" ), 1, 0 ),
					'f', Item.feather );

			
			id = id / 16 * 16 + 15;
		} else id += 16;


		

		
		//point: Coal Tools
		if ( isEnabled( "Coal" ) ) {

			EnumToolMaterial TMCoal = EnumHelper.addToolMaterial( "COAL", 1, 472, 5.0F, 2.0F, 8 );
			TMCoal.customCraftingMaterial = new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ).getItem();
			
			
			//Coal Sword
			id ++;

			aitem[id] = new Item_SpecialSword( aitemID[id], TMCoal ).addEffect( Item_SpecialSword.EFFECT_IGNITION, 0, 4, 0.1F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );
			eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_ZOMBIE, 0.15 );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"e",
					"s",
					'e', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );

			

			//Coal Shovel
			id ++;

			aitem[id] = new Item_Spade( aitemID[id], TMCoal )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"s",
					"s",
					'e', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			

			//Coal Pickaxe
			id ++;

			aitem[id] = new Item_Pickaxe( aitemID[id], TMCoal )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"eee",
					" s ",
					" s ",
					'e', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );

			

			//Coal Axe
			id ++;

			aitem[id] = new Item_Axe( aitemID[id], TMCoal )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"es",
					" s",
					'e', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );


			
			//Coal Hoe
			id ++;

			aitem[id] = new Item_Hoe( aitemID[id], TMCoal )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"s ",
					"s ",
					'e', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );


			
			//Coal Hammer
			id ++;

			aitem[id] = new Item_Hammer( aitemID[id], TMCoal ).setBurnTick( 200 * 80 ).setMaxDamage( (int)( 64 * 7.5 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );






			armorid ++;
			EnumArmorMaterial AMCoal = EnumHelper.addArmorMaterial( "COAL", 18, new int[] { 2, 6, 4, 2 }, 8 );
			AMCoal.customCraftingMaterial = new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ).getItem();
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_OVERWORLD, 0.15 );
			
			
			//Coal Helmet
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 0 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					's', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ) );



			//Coal Chestplate
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"sss",
					"sss",
					's', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ) );


			
			//Coal Leggings
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 2 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );

			GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					"s s",
					's', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ) );

			

			//Coal Boots
			id ++;

			aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 3 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"s s",
					's', new ItemStack( getBlockID( "CoalBlock" ), 1, 0 ) );



			//Coal Bow
			id ++;

			aitem[id] = new Item_Bow( aitemID[id], TMCoal ).setParameters( 0.9F, 1.0F, 0.2F, 0.2F ).setCraftingMaterial( getBlockID( "CoalBlock" ) )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 * 80 );
				
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" st",
					"s t",
					" st",
					's', new ItemStack ( getBlockID( "CoalBlock" ), 1, 0 ),
					't', Item.silk );



			//Coal Arrow
			id++;

			aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.9, 0.4, 0.2, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
			.addEffect( Struct_Arrow.EFFECT_FLAME, 0, 0, 1.0F )
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
					'h', new ItemStack ( Item.coal, 1, fm ),
					's', new ItemStack ( Item.stick, 1, 0 ),
					'f', Item.feather );

			
			id = id / 16 * 16 + 15;
		} else { id += 16; armorid ++; }


		
		//point: Obsidian Tools
		if ( isEnabled( "Obsidian" ) ) {
			
			EnumToolMaterial TMObsidian = EnumHelper.addToolMaterial( "OBSIDIAN", 1, 2341, 5.5F, 1.8F, 1 );
			TMObsidian.customCraftingMaterial = new ItemStack( Block.obsidian, 1, fm ).getItem();
			
			
			//Obsidian Sword
			id ++;

			aitem[id] = new Item_Sword( aitemID[id], TMObsidian )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"e",
					"s",
					'e', new ItemStack( Block.obsidian, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );

			

			//Obsidian Shovel
			id ++;

			aitem[id] = new Item_Spade( aitemID[id], TMObsidian )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"e",
					"s",
					"s",
					'e', new ItemStack( Block.obsidian, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			

			//Obsidian Pickaxe
			id ++;

			aitem[id] = new Item_Pickaxe( aitemID[id], TMObsidian )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"eee",
					" s ",
					" s ",
					'e', new ItemStack( Block.obsidian, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );

			

			//Obsidian Axe
			id ++;

			aitem[id] = new Item_Axe( aitemID[id], TMObsidian )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"es",
					" s",
					'e', new ItemStack( Block.obsidian, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );


			
			//Obsidian Hoe
			id ++;

			aitem[id] = new Item_Hoe( aitemID[id], TMObsidian )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"ee",
					"s ",
					"s ",
					'e', new ItemStack( Block.obsidian, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );


			
			//Obsidian Hammer
			id ++;

			aitem[id] = new Item_Hammer( aitemID[id], TMObsidian ).setMaxDamage( (int)( 64 * 36 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( Block.obsidian, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );




			armorid ++;
			EnumArmorMaterial AMObsidian = EnumHelper.addArmorMaterial( "OBSIDIAN", 5, new int[] { 2, 6, 5, 2 }, 1 );
			AMObsidian.customCraftingMaterial = new ItemStack( Block.obsidian, 1, 0 ).getItem();
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_ALLMOB, 0.15 );
			
			
			//Obsidian Helmet
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 0 )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 0, Item_SpecialArmor.FLAG_FULLEQ )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					's', new ItemStack( Block.obsidian, 1, 0 ) );



			//Obsidian Chestplate
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 1 )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 0, Item_SpecialArmor.FLAG_FULLEQ )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"sss",
					"sss",
					's', new ItemStack( Block.obsidian, 1, 0 ) );


			
			//Obsidian Leggings
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 2 )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 0, Item_SpecialArmor.FLAG_FULLEQ )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					"s s",
					's', new ItemStack( Block.obsidian, 1, 0 ) );

			

			//Obsidian Boots
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 3 )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 0, Item_SpecialArmor.FLAG_FULLEQ )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"s s",
					's', new ItemStack( Block.obsidian, 1, 0 ) );


			
			//Obsidian Shears
			id ++;

			aitem[id] = new Item_Shears( aitemID[id], TMObsidian )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s",
					"s ",
					's', new ItemStack( Block.obsidian, 1, 0 ) );

			

			//Obsidian Bow
			id ++;
			
			aitem[id] = new Item_Bow( aitemID[id], TMObsidian ).setParameters( 0.5F, 1.1F, 0.5F, 0.0F ).setCraftingMaterial( Block.obsidian.blockID )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" st",
					"s t",
					" st",
					's', new ItemStack ( Block.obsidian, 1, 0 ),
					't', Item.silk );



			//Obsidian Arrow
			id ++;
			
			aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.7, 3.2, 0.0, 0.08, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
					"h",
					"s",
					"f",
					'h', new ItemStack ( Block.obsidian, 1, 0 ),
					's', new ItemStack ( Item.stick, 1, 0 ),
					'f', Item.feather );

			
			id = id / 16 * 16 + 15;
		} else { id += 16; armorid ++; }

		
		
		//point: Ex Vanilla Tools
		if ( isEnabled ( "ExVanilla" ) ) {
			
			//note: EnumToolMaterial.WOOD を使うと問答無用で竈の燃料になってしまう(すると無限燃焼の不具合を起こします)ので、これで対応します
			EnumToolMaterial TMWood = EnumHelper.addToolMaterial( "NONFLAMMABLE_WOOD", EnumToolMaterial.WOOD.getHarvestLevel(), EnumToolMaterial.WOOD.getMaxUses(),
					EnumToolMaterial.WOOD.getEfficiencyOnProperMaterial(), EnumToolMaterial.WOOD.getDamageVsEntity(), EnumToolMaterial.WOOD.getEnchantability() );

			//Wooden Hammer
			id ++;
			
			aitem[id] = new Item_Hammer( aitemID[id], TMWood ).setBurnTick( 200 ).setMaxDamage( (int)( 64 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( Block.planks, 1, fm ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			
			//Stone Hammer
			id ++;
			
			aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.STONE ).setMaxDamage( (int)( 64 * 2 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( Block.cobblestone, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			
			//Iron Hammer
			id ++;
			
			aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.IRON ).setMaxDamage( (int)( 64 * 4 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( Item.ingotIron, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			
			//Gold Hammer
			id ++;
			
			aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.GOLD ).setMaxDamage( (int)( 64 * 0.5 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( Item.ingotGold, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			
			//Diamond Hammer
			id ++;
			
			aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.EMERALD ).setMaxDamage( (int)( 64 * 24 ) - 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( Item.diamond, 1, 0 ),
					's', new ItemStack( Item.stick, 1, 0 ) );
			
			
			
			armorid ++;
			EnumArmorMaterial AMWood = EnumHelper.addArmorMaterial( "WOOD", 3, new int[] { 1, 2, 2, 1 }, 15 );
			AMWood.customCraftingMaterial = new ItemStack( Block.planks, 1, 0 ).getItem();
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_OVERWORLD, 0.15 );
			
			
			//Wooden Helmet
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 0 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					's', new ItemStack( Block.planks, 1, fm ) );



			//Wooden Chestplate
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 1 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"sss",
					"sss",
					's', new ItemStack( Block.planks, 1, fm ) );


			
			//Wooden Leggings
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 2 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					"s s",
					's', new ItemStack( Block.planks, 1, fm ) );

			

			//Wooden Boots
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 3 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"s s",
					's', new ItemStack( Block.planks, 1, fm ) );

			
			
			armorid ++;
			EnumArmorMaterial AMStone = EnumHelper.addArmorMaterial( "STONE", 4, new int[] { 2, 4, 3, 1 }, 5 );
			AMStone.customCraftingMaterial = new ItemStack( Block.cobblestone, 1, 0 ).getItem();
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_ALLMOB, 0.15 );
			
			
			//Stone Helmet
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 0 ).setIsDisposable( true )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					's', new ItemStack( Block.cobblestone, 1, fm ) );



			//Stone Chestplate
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 1 ).setIsDisposable( true )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"sss",
					"sss",
					's', new ItemStack( Block.cobblestone, 1, fm ) );


			
			//Stone Leggings
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 2 ).setIsDisposable( true )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
					"sss",
					"s s",
					"s s",
					's', new ItemStack( Block.cobblestone, 1, fm ) );

			

			//Stone Boots
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 3 ).setIsDisposable( true )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			fuelh.addFuel( aitemID[id], -1, 200 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"s s",
					"s s",
					's', new ItemStack( Block.cobblestone, 1, fm ) );

			
			
			//Golden Shears
			id ++;

			aitem[id] = new Item_Shears( aitemID[id], EnumToolMaterial.GOLD )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s",
					"s ",
					's', new ItemStack( Item.ingotGold, 1, 0 ) );
			
			
			//Diamond Shears
			id ++;

			aitem[id] = new Item_Shears( aitemID[id], EnumToolMaterial.EMERALD )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" s",
					"s ",
					's', new ItemStack( Item.diamond, 1, 0 ) );

			
			id = id / 16 * 16 + 15;
		} else { id += 16; armorid += 2; }
		
		
		//fixme: フラグの有効化とアーマー
		if ( replaceVanillaTools ) {

			eequip.addMobWeaponEquipment( new ItemStack( Item.swordWood ), Event_MobEquipment.FLAG_ZOMBIE, 0.15 );
			eequip.addMobWeaponEquipment( new ItemStack( Item.swordStone ), Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
			eequip.addMobWeaponEquipment( new ItemStack( Item.swordDiamond ), Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
			eequip.addMobWeaponEquipment( new ItemStack( Item.swordGold ), Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
			
			//Iron Shovel
			Item.itemsList[Item.shovelIron.itemID] = new Item_Spade( Item.shovelIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "shovelIron" ).func_111206_d( "iron_shovel" );

			//Iron Pickaxe
			Item.itemsList[Item.pickaxeIron.itemID] = new Item_Pickaxe( Item.pickaxeIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "pickaxeIron" ).func_111206_d( "iron_pickaxe" );

			//Iron Axe
			Item.itemsList[Item.axeIron.itemID] = new Item_Axe( Item.axeIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "hatchetIron" ).func_111206_d( "iron_axe" );

			//Iron Sword
			Item.itemsList[Item.swordIron.itemID] = new Item_Sword( Item.swordIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "swordIron" ).func_111206_d( "iron_sword" );

			//Wooden Sword
			Item.itemsList[Item.swordWood.itemID] = new Item_Sword( Item.swordWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "swordWood" ).func_111206_d( "wood_sword" );

			//Wooden Shovel
			Item.itemsList[Item.shovelWood.itemID] = new Item_Spade( Item.shovelWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "shovelWood" ).func_111206_d( "wood_shovel" );

			//Wooden Pickaxe
			Item.itemsList[Item.pickaxeWood.itemID] = new Item_Pickaxe( Item.pickaxeWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "pickaxeWood" ).func_111206_d( "wood_pickaxe" );

			//Wooden Axe
			Item.itemsList[Item.axeWood.itemID] = new Item_Axe( Item.axeWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "hatchetWood" ).func_111206_d( "wood_axe" );

			//Stone Sword
			Item.itemsList[Item.swordStone.itemID] = new Item_Sword( Item.swordStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "swordStone" ).func_111206_d( "stone_sword" );

			//Stone Shovel
			Item.itemsList[Item.shovelStone.itemID] = new Item_Spade( Item.shovelStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "shovelStone" ).func_111206_d( "stone_shovel" );

			//Stone Pickaxe
			Item.itemsList[Item.pickaxeStone.itemID] = new Item_Pickaxe( Item.pickaxeStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "pickaxeStone" ).func_111206_d( "stone_pickaxe" );

			//Stone Axe
			Item.itemsList[Item.axeStone.itemID] = new Item_Axe( Item.axeStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "hatchetStone" ).func_111206_d( "stone_axe" );

			//Diamond Sword
			Item.itemsList[Item.swordDiamond.itemID] = new Item_Sword( Item.swordDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "swordDiamond" ).func_111206_d( "diamond_sword" );

			//Diamond Shovel
			Item.itemsList[Item.shovelDiamond.itemID] = new Item_Spade( Item.shovelDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "shovelDiamond" ).func_111206_d( "diamond_shovel" );

			//Diamond Pickaxe
			Item.itemsList[Item.pickaxeDiamond.itemID] = new Item_Pickaxe( Item.pickaxeDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "pickaxeDiamond" ).func_111206_d( "diamond_pickaxe" );

			//Diamond Axe
			Item.itemsList[Item.axeDiamond.itemID] = new Item_Axe( Item.axeDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "hatchetDiamond" ).func_111206_d( "diamond_axe" );

			//Gold Sword
			Item.itemsList[Item.swordGold.itemID] = new Item_Sword( Item.swordGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "swordGold" ).func_111206_d( "gold_sword" );

			//Gold Shovel
			Item.itemsList[Item.shovelGold.itemID] = new Item_Spade( Item.shovelGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "shovelGold" ).func_111206_d( "gold_shovel" );

			//Gold Pickaxe
			Item.itemsList[Item.pickaxeGold.itemID] = new Item_Pickaxe( Item.pickaxeGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "pickaxeGold" ).func_111206_d( "gold_pickaxe" );

			//Gold Axe
			Item.itemsList[Item.axeGold.itemID] = new Item_Axe( Item.axeGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "hatchetGold" ).func_111206_d( "gold_axe" );

			//Wooden Hoe
			Item.itemsList[Item.hoeWood.itemID] = new Item_Hoe( Item.hoeWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "hoeWood" ).func_111206_d( "wood_hoe" );

			//Stone Hoe
			Item.itemsList[Item.hoeStone.itemID] = new Item_Hoe( Item.hoeStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "hoeStone" ).func_111206_d( "stone_hoe" );

			//Iron Hoe
			Item.itemsList[Item.hoeIron.itemID] = new Item_Hoe( Item.hoeIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "hoeIron" ).func_111206_d( "iron_hoe" );

			//Diamond Hoe
			Item.itemsList[Item.hoeDiamond.itemID] = new Item_Hoe( Item.hoeDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "hoeDiamond" ).func_111206_d( "diamond_hoe" );

			//Gold Hoe
			Item.itemsList[Item.hoeGold.itemID] = new Item_Hoe( Item.hoeGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "hoeGold" ).func_111206_d( "gold_hoe" );

			//Shears
			Item.itemsList[Item.shears.itemID] = new Item_Shears( Item.shears.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "shears" ).func_111206_d( "shears" );

		}
		
		
		
		
		
		//point: Slime Tools
		if ( isEnabled( "Slime" ) ) {

			EnumToolMaterial TMSlime = EnumHelper.addToolMaterial( "SLIME", 0, 96, 3.0F, 0.3F, 49 );
			TMSlime.customCraftingMaterial = new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ).getItem();
			
			
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
			AMSlime.customCraftingMaterial = new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ).getItem();
			eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
					Event_MobEquipment.FLAG_OVERWORLD, 0.15 );
			
			
			//Slime Helmet
			id ++;

			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 0 ).setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) + 100, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

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
		} else { id += 16; armorid ++; }


		











		//point: add new item




		//point: arrow register
		{
			Event_Arrow earrow = new Event_Arrow();

			earrow.addArrow( Item.arrow.itemID );
			if ( isEnabled( "Glass" ) ) earrow.addArrow( getItemIDforCraft( "GlassArrow" ) );
			if ( isEnabled( "Ancient" ) ) earrow.addArrow( getItemIDforCraft( "AncientArrow" ) );
			if ( isEnabled( "Coal" ) ) earrow.addArrow( getItemIDforCraft( "CoalArrow" ) );
			if ( isEnabled( "Obsidian" ) ) earrow.addArrow( getItemIDforCraft( "ObsidianArrow" ) );
			if ( isEnabled( "Slime" ) ) earrow.addArrow( getItemIDforCraft( "SlimeArrow" ) );
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
