package andmod.AndCore;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Item_Spade extends ItemSpade {

	protected int itemDamageVsEntity;
	protected int itemDamageVsBlock;
	protected int enchantability;


	public Item_Spade( int itemID, EnumToolMaterial material ) {
		super( itemID, material );

		itemDamageVsEntity = 2;
		itemDamageVsBlock = 1;

		enchantability = material.getEnchantability();
	}

	public Item_Spade( int itemID, EnumToolMaterial material, int durability, float power, float efficiency, int enchantability ) {
		super( itemID, material );

		itemDamageVsEntity = 2;
		itemDamageVsBlock = 1;

		setMaxDamage( durability );
		damageVsEntity = power;
		efficiencyOnProperMaterial = efficiency;
		this.enchantability = enchantability;
	}


	/**
	 * 生物に対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Spade setItemDamageVsEntity( int damage ) {
		itemDamageVsEntity = damage;
		return this;
	}


	/**
	 * ブロックに対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Spade setItemDamageVsBlock( int damage ) {
		itemDamageVsBlock = damage;
		return this;
	}


	@Override
	public int getItemEnchantability() {
		return enchantability;
	}


	@Override
	public boolean hitEntity( ItemStack items, EntityLivingBase defender, EntityLivingBase attacker ) {
		items.damageItem( itemDamageVsEntity, attacker );
		return true;
	}


	@Override
	public boolean onBlockDestroyed( ItemStack items, World world, int id, int bx, int by, int bz, EntityLivingBase eliv ) {

		if ( Block.blocksList[id].getBlockHardness( world, bx, by, bz ) != 0.0 )
			items.damageItem( itemDamageVsBlock, eliv );

		return true;
	}



}
