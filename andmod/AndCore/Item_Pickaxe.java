package andmod.AndCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Item_Pickaxe extends ItemPickaxe {

	protected int itemDamageVsEntity;
	protected int itemDamageVsBlock;
	protected int enchantability;


	public Item_Pickaxe( int itemID, EnumToolMaterial material ) {
		super( itemID, material );

		itemDamageVsEntity = 2;
		itemDamageVsBlock = 1;

		enchantability = material.getEnchantability();
	}


	public Item_Pickaxe( int itemID, EnumToolMaterial material, int durability, float power, float efficiency, int enchantability ) {
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
	public Item_Pickaxe setItemDamageVsEntity( int damage ) {
		itemDamageVsEntity = damage;
		return this;
	}


	/**
	 * ブロックに対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Pickaxe setItemDamageVsBlock( int damage ) {
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


	@Override
	public boolean canHarvestBlock( Block block ) {

		if ( block == Block.obsidian )
			return toolMaterial.getHarvestLevel() >= 3;

			else if (	block == Block.oreDiamond || block == Block.blockDiamond ||
					block == Block.oreEmerald || block == Block.blockEmerald ||
					block == Block.oreGold || block == Block.blockGold||
					block == Block.oreRedstone || block == Block.oreRedstoneGlowing )
				return toolMaterial.getHarvestLevel() >= 2;

				else if (	block == Block.oreIron || block == Block.blockIron ||
						block == Block.oreLapis || block == Block.blockLapis )
					return toolMaterial.getHarvestLevel() >= 1;

					else if ( 	block.blockMaterial == Material.rock ||
							block.blockMaterial == Material.iron ||
							block.blockMaterial == Material.anvil )
						return true;

					else return false;

	}


}
