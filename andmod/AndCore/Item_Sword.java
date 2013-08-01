package andmod.AndCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Item_Sword extends ItemSword {

	protected float weaponDamage;
	protected int itemDamageVsEntity;
	protected int itemDamageVsBlock;
	protected int enchantability;
	protected float efficiencyOnProperMaterial;


	/**
	 * 新しい剣を定義します。
	 * @param itemID	アイテムのID。
	 * @param material	アイテムの素材。
	 */
	public Item_Sword( int itemID, EnumToolMaterial material ) {
		super( itemID, material );

		weaponDamage = material.getDamageVsEntity() + 4.0F;
		itemDamageVsEntity = 1;
		itemDamageVsBlock = 2;
		enchantability = material.getEnchantability();
		efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
	}


	/**
	 * 新しい剣を定義します。
	 * @param itemID			アイテムのID。
	 * @param material			アイテムの素材。
	 * @param durability		耐久値。
	 * @param power				攻撃力。
	 * @param enchantability	エンチャント適性。
	 */
	public Item_Sword( int itemID, EnumToolMaterial material, int durability, float efficiency, float power, int enchantability ) {
		super( itemID, material );

		itemDamageVsEntity = 1;
		itemDamageVsBlock = 2;

		setMaxDamage( durability );
		efficiencyOnProperMaterial = efficiency;
		weaponDamage = power;
		this.enchantability = enchantability;

	}


	/**
	 * 生物に対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Sword setItemDamageVsEntity( int damage ) {
		itemDamageVsEntity = damage;
		return this;
	}


	/**
	 * ブロックに対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Sword setItemDamageVsBlock( int damage ) {
		itemDamageVsBlock = damage;
		return this;
	}


	@Override
	public Multimap func_111205_h() {
		Multimap multimap = HashMultimap.create();	//from Item.func_111205_h()
		multimap.put( SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier( field_111210_e, "Weapon modifier", weaponDamage, 0 ) );
		return multimap;
	}


	@Override
	public float func_82803_g() {
		return weaponDamage - 4.0F;
	}



	@Override
	public boolean hitEntity( ItemStack items, EntityLivingBase defender, EntityLivingBase attacker ) {
		items.damageItem( itemDamageVsEntity, attacker );
		return true;
	}


	@Override
	public float getStrVsBlock( ItemStack items, Block block ) {

		if ( block.blockMaterial == Material.web )
			//return 15.0F;
			return efficiencyOnProperMaterial * 3.0F;
		else if ( block.blockMaterial == Material.plants || block.blockMaterial == Material.vine || block.blockMaterial == Material.coral ||
				block.blockMaterial == Material.leaves || block.blockMaterial == Material.pumpkin )
			//return 1.5F;
			return Math.max( efficiencyOnProperMaterial / 3.0F, 1.0F );
		else return 1.0F;
	}


	@Override
	public boolean onBlockDestroyed( ItemStack items, World world, int id, int bx, int by, int bz, EntityLivingBase eliv ) {

		if ( Block.blocksList[id].blockMaterial == Material.web )
			items.damageItem( (int)Math.ceil( itemDamageVsBlock / 2.0 ), eliv );
		else if ( Block.blocksList[id].getBlockHardness( world, bx, by, bz ) != 0.0 )
			items.damageItem( itemDamageVsBlock, eliv );

		return true;
	}


	@Override
	public boolean canHarvestBlock( Block block ) {
		return block.blockMaterial == Material.web;
	}


	@Override
	public int getItemEnchantability() {
		return enchantability;
	}


}
