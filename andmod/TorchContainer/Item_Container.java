package andmod.TorchContainer;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class Item_Container extends Item implements IRecipe {

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
		
		int bid = world.getBlockId( bx, by, bz);
		
		if ( bid == Block.snow.blockID && ( world.getBlockMetadata( bx, by, bz ) & 7 ) < 1 )
			side = 1;
		else if ( bid != Block.vine.blockID && bid != Block.tallGrass.blockID && bid != Block.deadBush.blockID && 
				( Block.blocksList[bid] == null || !Block.blocksList[bid].isBlockReplaceable( world, bx, by, bz ) ) )
			switch( side ) {
			case 0:
				by --; break;
			case 1:
				by ++; break;
			case 2:
				bz --; break;
			case 3:
				bz ++; break;
			case 4:
				bx --; break;
			case 5:
				bx ++; break;
			}
		
		if ( items.stackSize <= 0 || items.getItemDamage() >= items.getMaxDamage() || !eplayer.canPlayerEdit( bx, by, bz, side, contents ) || 
				( by == 255 && Block.blocksList[contents.itemID].blockMaterial.isSolid() ) ||
				!world.canPlaceEntityOnSide( contents.itemID, bx, by, bz, false, side, eplayer, contents ) )
			return false;
		
		Block block = Block.blocksList[contents.itemID];
		int meta = block.onBlockPlaced( world, bx, by, bz, side, px, py, pz, contents.getItem().getMetadata( contents.getItemDamage() ) );
		
		if ( placeBlockAt( contents, eplayer, world, bx, by, bz, side, meta ) ) {
			world.playSoundEffect( bx + 0.5, by + 0.5, bz + 0.5, block.stepSound.getPlaceSound(), ( block.stepSound.getVolume() + 1.0F ) / 2.0F, block.stepSound.getPitch() * 0.8F );
            items.damageItem( 1, eplayer );
            
            int charge = chargeItem( items, eplayer );
    		if ( charge != items.getItemDamage() ) {
    			items.setItemDamage( charge );
    			world.playSoundAtEntity( eplayer, "random.pop", 0.2F, ( ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.7F + 1.0F ) * 2.0F );
    		}
		}
		
		return true;
		
	}


	
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		int charge = chargeItem( items, eplayer );
		if ( charge != items.getItemDamage() ) {
			items.setItemDamage( charge );
			world.playSoundAtEntity( eplayer, "random.pop", 0.2F, ( ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.7F + 1.0F ) * 2.0F );
			eplayer.swingItem();
		}
		return items;
	}





	/**
	 * アイテムを吸収して、耐久値を回復します。
	 * @param items		アイテム。
	 * @param eplayer	プレイヤー。
	 * @return			計算された耐久値を返します。
	 */
	private int chargeItem( ItemStack items, EntityPlayer eplayer ) {
		
		int ret = items.getItemDamage();
		while ( eplayer.inventory.hasItem( contents.itemID ) && ret > 0 ) {
			eplayer.inventory.consumeInventoryItem( contents.itemID );
			ret --;
		}
		
		return ret;
	}
	

	private boolean placeBlockAt( ItemStack items, EntityPlayer eplayer, World world, int x, int y, int z, int side, int meta ) {

		if ( !world.setBlock( x, y, z, contents.itemID, meta, 3 ) )
			return false;

		if ( world.getBlockId( x, y, z ) == contents.itemID ) {
			Block.blocksList[contents.itemID].onBlockPlacedBy( world, x, y, z, eplayer, items );
			Block.blocksList[contents.itemID].onPostBlockPlaced( world, x, y, z, meta );
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
