package andmod.Quiver;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import andmod.AndCore.Event_Arrow;
import andmod.AndCore.IArrow;
import cpw.mods.fml.common.ICraftingHandler;

public class Item_Quiver extends Item implements IRecipe, ICraftingHandler, IArrow {
	
	private ItemStack material;
	private int increment;
	private boolean extending;

	public Item_Quiver( int itemID, int durability ) {
		super( itemID );
		
		setMaxStackSize( 1 );
		setMaxDamage( durability );
		setNoRepair();
		
		material = new ItemStack( Item.leather );
		increment = 64;
	}

	
	@Override
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4 ) {
		if ( items.getItemDamage() < items.getMaxDamage() )
			list.add( Item.itemsList[ getArrowID( items ) ].getItemDisplayName( items ) );
		else 
			list.add( "Empty" );
		
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
		
		
		if ( !items.hasTagCompound() )
			initNBT( items );
		
		int ret = items.getItemDamage();
		int contentsID = 0;
		if ( ret >= items.getMaxDamage() ) {
			//empty
			
			for ( int i = 0; i < eplayer.inventory.getSizeInventory() - 4; i ++ ) {
				ItemStack itemt = eplayer.inventory.getStackInSlot( i );
				if ( itemt == null ) continue;
				
				for ( int j = 0; j < Event_Arrow.IDList.size(); j ++ ) {
					if ( itemt.itemID == Event_Arrow.IDList.get( j ) ) {
				
						if ( itemt.getItem() instanceof IArrow ) {
							if ( !( itemt.getItem() instanceof Item_Quiver ) ) {
								if ( ( (IArrow)itemt.getItem() ).canConsumeArrow( itemt ) ) {
									contentsID = ( (IArrow)itemt.getItem() ).getArrowID( itemt );
									break;
								}
							}
							
						} else {
							if ( itemt.stackSize > 0 ) {
								contentsID = itemt.itemID;
								break;
							}
						}
					}
				}
				
				if ( contentsID > 0 ) break;
			}
			
			
			if ( contentsID > 0 ) setArrowIDtoNBT( items, contentsID );
			
		} else contentsID = getArrowIDfromNBT( items );
		
		if ( contentsID <= 0 ) return ret;
		
		
		
		for ( int i = 0; i < eplayer.inventory.getSizeInventory() - 4 && ret > 0; i ++ ) {
			ItemStack itemt = eplayer.inventory.getStackInSlot( i );
			if ( itemt == null ) continue;
			
			if ( itemt.getItem() instanceof IArrow ) {
				if ( itemt.getItem() instanceof Item_Quiver )
					continue;
				
				IArrow arrow = (IArrow)itemt.getItem();
				if ( contentsID == arrow.getArrowID( itemt ) ) { 
					
					while ( arrow.canConsumeArrow( itemt ) && ret > 0 ) {
						itemt = arrow.consumeArrow( itemt );
						ret --;
					}
					
					eplayer.inventory.setInventorySlotContents( i, itemt );
				}
		
			} else {
				if ( contentsID == itemt.itemID ) {
					while ( itemt.stackSize > 0 && ret > 0 ) {
						itemt.stackSize --;
						ret --;
					}
					
					if ( itemt.stackSize <= 0 )
						eplayer.inventory.setInventorySlotContents( i, null );
					
				}
			}
		}
		
		return ret;
	}
	
	
	void initNBT( ItemStack items ) {
		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			nbt = new NBTTagCompound();
			items.setTagCompound( nbt );
		}
		
		nbt.setShort( "arrowID", (short)Item.arrow.itemID );
		nbt.setInteger( "maxStack", getMaxDamage() );
	}
	
	
	int getArrowIDfromNBT( ItemStack items ) {
		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			initNBT( items );
			nbt = items.getTagCompound();
		}
		
		return nbt.getShort( "arrowID" );
	}
	
	
	/**
	 * 注意：プログラマを信用しているので、問答無用で書き込みます。未初期化時には使用しないでください。
	 * @param items
	 * @param arrowID
	 */
	void setArrowIDtoNBT( ItemStack items, int arrowID ) {
		items.getTagCompound().setShort( "arrowID", (short)arrowID );
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
	
	
	
	//以降クラフティング関連
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
		
		if ( items != null && items.itemID == itemID && !extending ) {
			items.setItemDamage( items.getItemDamage() + 1 );
			return items;
		} 
		
		return null;	
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
	public void onSmelting( EntityPlayer player, ItemStack item ) {
	}
	
	

	@Override
	public boolean matches( InventoryCrafting inv, World world ) {
		
		boolean flag = false;
		
		for ( int i = 0; i < inv.getSizeInventory(); i ++ ) {
			ItemStack itemt = inv.getStackInSlot( i );
			
			if ( itemt != null ) {
				if ( itemt.itemID == itemID && !flag ) flag = true;
				else if ( itemt.itemID != material.itemID ) return false;
			}
		}
		
		return flag;
	}


	@Override
	public ItemStack getCraftingResult( InventoryCrafting inv ) {
		
		int materialCount = 0;
		ItemStack items = null;
		
		
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
			if ( canConsumeArrow( items ) ) {
				return new ItemStack( getArrowID( items ), 1, 0 );
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


	@Override
	public int getArrowID( ItemStack items ) {
		if ( items == null ) return 0;
		return getArrowIDfromNBT( items );
	}


	@Override
	public boolean canConsumeArrow( ItemStack items ) {
		if ( items == null ) return false;
		return items.getItemDamage() < items.getMaxDamage();
	}


	@Override
	public ItemStack consumeArrow( ItemStack items ) {
		if ( items == null ) return items;
		
		items.setItemDamage( items.getItemDamage() + 1 );
		return items;
	}


	

}
