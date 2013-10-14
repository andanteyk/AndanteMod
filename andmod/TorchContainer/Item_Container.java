package andmod.TorchContainer;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.ICraftingHandler;

public class Item_Container extends Item implements IRecipe, ICraftingHandler {

	private ItemStack contents = new ItemStack( 0, 1, 0 );
	private ItemStack material;
	private int increment;
	private boolean extending;
	

	public Item_Container( int itemID, int durability, ItemStack contents ) {
		super( itemID );

		setMaxStackSize( 1 );
		setNoRepair();
		setMaxDamage( durability );
		this.contents = contents;
		
		material = new ItemStack( Item.leather );
		increment = 64;
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
		return !extending;
	}
	
	
	@Override
	public ItemStack getContainerItemStack( ItemStack items ) {
		if ( items != null && items.itemID == itemID && !extending )
			items.setItemDamage( items.getItemDamage() + 1 );

		return items;	//checkme
	}


	@Override
	public boolean matches( InventoryCrafting inv, World world ) {
		
		ItemStack items = null;
		boolean mtr = false;
		
		for ( int i = 0; i < inv.getSizeInventory(); i ++ ) {
			ItemStack itemt = inv.getStackInSlot( i );
			
			if ( itemt != null ) {
				if ( itemt.itemID == itemID && items == null ) 
					items = itemt;
				else if ( itemt.itemID == material.itemID )
					mtr = true;
				else return false;
			}
		}
		
		
		if ( items != null ) {
			return mtr || items.getItemDamage() < items.getMaxDamage();
		}
		
		return false;
	}

	
	@Override
	public ItemStack getCraftingResult( InventoryCrafting inv ) {
		//return contents.copy();
		int materialCount = 0;
		ItemStack items = null;
		
		
		{	/* 苦肉の策です。
			 * 詳細はQuiverを参照してください。
			 */
			StackTraceElement stack[] = ( new Throwable() ).getStackTrace();
			if ( stack.length >= 4 && ( stack[3].getMethodName().equals( "decrStackSize" ) || stack[3].getMethodName().equals( "func_70298_a" ) ) ) {
				return null;
			}
			
			/*//for de-bug
			for ( int i = 0; i < stack.length; i ++ )
				System.out.println( stack[i].getMethodName() );
			*/
		}
		
		
		for ( int i = 0; i < inv.getSizeInventory(); i ++ ) {
			ItemStack itemt = inv.getStackInSlot( i );
			if ( itemt == null ) continue;
			
			if ( itemt.itemID == itemID )
				items = itemt.copy();
			else if ( itemt.itemID == material.itemID )
				materialCount ++;
			
		}
		
		
		if ( items == null ) return null;
		if ( materialCount == 0 ) {
			//release
			extending = false;
			if ( items.getItemDamage() < items.getMaxDamage() ) {
				return contents.copy();
			}
			
		} else {
			//extend
			extending = true;
			setMaxDamage( items, items.getMaxDamage() + materialCount * increment );
			items.setItemDamage( items.getItemDamage() + materialCount * increment );
			return items;
		}
		
		
		return null;	
	}


	@Override
	public int getRecipeSize() {
		return 9;
	}


	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}


	
	
	private void initNBT( ItemStack items ) {
		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			nbt = new NBTTagCompound();
			items.setTagCompound( nbt );
		}
		
		nbt.setInteger( "maxStack", getMaxDamage() );
	}
	
	
	@Override
	public int getMaxDamage( ItemStack items ) {
		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			initNBT( items );
			nbt = items.getTagCompound();
		}
		
		return nbt.getInteger( "maxStack" );
	}

	
	public void setMaxDamage( ItemStack items, int max ) {
		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			initNBT( items );
			nbt = items.getTagCompound();
		}
		
		nbt.setInteger( "maxStack", max );
	}



	@Override
	public void onCrafting( EntityPlayer eplayer, ItemStack items, IInventory inv ) {
		for ( int i = 0; i < inv.getSizeInventory(); i ++ ) {
			ItemStack itemt = inv.getStackInSlot( i );
			if ( itemt != null && itemt.itemID == material.itemID ) {
				extending = true;
				return;
			}
		}
		
		extending = false;
	}


	@Override
	public void onSmelting( EntityPlayer eplayer, ItemStack items ) {}
	
	
}
