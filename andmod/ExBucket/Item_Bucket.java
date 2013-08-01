package andmod.ExBucket;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class Item_Bucket extends Item implements IFuelHandler {

	private ItemStack parent;
	private ItemStack contents;
	private int burnTick;
	
	private ArrayList<ItemStack> ContentsList;
	private ArrayList<ItemStack> ContainerList;
	private ArrayList<Integer> FlagList;
	
	public static final int FLAG_COW = 0x1;
	
	
	/**
	 * 新しいバケツを定義します。
	 * @param itemID		アイテムのID。
	 * @param maxStack		最大スタック数。
	 * @param durability	内容量。
	 * @param parentItem	親バケツ。空のバケツを指定してください。ない場合はnullを指定してください。
	 * @param contentsItem	中身。
	 * @param burnTick		燃焼時間。
	 */
	public Item_Bucket( int itemID, int maxStack, int durability, ItemStack parentItem, ItemStack contentsItem, int burnTick ) {
		super( itemID );
		
		setMaxStackSize( maxStack );
		setMaxDamage( durability );
		if ( parentItem != null )
			parent = parentItem;
		else parent = new ItemStack( 0, 1, 0 );
		if ( contentsItem != null )
			contents = contentsItem;
		else contents = new ItemStack( 0, 1, 0 );
		this.burnTick = burnTick;
		
		ContentsList = new ArrayList<ItemStack>();
		ContainerList = new ArrayList<ItemStack>();
		FlagList = new ArrayList<Integer>();
		
		if ( burnTick > 0 ) GameRegistry.registerFuelHandler( this );
		
		setCreativeTab( CreativeTabs.tabMisc );
	}


	@Override
	public int getBurnTime( ItemStack fuel ) {
		if ( fuel.itemID == itemID ) return burnTick;
		return 0;
	}
	
	
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		if ( contents.itemID == Item.bucketMilk.itemID ) {
			eplayer.setItemInUse( items, getMaxItemUseDuration( items ) );
			return items;
		}
		
		double shift = 1.0;
		double px = eplayer.prevPosX + ( eplayer.posX - eplayer.prevPosX ) * shift;
		double py = eplayer.prevPosY + ( eplayer.posY - eplayer.prevPosY ) * shift + 1.62D - eplayer.yOffset;
		double pz = eplayer.prevPosZ + ( eplayer.posZ - eplayer.prevPosZ ) * shift;

		
		MovingObjectPosition mopos = getMovingObjectPositionFromPlayer( world, eplayer, contents.itemID == 0 || items.getItemDamage() > 0 );

		if ( mopos == null ) {
			
			return items;
			
		} else {

			if ( mopos.typeOfHit == EnumMovingObjectType.TILE ) {
				int bx = mopos.blockX;
				int by = mopos.blockY;
				int bz = mopos.blockZ;

				if ( !world.canMineBlock( eplayer, bx, by, bz ) )
					return items;

				if ( contents.itemID == 0 ) {

					if ( !eplayer.canPlayerEdit( bx, by, bz, mopos.sideHit, items ) )
						return items;

					for ( int i = 0; i < ContentsList.size(); i++ )
						if ( world.getBlockId( bx, by, bz ) == ContentsList.get( i ).itemID && ( ContentsList.get( i ).getItemDamage() == OreDictionary.WILDCARD_VALUE ? true : world.getBlockMetadata( bx, by, bz ) == ContentsList.get(i).getItemDamage() ) ) {

							ItemStack ret = new ItemStack( ContainerList.get( i ).itemID, 1, ContainerList.get( i ).getMaxDamage() - 1 );

							world.setBlockToAir( bx, by, bz );
							if ( eplayer.capabilities.isCreativeMode )
								return items;
							if ( --items.stackSize <= 0 )
								return ret;
							if ( !eplayer.inventory.addItemStackToInventory( ret ) ) eplayer.dropPlayerItem( ret );
							return items;

						}
					

				} else if ( items.getItemDamage() > 0 ) {


					if ( !eplayer.canPlayerEdit( bx, by, bz, mopos.sideHit, items ) )
						return items;

					for ( int i = 0; i < ContentsList.size(); i++ )
						if ( world.getBlockId( bx, by, bz ) == ContentsList.get( i ).itemID && ( ContentsList.get( i ).getItemDamage() == OreDictionary.WILDCARD_VALUE ? true : world.getBlockMetadata( bx, by, bz ) == ContentsList.get( i ).getItemDamage() ) ) {

							ItemStack ret = new ItemStack( items.itemID, 1, items.getItemDamage() - 1 );

							world.setBlockToAir( bx, by, bz );
							if ( eplayer.capabilities.isCreativeMode )
								return items;
							/*
							 * if ( --itemStack.stackSize <= 0 ) { //check it
							 * return ret; } if ( !
							 * eplayer.inventory.addItemStackToInventory(ret) )
							 * eplayer.dropPlayerItem(ret);
							 */
							return ret;
						}

				}
				
				
				if ( contents.itemID != Item.bucketMilk.itemID ) { // place contents

					if ( contents.itemID < 0 )
						return new ItemStack( parent.itemID, 1, parent.getItemDamage() );

					MovingObjectPosition mopos2 = getMovingObjectPositionFromPlayer( world, eplayer, false );
					bx = mopos2.blockX;
					by = mopos2.blockY;
					bz = mopos2.blockZ;

					switch ( mopos2.sideHit ) {
					case 0:
						by--; break;
					case 1:
						by++; break;
					case 2:
						bz--; break;
					case 3:
						bz++; break;
					case 4:
						bx--; break;
					case 5:
						bx++; break;
					}

					if ( !eplayer.canPlayerEdit( bx, by, bz, mopos2.sideHit, items ) )
						return items;

					if ( tryPlaceContainedLiquid( world, px, py, pz, bx, by, bz ) && !eplayer.capabilities.isCreativeMode )
						if ( items.getItemDamage() >= items.getMaxDamage() - 1 )
							return new ItemStack( parent.itemID, 1, parent.getItemDamage() );
						else {
							items.setItemDamage( items.getItemDamage() + 1 );
							return items;
						}

				}

			}


			return items;
		}

	}
	
	
	public boolean tryPlaceContainedLiquid( World world, double px, double py, double pz, int bx, int by, int bz ) {
		if ( contents.itemID <= 0 )
			return false;
		
		else if ( !world.isAirBlock( bx, by, bz ) && world.getBlockMaterial( bx, by, bz ).isSolid() )
			return false;
		
		else if ( world.provider.isHellWorld && contents.itemID == Block.waterMoving.blockID ) {
			// here is nether
			world.playSoundEffect( px + 0.5D, py + 0.5D, pz + 0.5D,	"random.fizz", 0.5F, 2.6F + ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.8F );
			for ( int i = 0; i < 8; i ++ )
				world.spawnParticle( "largesmoke", bx + Math.random(), by + Math.random(), bz + Math.random(), 0.0D, 0.0D, 0.0D );

		} else
			world.setBlock( bx, by, bz, contents.itemID, contents.getItemDamage(), 3 );

		return true;
	}
	
	
	//itemInteractionForEntity.
	@Override
	public boolean func_111207_a( ItemStack items, EntityPlayer eplayer, EntityLivingBase eliv ) {

		for ( int i = 0; i < ContentsList.size(); i ++ )
			if ( ( FlagList.get( i ) & FLAG_COW ) != 0 && eliv instanceof EntityCow ) {
				
				if ( contents.itemID == 0 ) {
					ItemStack ret = new ItemStack( ContainerList.get( i ).itemID, items.stackSize, ContainerList.get( i ).getMaxDamage() - 1 );
					//if ( --items.stackSize <= 0 )
						eplayer.inventory.setInventorySlotContents( eplayer.inventory.currentItem, ret );
					//if ( !eplayer.inventory.addItemStackToInventory( ret ) ) eplayer.dropPlayerItem( ret );

				} else if ( ContentsList.get( i ).itemID == contents.itemID && items.getItemDamage() > 0 )
					items.setItemDamage( items.getItemDamage() - 1 );


				return true;
			}

		return false;
	}



	@Override
	public ItemStack onEaten( ItemStack items, World world, EntityPlayer eplayer ) {
		if ( contents.itemID == Item.bucketMilk.itemID ) {

			if ( ! eplayer.capabilities.isCreativeMode )
				if ( items.getItemDamage() >= items.getMaxDamage() - 1 )
					items = new ItemStack( parent.itemID, 1, parent.getItemDamage() );
				else
					items.setItemDamage( items.getItemDamage() + 1 );

			if ( ! world.isRemote )
				eplayer.curePotionEffects( new ItemStack( Item.bucketMilk, 1 ) );

		}

		return items;
	}


	@Override
	public int getMaxItemUseDuration( ItemStack items ) {
		return contents.itemID == Item.bucketMilk.itemID ? 32 : 0 ;
	}


	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return contents.itemID == Item.bucketMilk.itemID ? EnumAction.drink : EnumAction.none ;
	}


	/**
	 * バケツ使用時にどのアイテムに変化するかを追加します。
	 * @param container		バケツ。
	 * @param contents		バケツの中身。
	 * @param flag			フラグ。FLAG系定数を参照してください。
	 * @return				設定されたアイテムを返します。
	 */
	public Item_Bucket addContentsID( ItemStack container, ItemStack contents, int flag ) {
		ContainerList.add( container );
		ContentsList.add( contents );
		FlagList.add( flag );
		return this;
	}


	@Override
	public boolean hasContainerItem() {
		return parent.itemID != 0;
	}

	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid( ItemStack items ) {
		return false;
	}

	
	@Override
	public ItemStack getContainerItemStack( ItemStack items ) {
		if ( items != null && items.itemID == itemID )

			if ( items.getItemDamage() >= items.getMaxDamage() - 1 )
				if ( parent.itemID == 0 )
					return null;
				else
					items = new ItemStack( parent.itemID, 1, parent.getItemDamage() );
			else
				items.setItemDamage( items.getItemDamage() + 1 );

		return items;
	}
}
