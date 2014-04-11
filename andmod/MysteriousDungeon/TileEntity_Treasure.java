package andmod.MysteriousDungeon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntity_Treasure extends TileEntity implements IInventory {
	
	protected static final int contentsSize = 1;
	
	protected ItemStack[] contents = new ItemStack[contentsSize];
	protected String containerName;
	protected int keyID;
	
	public TileEntity_Treasure() {
	}

	@Override
	public int getSizeInventory() {
		return contentsSize;
	}

	@Override
	public ItemStack getStackInSlot( int pos ) {
		return contents[pos];
	}

	@Override
	public ItemStack decrStackSize( int pos, int num ) {
		
		if ( contents[pos] != null ) {
			
			ItemStack items;
			
			if ( contents[pos].stackSize <= num ) {
				items = contents[pos];
				contents[pos] = null;
				onInventoryChanged();
				return items;
			
			} else {
				items = contents[pos].splitStack( num );
				
				if ( contents[pos].stackSize <= 0 )
					contents[pos] = null;
				
				onInventoryChanged();
				return items;
			}
			
		} else return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing( int pos ) {
		
		if ( contents[pos] != null ) {
			ItemStack items = contents[pos];
			contents[pos] = null;
			return items;
			
		} else return null;
	}

	
	@Override
	public void setInventorySlotContents( int pos, ItemStack items ) {
		contents[pos] = items;
		
		if ( items != null && items.stackSize > getInventoryStackLimit() )
			items.stackSize = getInventoryStackLimit();
		
		onInventoryChanged();
	}

	
	@Override
	public String getInvName() {
		return isInvNameLocalized() ? containerName : "*Default Name*";
	}

	@Override
	public boolean isInvNameLocalized() {
		return containerName != null && containerName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer eplayer ) {
		return worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) != this ? false : eplayer.getDistanceSq( xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D ) <= 64.0D;
	}

	@Override
	public void openChest() {
		//nop?
	}

	@Override
	public void closeChest() {
		//nop?
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

}
