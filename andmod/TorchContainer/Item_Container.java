package andmod.TorchContainer;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class Item_Container extends Item implements IRecipe {


	private float fx, fy, fz;
	private ItemStack contents = new ItemStack( 0, 1, 0 );


	public Item_Container( int itemID, int durability, ItemStack contents ) {
		super( itemID );

		setMaxStackSize( 1 );
		setNoRepair();
		setMaxDamage( durability );
		this.contents = contents;
	}



	//p*は　ブロックのどこをクリックしたか（ブロックの上辺をクリックすればpy=1.0）
	@Override
	public boolean onItemUse( ItemStack items, EntityPlayer eplayer, World world, int bx, int by, int bz, int side, float px, float py, float pz ) {
		fx = px; fy = py; fz = pz;
		return false;
	}


	
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		eplayer.swingItem();

		MovingObjectPosition mop = getMovingObjectPositionFromPlayer( world, eplayer, true );
		if ( mop == null ) {

			if ( ! eplayer.capabilities.isCreativeMode ) {
				if ( items.getItemDamage() > 0 && eplayer.inventory.hasItem( contents.itemID ) ) {
					world.playSoundAtEntity( eplayer, "random.pop", 0.2F, ( ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.7F + 1.0F ) * 2.0F );
					return chargeItem ( items, eplayer );
				}
			}
			
			return items;
		}

		if ( mop.typeOfHit == EnumMovingObjectType.TILE ) {

			int bx = mop.blockX;
			int by = mop.blockY;
			int bz = mop.blockZ;
			int side = mop.sideHit;


			int bid = world.getBlockId( bx, by, bz );

			if ( bid == Block.snow.blockID )
				side = 1;
			else if ( bid != Block.vine.blockID && bid != Block.tallGrass.blockID && bid != Block.deadBush.blockID
					&& ( Block.blocksList[bid] == null || !Block.blocksList[bid].isBlockReplaceable( world, bx, by, bz ) ) )
				switch ( side ) {
				case 0:
					by--;
					break;
				case 1:
					by++;
					break;
				case 2:
					bz--;
					break;
				case 3:
					bz++;
					break;
				case 4:
					bx--;
					break;
				case 5:
					bx++;
					break;
				}




			if ( items.stackSize == 0 || ! eplayer.canPlayerEdit( bx, by, bz, side, items ) ||
				( by == 255 && Block.blocksList[contents.itemID].blockMaterial.isSolid() ) )
				return items;

			else if ( items.getItemDamage() < items.getMaxDamage() && world.canPlaceEntityOnSide( contents.itemID, bx, by, bz, false, side, eplayer, items ) ) {

				Block block = Block.blocksList[contents.itemID];
				int meta = getMetadata( contents.getItemDamage() );
				int var14 = block.onBlockPlaced( world, bx, by, bz, side, fx, fy, fz, meta );

				if ( placeBlockAt( items, eplayer, world, bx, by, bz, side, var14 ) ) {
					world.playSoundEffect( bx + 0.5F, by + 0.5F, bz + 0.5F, block.stepSound.getPlaceSound(), ( block.stepSound.getVolume() + 1.0F ) / 2.0F, block.stepSound.getPitch() * 0.8F );
					items.damageItem( 1, eplayer );
				}


				if ( items.getItemDamage() > 0 && eplayer.inventory.hasItem( contents.itemID ) )
					items = chargeItem( items, eplayer );


				return items;

			} else if ( items.getItemDamage() >= items.getMaxDamage() || ( items.getItemDamage() > 0 && eplayer.inventory.hasItem( contents.itemID ) ) )
				return chargeItem ( items, eplayer );
			else return items;

		} else return items;
	}





	/**
	 * アイテムを吸収して、耐久値を回復します。
	 * @param items		アイテム。
	 * @param eplayer	プレイヤー。
	 * @return			処理したアイテムを返します。
	 */
	private ItemStack chargeItem ( ItemStack items, EntityPlayer eplayer ) {

		while ( eplayer.inventory.hasItem( contents.itemID ) && items.getItemDamage() > 0 ) {
			eplayer.inventory.consumeInventoryItem( contents.itemID );
			items.setItemDamage( items.getItemDamage() - 1 );
		}

		return items;
	}



	/**
	 * アイテムを排出します。
	 * @param items		アイテム。
	 * @param eplayer	プレイヤー。
	 * @return			処理したアイテムを返します。
	 */
	@Deprecated
	public ItemStack dischargeItem ( ItemStack items, EntityPlayer eplayer ) {
		while ( items.getItemDamage() < items.getMaxDamage() ) {

			int dmg = items.getMaxStackSize();
			if ( items.getItemDamage() + dmg > items.getMaxDamage() ) dmg = items.getMaxDamage() - items.getItemDamage();


			if ( !eplayer.inventory.addItemStackToInventory( new ItemStack( contents.itemID, dmg, contents.getItemDamage() ) ) ) eplayer.dropPlayerItem( new ItemStack( contents.itemID, dmg, contents.getItemDamage() ) );
			items.setItemDamage( items.getItemDamage() + dmg );

		}
		return items;
	}




	private boolean placeBlockAt( ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, int metadata ) {

		if ( !world.setBlock( x, y, z, contents.itemID, metadata, 3 ) )
			return false;

		if ( world.getBlockId( x, y, z ) == contents.itemID ) {
			Block.blocksList[contents.itemID].onBlockPlacedBy( world, x, y, z, player, stack );
			Block.blocksList[contents.itemID].onPostBlockPlaced( world, x, y, z, metadata );
		}

		return true;
	}


	@Override
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4) {
		list.add( ( items.getMaxDamage() - items.getItemDamage() ) + " / " + items.getMaxDamage() );
	}



	@Override
	public boolean doesContainerItemLeaveCraftingGrid( ItemStack items ) {
		return false;
	}
	
	
	@Override
	public boolean hasContainerItem() {
		return true;
	}
	
	
	@Override
	public ItemStack getContainerItemStack( ItemStack items ) {
		if ( items != null && items.itemID == itemID )
			items.setItemDamage( items.getItemDamage() + 1 );

		return items;
	}


	@Override
	public boolean matches( InventoryCrafting inv, World world ) {
		
		ItemStack items = null;
		
		for ( int i = 0; i < inv.getSizeInventory(); i ++ ) {
			ItemStack itemt = inv.getStackInSlot( i );
			
			if ( itemt != null ) {
				if ( itemt.itemID == itemID && items == null ) 
					items = itemt;
				else return false;
			}
		}
		
		
		if ( items != null ) {
			return items.getItemDamage() < items.getMaxDamage();
		}
		
		return false;
	}

	
	@Override
	public ItemStack getCraftingResult( InventoryCrafting inv ) {
		return contents.copy();
	}


	@Override
	public int getRecipeSize() {
		return 1;
	}


	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	
}
