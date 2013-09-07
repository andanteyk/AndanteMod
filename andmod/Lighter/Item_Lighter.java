package andmod.Lighter;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import andmod.AndCore.AndMod_AndCore;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class Item_Lighter extends Item implements IFuelHandler {

	protected int burntick;
	protected ItemStack container;
	protected String[] info = new String[2];
	protected boolean canIgnite;
	
	/**
	 * 新しいライターを定義します。
	 * @param itemID		アイテムのID。
	 * @param durability	耐久値。
	 * @param burntick		耐久値1当たりの燃焼時間。
	 * @param canIgnite		着火可能か。不可能にすれば燃料のように使えます。
	 * @param container		入れ物となるアイテム。
	 */
	public Item_Lighter( int itemID, int durability, int burntick, boolean canIgnite, ItemStack container ) {
		super( itemID );
		
		setMaxStackSize( 1 );
		setMaxDamage( durability );
		this.burntick = burntick;
		if ( burntick > 0 ) GameRegistry.registerFuelHandler( this );
		this.container = container;
		this.canIgnite = canIgnite;
	}
	

	/**
	 * 説明文を追加します。
	 * @param english	英語の説明文。
	 * @param japanese	日本語の説明文。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Lighter addInformation( String english, String japanese ) {
		info[0] = english;
		info[1] = japanese;
		return this;
	}

	
	@Override
	public int getBurnTime( ItemStack fuel ) {
		if ( fuel.itemID == itemID )
			return burntick;
		else return 0;
	}

	
	@Override
	public ItemStack getContainerItemStack( ItemStack items ) {
		if ( items.getItemDamage() < items.getMaxDamage() - 1 ) {
			items.setItemDamage( items.getItemDamage() + 1 );
			items.stackSize = 1;
			return items;
		} else
			return container;
	}
	

	@Override
	public boolean isFull3D() {
		return canIgnite;
	}

	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return canIgnite;
	}

	
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {
		// System.out.println("onItemRightClick called");

		if ( canIgnite ) {

			MovingObjectPosition var12 = getMovingObjectPositionFromPlayer( world, eplayer, true );
			if ( var12 == null )
				return items;
			if ( var12.typeOfHit == EnumMovingObjectType.TILE ) {
				int px = var12.blockX;
				int py = var12.blockY;
				int pz = var12.blockZ;

				if ( !world.canMineBlock( eplayer, px, py, pz ) ) return items;
				if ( !eplayer.canPlayerEdit( px, py, pz, var12.sideHit, items ) ) return items;
				if ( world.isRemote ) return items;

				if ( world.getBlockId( px, py, pz ) == Block.tnt.blockID ) {
					
					EntityTNTPrimed entitytntprimed = new EntityTNTPrimed( world, px + 0.5F, py + 0.5F, pz + 0.5F, eplayer );
					world.spawnEntityInWorld( entitytntprimed );
					world.playSoundAtEntity( entitytntprimed, "random.fuse", 1.0F, 1.0F );
					world.setBlockToAir( px, py, pz );

					eplayer.swingItem();
					return decreaseDurability ( items, eplayer ) ;
				}


				switch ( var12.sideHit ) {
				case 0:
					py--; break;
				case 1:
					py++; break;
				case 2:
					pz--; break;
				case 3:
					pz++; break;
				case 4:
					px--; break;
				case 5:
					px++; break;
				}

				if ( world.getBlockId( px, py, pz ) == 0 ) {
					world.playSoundEffect( px + 0.5D, py + 0.5D, pz + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );

					world.setBlock( px, py, pz, Block.fire.blockID );
					eplayer.swingItem();
				}

				return decreaseDurability( items, eplayer ) ;
			}
		}

		return items;
	}
	
	
	protected ItemStack decreaseDurability( ItemStack items, EntityPlayer eplayer ) {
		if ( items.getItemDamage() >= items.getMaxDamage() - 1 )
			return container;
		else
			items.damageItem( 1, eplayer );
		return items;
	}
	
	
	@Override
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4 ) {
		
		if ( AndMod_AndCore.isJapanese() && !info[1].equals( "" ) ) list.add( info[1] );
		else if ( !info[0].equals( "" ) ) list.add( info[0] );
		
	}
	
	
}
