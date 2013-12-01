package andmod.Util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
		modid	= "AndanteMod_Util",
		name	= "And Utility",
		version	= "1.6.2.5"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)


public class AndMod_Util implements IFuelHandler {

	private int fm = OreDictionary.WILDCARD_VALUE;

	

	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		//info: キノコシチュー;同種でもいいが３個必要
		GameRegistry.addShapelessRecipe( new ItemStack( Item.bowlSoup, 1 ),
			new ItemStack( Block.mushroomBrown, 1 ),
			new ItemStack( Block.mushroomBrown, 1 ),
			new ItemStack( Block.mushroomBrown, 1 ),
			new ItemStack( Item.bowlEmpty, 1 ) ) ;

		GameRegistry.addShapelessRecipe( new ItemStack( Item.bowlSoup, 1 ),
			new ItemStack( Block.mushroomRed, 1 ),
			new ItemStack( Block.mushroomRed, 1 ),
			new ItemStack( Block.mushroomRed, 1 ),
			new ItemStack( Item.bowlEmpty, 1 ) ) ;

		//info: 羊毛を糸に
		GameRegistry.addShapelessRecipe( new ItemStack( Item.silk, 4 ),
			new ItemStack( Block.cloth, 1, fm ) ) ;

		//info: 苔石系;つたを使う
		GameRegistry.addShapelessRecipe( new ItemStack( Block.cobblestoneMossy, 1 ), 
			new ItemStack( Block.vine, 1 ),
			new ItemStack( Block.cobblestone, 1 ) ) ;
		GameRegistry.addShapelessRecipe( new ItemStack( Block.stoneBrick, 1, 1 ),
			new ItemStack( Block.vine, 1 ),
			new ItemStack( Block.stoneBrick, 1, 0 ) ) ;
		GameRegistry.addShapelessRecipe( new ItemStack( Block.cobblestoneWall, 1, 1 ),
			new ItemStack( Block.vine, 1 ),
			new ItemStack( Block.cobblestoneWall, 1, 0 ) ) ;

		//info: 模様入り石煉瓦
		GameRegistry.addRecipe( new ItemStack( Block.stoneBrick, 8, 3 ),
				"bbb",
				"b b",
				"bbb",
				'b', new ItemStack( Block.stoneBrick, 1, 0 ) ) ;

		//info: 土関係
		FurnaceRecipes.smelting().addSmelting( Block.leaves.blockID, new ItemStack( Block.dirt, 1 ), 0.1F );
		GameRegistry.addShapelessRecipe( new ItemStack( Block.grass, 1 ),
			new ItemStack( Block.tallGrass, 1, fm ),
			new ItemStack( Block.dirt, 1 ) ) ;
		FurnaceRecipes.smelting().addSmelting( Block.mushroomCapBrown.blockID, new ItemStack( Block.mycelium, 1 ), 0.3F );
		FurnaceRecipes.smelting().addSmelting( Block.mushroomCapRed.blockID, new ItemStack( Block.mycelium, 1 ), 0.3F );

		//info: エンチャントの瓶のレシピ
		GameRegistry.addShapelessRecipe( new ItemStack( Item.expBottle, 3, 0 ),
			new ItemStack( Item.glassBottle, 1 ),
			new ItemStack( Item.glassBottle, 1 ),
			new ItemStack( Item.glassBottle, 1 ),
			new ItemStack( Item.eyeOfEnder, 1 ) ) ;

		//info: 砂利４つで火打石
		GameRegistry.addRecipe( new ItemStack( Item.flint, 1, 0 ),
				"gg",
				"gg",
				'g', new ItemStack( Block.gravel, 1 ) ) ;

		//info: 苗木を燃やすと枯れ木に
		FurnaceRecipes.smelting().addSmelting( Block.sapling.blockID, new ItemStack( Block.deadBush, 1 ), 0.1F );

		//info: 松明レシピの追加
		GameRegistry.addRecipe( new ItemStack( Block.torchWood, 32, 0 ),
				"c",
				"s",
				'c', new ItemStack( Item.coal, 1, fm ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) ) ;
		GameRegistry.addRecipe( new ItemStack( Block.torchWood, 16, 0 ),
				"c",
				"s",
				'c', new ItemStack( Item.blazePowder, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) ) ;
		GameRegistry.addRecipe( new ItemStack( Block.torchWood, 64, 0 ),
				"c",
				"s",
				'c', new ItemStack( Item.blazePowder, 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) ) ;

		//info: エンドポータルフレーム：biosphereユーザーのために
		GameRegistry.addRecipe( new ItemStack( Block.endPortalFrame, 1, 0 ),
				" y ",
				"ppp",
				"eee",
				'y', new ItemStack( Item.eyeOfEnder, 1, 0 ),
				'p', new ItemStack( Item.enderPearl, 1, 0 ),
				'e', new ItemStack( Block.whiteStone, 1, 0 ) ) ;

		//info: 蜘蛛の巣
		GameRegistry.addRecipe( new ItemStack( Block.web, 1, 0 ),
				"s s",
				" s ",
				"s s",
				's', new ItemStack( Item.silk, 1, 0 ) ) ;

		//info: 蓮の葉
		GameRegistry.addRecipe( new ItemStack(Block.waterlily, 2, 0 ),
				"s s",
				"sss",
				" s ",
				's', new ItemStack( Block.tallGrass, 1, fm ) ) ;


		//info: ネザーウォートを赤石粉に;9つでクラフトに変更
		GameRegistry.addShapelessRecipe( new ItemStack( Item.redstone, 1, 0 ), 
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ),
			new ItemStack( Item.netherStalkSeeds, 1, 0 ) ) ;

		//info: 木炭->石炭ブロック->ダイヤ
		GameRegistry.addRecipe( new ItemStack( Block.field_111034_cE, 1, 0 ), 
				"ccc",
				"ccc",
				"ccc",
				'c', new ItemStack( Item.coal, 1, fm ) ) ;
		GameRegistry.addRecipe( new ItemStack( Item.diamond, 1, 0 ), 
				"ccc",
				"ccc",
				"ccc",
				'c', new ItemStack( Block.field_111034_cE, 1, 0 ) ) ;

		//info: スイカを還元
		GameRegistry.addShapelessRecipe( new ItemStack( Item.melon, 9, 0 ), 
			new ItemStack( Block.melon, 1, 0 ) ) ;
				
		//info: エンドストーン生成
		GameRegistry.addRecipe( new ItemStack( Block.whiteStone, 8, 0 ),
				"sss",
				"ses",
				"sss",
				's', new ItemStack( Block.cobblestone, 1, 0 ),
				'e', new ItemStack( Item.enderPearl, 1, 0 ) ) ;

		
		//info: ポータルフレームを破壊可能に　回収はできません
		Block.blocksList[ Block.endPortalFrame.blockID ] =
				Block.blocksList[ Block.endPortalFrame.blockID ].setHardness( 50.0F ).setResistance( 2000.0F );
		//意味は特にないです。気分的問題
		MinecraftForge.setBlockHarvestLevel( Block.endPortalFrame, "pickaxe", 3 );


		//info: 葉を斧で高速破壊できる
		MinecraftForge.setBlockHarvestLevel( Block.leaves, "axe", 0 );
		
		
		//info: 蓮の葉を壊れにくくする　畑とかにどうぞ
		Block.blocksList[ Block.waterlily.blockID ] =
				Block.blocksList[ Block.waterlily.blockID ].setHardness( 0.2F );

		
		//info: ブロックをクリエイティブタブに登録
		Block.blocksList[ Block.commandBlock.blockID ] =
				Block.blocksList[ Block.commandBlock.blockID ].setCreativeTab( CreativeTabs.tabRedstone );
		Block.blocksList[ Block.dragonEgg.blockID ] =
				Block.blocksList[ Block.dragonEgg.blockID ].setCreativeTab( CreativeTabs.tabDecorations );
		Block.blocksList[ Block.portal.blockID ] =
				Block.blocksList[ Block.portal.blockID ].setCreativeTab( CreativeTabs.tabDecorations );
		Block.blocksList[ Block.endPortal.blockID ] =
				Block.blocksList[ Block.endPortal.blockID ].setCreativeTab( CreativeTabs.tabDecorations );
		
			
		//info: スポーンエッグ拡張
		EntityList.entityEggs.put( 53, new EntityEggInfo( 53, 0x00AFAF, 0x799C65 ) );	//Giant
		EntityList.entityEggs.put( 63, new EntityEggInfo( 63, 0x161616, 0x00003A ) );	//Ender Dragon
		EntityList.entityEggs.put( 64, new EntityEggInfo( 64, 0x141414, 0xC1C1C1 ) );	//Wither
		EntityList.entityEggs.put( 97, new EntityEggInfo( 97, 0xEEFFFF, 0xE3901D ) );	//Snow Golem
		EntityList.entityEggs.put( 99, new EntityEggInfo( 99, 0xE0E0E0, 0xCACACA ) );	//Iron Golem
		
		GameRegistry.addRecipe( new ItemStack( Item.monsterPlacer, 1, 97 ),
				"p",
				"s",
				"s",
				'p', new ItemStack( Block.pumpkin ), 
				's', new ItemStack( Block.blockSnow ) ) ;
		
		GameRegistry.addRecipe( new ItemStack( Item.monsterPlacer, 1, 99 ),
				" p ",
				"sss",
				" s ",
				'p', new ItemStack( Block.pumpkin ), 
				's', new ItemStack( Block.blockIron ) ) ;
		
		
		//info: 燃料の追加
		GameRegistry.registerFuelHandler( new AndMod_Util() );
	}






	@Override
	public int getBurnTime( ItemStack fuel ) {
		
		if ( fuel.itemID == Item.seeds.itemID )
			return 200 / 4;
		else if ( fuel.itemID == Block.leaves.blockID )
			return 200 / 2;
		else if ( fuel.itemID == Block.deadBush.blockID )
			return 200;
		else if ( fuel.itemID == Block.tallGrass.blockID )
			return 200 / 2;
		else if ( fuel.itemID == Block.cloth.blockID )
			return 200;
		else if ( fuel.itemID == Item.reed.itemID )
			return 200 / 2;
		else if ( fuel.itemID == Item.wheat.itemID )
			return 200 * 3 / 4;
		else if ( fuel.itemID == Item.bowlEmpty.itemID )
			return 200;
		else if ( fuel.itemID == Item.blazePowder.itemID )
			return 200 * 6;
		else if ( fuel.itemID == Item.magmaCream.itemID )
			return 200 * 12;
		else if ( fuel.itemID == Item.doorWood.itemID )
			return 200 * 4;
		else if ( fuel.itemID == Item.boat.itemID )
			return 200 * 4;
		else if ( fuel.itemID == Block.field_111038_cB.blockID )		//hay
			return (int)( 200 * 7.5 );
		
		
		return 0;
	}

}
