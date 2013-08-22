package andmod.Quiver;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class Item_Quiver extends Item implements IRecipe {
	
	private ItemStack contents;

	public Item_Quiver( int itemID, int durability, ItemStack contents ) {
		super( itemID );
		this.contents = contents;
		
		setMaxStackSize( 1 );
		setMaxDamage( durability );
		setNoRepair();
	}

	
	@Override
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4) {
		list.add( ( items.getMaxDamage() - items.getItemDamage() ) + " / " + items.getMaxDamage() );
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
	
	
	
	//以降クラフティング関連
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
		
		boolean flag = false;
		
		for ( int i = 0; i < inv.getSizeInventory(); i ++ ) {
			ItemStack itemt = inv.getStackInSlot( i );
			
			if ( itemt != null ) {
				if ( itemt.itemID == itemID && itemt.getItemDamage() < itemt.getMaxDamage() && !flag ) flag = true;
				else return false;
			}
		}
		
		return flag;
	}


	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
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
