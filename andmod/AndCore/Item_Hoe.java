package andmod.AndCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class Item_Hoe extends ItemHoe {

	protected int enchantability;
	protected int harvestLevel;
	
	/** 拡張機能の使用可否。アイテム宣言よりも先に設定してください。 */
	public static boolean canUseExtendedMode = true;


	public Item_Hoe( int itemID, EnumToolMaterial material ) {
		super( itemID, material );

		enchantability = material.getEnchantability();
		harvestLevel = canUseExtendedMode ? material.getHarvestLevel() : 0;
	}


	public Item_Hoe( int itemID, EnumToolMaterial material, int durability, int harvestLevel, int enchantability ) {
		super( itemID, material );

		this.enchantability = enchantability;
		this.harvestLevel = canUseExtendedMode ? material.getHarvestLevel() : 0;
		setMaxDamage( durability );
	}


	@Override
	public int getItemEnchantability() {
		return enchantability;
	}


	@Override
	public boolean onItemUse( ItemStack items, EntityPlayer eplayer, World world, int bx, int by, int bz, int side, float px, float py, float pz ) {

		int flag = 0;
		boolean consumeSeeds = !eplayer.capabilities.isCreativeMode;



		{

			int tid = world.getBlockId( bx, by, bz );
			while ( tid == Block.crops.blockID || tid == Block.potato.blockID || tid == Block.carrot.blockID || tid == Block.netherStalk.blockID ||
					tid == Block.tallGrass.blockID || tid == Block.plantYellow.blockID || tid == Block.plantRed.blockID || tid == Block.reed.blockID ||
					tid == Block.waterlily.blockID || tid == Block.cactus.blockID ) {
				by --;
				tid = world.getBlockId( bx, by, bz );
			}
		}


		for ( int z = -( harvestLevel >= 1 ? harvestLevel - 1 : 0 ); z <= ( harvestLevel >= 1 ? harvestLevel - 1 : 0 ); z++ )
			for ( int x = -( harvestLevel >= 1 ? harvestLevel - 1 : 0 ); x <= ( harvestLevel >= 1 ? harvestLevel - 1 : 0 ); x++ ) {

				int cx = bx + x;
				int cy = by;
				int cz = bz + z;
				boolean used = false;	//一回の操作のうちで使えばtrue




				if ( !eplayer.canPlayerEdit( cx, cy, cz, side, items ) ) continue;

				else {
					UseHoeEvent event = new UseHoeEvent( eplayer, items, world, cx, cy, cz );
					if ( MinecraftForge.EVENT_BUS.post( event ) ) continue;

					if ( event.getResult() == Result.ALLOW ) {
						//items.damageItem(1, eplayer);
						flag ++;
						continue;
					}

					int selectedID = world.getBlockId( cx, cy, cz );
					int topID = world.getBlockId( cx, cy + 1, cz );
					int topMeta = world.getBlockMetadata( cx, cy + 1, cz );


					if ( !world.isRemote ) {

						//草刈り
						if ( harvestLevel >= 1 && (
								topID == Block.tallGrass.blockID || topID == Block.plantYellow.blockID || topID == Block.plantRed.blockID || topID == Block.deadBush.blockID ||
								topID == Block.melon.blockID || topID == Block.pumpkin.blockID || topID == Block.cactus.blockID ||
								topID == Block.mushroomBrown.blockID || topID == Block.mushroomRed.blockID ) ) {
							world.destroyBlock( cx, cy + 1, cz, true );
							topID = 0;
							used = true;
						}



						//耕地にするコード note:スニーク中は耕地にしません
						if ( ( side != 0 && ( topID == 0 || topID == Block.crops.blockID || topID == Block.potato.blockID || topID == Block.carrot.blockID ) ) && ( !eplayer.isSneaking() ) && ( selectedID == Block.grass.blockID || selectedID == Block.mycelium.blockID || selectedID == Block.dirt.blockID) ) {

							world.setBlock( cx, cy, cz, Block.tilledField.blockID );
							selectedID = Block.tilledField.blockID;
							used = true;

						}



						//小麦
						if ( harvestLevel >= 1 && ( selectedID == Block.tilledField.blockID || selectedID == Block.dirt.blockID || selectedID == Block.grass.blockID || selectedID == Block.mycelium.blockID )
								&& topID == Block.crops.blockID && topMeta >= 7 ) {

							world.destroyBlock( cx, cy + 1, cz, true ); topID = 0;

							if ( eplayer.inventory.hasItem ( Item.seeds.itemID ) ) {

								world.setBlock( cx, cy + 1, cz, Block.crops.blockID );
								topID = Block.crops.blockID;
								if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.seeds.itemID );

							} else
								for ( int i = 0; i < eplayer.inventory.getSizeInventory() - 4; i ++ ) {
									ItemStack sdt = eplayer.inventory.getStackInSlot( i );
									if ( sdt == null ) continue;

									if ( sdt.itemID == Item.potato.itemID ) {

										world.setBlock( cx, cy + 1, cz, Block.potato.blockID );
										topID = Block.potato.blockID;
										if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.potato.itemID );

										break;
									}
									if ( sdt.itemID == Item.carrot.itemID ) {

										world.setBlock( cx, cy + 1, cz, Block.carrot.blockID );
										topID = Block.carrot.blockID;
										if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.carrot.itemID );

										break;
									}

								}

							used = true;

						}



						//芋
						if ( harvestLevel >= 1 && ( selectedID == Block.tilledField.blockID || selectedID == Block.dirt.blockID || selectedID == Block.grass.blockID || selectedID == Block.mycelium.blockID )
								&& topID == Block.potato.blockID && topMeta >= 7 ) {

							world.destroyBlock( cx, cy + 1, cz, true ); topID = 0;

							if ( eplayer.inventory.hasItem ( Item.potato.itemID ) ) {

								world.setBlock( cx, cy + 1, cz, Block.potato.blockID );
								topID = Block.potato.blockID;
								if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.potato.itemID );

							} else
								for ( int i = 0; i < eplayer.inventory.getSizeInventory() - 4; i ++ ) {
									ItemStack sdt = eplayer.inventory.getStackInSlot( i );
									if ( sdt == null ) continue;

									if ( sdt.itemID == Item.carrot.itemID ) {

										world.setBlock( cx, cy + 1, cz, Block.carrot.blockID );
										topID = Block.carrot.blockID;
										if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem(Item.carrot.itemID);

										break;
									}
									if ( sdt.itemID == Item.seeds.itemID ) {

										world.setBlock( cx, cy + 1, cz, Block.crops.blockID );
										topID = Block.crops.blockID;
										if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem(Item.seeds.itemID);

										break;
									}

								}

							used = true;
						}



						//人参
						if ( harvestLevel >= 1 && ( selectedID == Block.tilledField.blockID || selectedID == Block.dirt.blockID || selectedID == Block.grass.blockID || selectedID == Block.mycelium.blockID )
								&& topID == Block.carrot.blockID && topMeta >= 7 ) {

							world.destroyBlock(cx, cy + 1, cz, true); topID = 0;

							if ( eplayer.inventory.hasItem ( Item.carrot.itemID ) ) {

								world.setBlock( cx, cy + 1, cz, Block.carrot.blockID );
								topID = Block.carrot.blockID;
								if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.carrot.itemID );


							} else
								for ( int i = 0; i < eplayer.inventory.getSizeInventory() - 4; i ++ ) {
									ItemStack sdt = eplayer.inventory.getStackInSlot( i );
									if ( sdt == null ) continue;

									if ( sdt.itemID == Item.potato.itemID ) {

										world.setBlock( cx, cy + 1, cz, Block.potato.blockID );
										topID = Block.potato.blockID;
										if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.potato.itemID );

										break;
									}
									if ( sdt.itemID == Item.seeds.itemID ) {

										world.setBlock( cx, cy + 1, cz, Block.crops.blockID );
										topID = Block.crops.blockID;
										if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.seeds.itemID );

										break;
									}

								}

							used = true;
						}


						//ネザーウォート
						if ( harvestLevel >= 1 && selectedID == Block.slowSand.blockID && topID == Block.netherStalk.blockID && topMeta >= 3 ) {
							world.destroyBlock( cx, cy + 1, cz, true ); topID = 0;

							if ( eplayer.inventory.hasItem ( Item.netherStalkSeeds.itemID ) ) {
								world.setBlock( cx, cy + 1, cz, Block.netherStalk.blockID );
								topID = Block.netherStalk.blockID;
								if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.netherStalkSeeds.itemID );
							}

							used = true;
						}



						//サトウキビ
						if ( harvestLevel >= 1 && ( selectedID == Block.sand.blockID ) && topID == Block.reed.blockID )
							if ( world.getBlockId( cx, cy + 2, cz ) == Block.reed.blockID ) {

								world.destroyBlock( cx, cy + 2, cz, true );

								used = true;
							}



						//空き耕地
						if ( harvestLevel >= 1 && selectedID == Block.tilledField.blockID && topID == 0 )
							for ( int i = 0; i < eplayer.inventory.getSizeInventory() - 4; i ++ ) {
								ItemStack sdt = eplayer.inventory.getStackInSlot( i );
								if ( sdt == null ) continue;

								if ( sdt.itemID == Item.potato.itemID ) {
									world.setBlock( cx, cy + 1, cz, Block.potato.blockID );
									topID = Block.potato.blockID;
									if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.potato.itemID );
									used = true; break;
								}
								if ( sdt.itemID == Item.carrot.itemID ) {
									world.setBlock( cx, cy + 1, cz, Block.carrot.blockID );
									topID = Block.carrot.blockID;
									if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.carrot.itemID );
									used = true; break;
								}
								if ( sdt.itemID == Item.seeds.itemID ) {
									world.setBlock( cx, cy + 1, cz, Block.crops.blockID );
									topID = Block.crops.blockID;
									if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.seeds.itemID );
									used = true; break;
								}

							}



						//空きソウルサンド
						if ( harvestLevel >= 1 && selectedID == Block.slowSand.blockID && topID == 0 )
							if ( eplayer.inventory.hasItem ( Item.netherStalkSeeds.itemID ) ) {
								world.setBlock( cx, cy + 1, cz, Block.netherStalk.blockID );
								topID = Block.netherStalk.blockID;
								if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.netherStalkSeeds.itemID );

								used = true;
							}



						//空き砂
						if ( harvestLevel >= 1 && selectedID == Block.sand.blockID && topID == 0 )
							if ( world.getBlockMaterial( cx - 1, cy, cz ) == Material.water ||
							world.getBlockMaterial( cx + 1, cy, cz ) == Material.water ||
							world.getBlockMaterial( cx, cy, cz - 1 ) == Material.water ||
							world.getBlockMaterial( cx, cy, cz + 1 ) == Material.water )

								if ( eplayer.inventory.hasItem ( Item.reed.itemID ) ) {
									world.setBlock( cx, cy + 1, cz, Block.reed.blockID );
									topID = Block.reed.blockID;
									if ( consumeSeeds ) eplayer.inventory.consumeInventoryItem( Item.reed.itemID );

									used = true;
								}


					}


				}


				if ( used )
					flag ++;
				//items.damageItem(1, eplayer);
			}


		if ( flag > 0 ) {
			//debug
			//System.out.println( "Hoe.damage : " + flag );
			items.damageItem( flag, eplayer );
			world.playSoundEffect( bx + 0.5F, by + 0.5F, bz + 0.5F, Block.dirt.stepSound.getStepSound(), ( Block.dirt.stepSound.getVolume() + 1.0F ) / 2.0F, Block.dirt.stepSound.getPitch() * 0.8F );
			eplayer.swingItem();

			return true;
		} else return false;

	}


}
