package andmod.AndCore;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Item_Bow extends ItemBow {

	@SideOnly(Side.CLIENT)
	protected Icon[] iconPulling;

	protected int enchantability;
	protected int bowCraftingMaterialID;

	protected int itemDamageOnUse;

	protected double chargeSpeed;
	protected double velocity;
	protected double power;
	protected double knockback;

	/** 特殊効果(ポーション効果の付加など)を渡すために利用されます。そのため、内部のvelocityなどは利用されないので注意してください。 */
	public Struct_Arrow property;

	/**
	 * 新しい弓を定義します。
	 * @param itemID			アイテムのID。
	 * @param durability		耐久値。
	 * @param enchantability	エンチャント適性。
	 */
	public Item_Bow( int itemID, int durability, int enchantability ) {
		super( itemID );

		setMaxDamage( durability );
		this.enchantability = enchantability;

		bowCraftingMaterialID = 0;
		itemDamageOnUse = 1;
		chargeSpeed = 1.0;
		velocity = 1.0;
		power = 2.0;
		knockback = 0.0;

		property = new Struct_Arrow();
	}


	/**
	 * 新しい弓を定義します。
	 * @param itemID	アイテムのID。
	 * @param material	素材。
	 */
	public Item_Bow( int itemID, EnumToolMaterial material ) {
		super( itemID );

		setMaxDamage( material.getMaxUses() );
		enchantability = material.getEnchantability();

		//fixme: 何故だか実行するとエラーを起こすので、初期化できません
		//bowCraftingMaterialID = material.getToolCraftingMaterial();
		bowCraftingMaterialID = 0;
		itemDamageOnUse = 1;
		chargeSpeed = 1.0F;
		velocity = 1.0F;
		power = 2.0F;
		knockback = 0.0F;

		property = new Struct_Arrow();
	}


	/**
	 * 弓の能力を設定します。
	 * @param chargeSpeed	弓を引き絞るのにかかる時間。既定値1.0、大きければ速くなります。
	 * @param velocity		矢の速度係数。既定値1.0。
	 * @param power			矢の威力。既定値2.0。
	 * @param knockback		ノックバック力。既定値0.0。
	 * @return				設定されたアイテムを返します。
	 */
	public Item_Bow setParameters( double chargeSpeed, double velocity, double power, double knockback ) {
		this.chargeSpeed = chargeSpeed;
		this.velocity = velocity;
		this.power = power;
		this.knockback = knockback;
		return this;
	}


	/**
	 * この弓から撃ち出した矢に特殊効果を与えます。
	 * @param effectID		効果ID。
	 * @param amplifier		強化レベル。
	 * @param duration		効果時間。
	 * @param probability	発生確率。0.0～1.0の間で指定します。
	 * @return				設定されたアイテムを返します。
	 */
	public Item_Bow addEffect( int effectID, int amplifier, int duration, float probability ) {

		property.EffectIDList.add( effectID );
		property.AmplifierList.add( amplifier );
		property.DurationList.add( duration );
		property.ProbabilityList.add( probability );
		return this;
	}


	/**
	 * 弓を使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Bow setItemDamageOnUse( int damage ) {
		itemDamageOnUse = damage;
		return this;
	}

	
	/**
	 * 金床で修理するときに必要になる素材を設定します。
	 * @param itemID	素材のID。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Bow setCraftingMaterial( int itemID ) {
		bowCraftingMaterialID = itemID;
		return this;
	}

	
	@Override
	public void onPlayerStoppedUsing( ItemStack items, World world, EntityPlayer eplayer, int remain ) {
		int chargeTime = getMaxItemUseDuration( items ) - remain;

		ArrowLooseEvent event = new ArrowLooseEvent( eplayer, items, chargeTime );
		MinecraftForge.EVENT_BUS.post( event );
		if ( event.isCanceled() ) return;
		
		
		chargeTime = event.charge;


		boolean isInfinite = eplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel( Enchantment.infinity.effectId, items ) > 0;

		if ( isInfinite || eplayer.inventory.hasItem( Item.arrow.itemID ) ) {

			float strength = chargeTime / 20.0F;
			strength = ( strength * strength + strength * 2.0F ) / 3.0F;

			if ( strength < 0.1D ) return;

			System.out.println( "Item_Bow : *** Unexpected calling!! *** " );	//ここから下は理論上呼ばれません。


			if ( strength > 1.0F ) strength = 1.0F;


			EntityArrow earrow = new EntityArrow( world, eplayer, (float)( strength * 2.0 * velocity ) );
			earrow.setDamage( earrow.getDamage() + power );
			earrow.setIsCritical( strength == 1.0 );


			//enchantment
			{
				int lv = EnchantmentHelper.getEnchantmentLevel( Enchantment.power.effectId, items );
				if ( lv > 0 )
					earrow.setDamage( earrow.getDamage() + lv * 0.5D + 0.5D);

				lv = EnchantmentHelper.getEnchantmentLevel( Enchantment.punch.effectId, items );
				if( lv > 0 )
					earrow.setKnockbackStrength( MathHelper.ceiling_double_int( knockback + lv ) );
				else
					earrow.setKnockbackStrength( MathHelper.ceiling_double_int( knockback ) );

				if ( EnchantmentHelper.getEnchantmentLevel( Enchantment.flame.effectId, items ) > 0 )
					earrow.setFire( 100 );

			}


			items.damageItem( itemDamageOnUse, eplayer );
			world.playSoundAtEntity( eplayer, "random.bow", 1.0F, 1.0F / ( itemRand.nextFloat() * 0.4F + 1.2F ) + strength * 0.5F );


			if ( isInfinite )
				earrow.canBePickedUp = 2;
			else
				eplayer.inventory.consumeInventoryItem( Item.arrow.itemID );


			if ( !world.isRemote ) world.spawnEntityInWorld( earrow );
		}
	}


	@Override
	public int getMaxItemUseDuration( ItemStack par1ItemStack ) {
		return (int)( 72000.0 / chargeSpeed );
	}


	@Override
	public int getItemEnchantability() {
		return enchantability;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IconRegister ireg ) {
		itemIcon = ireg.registerIcon( func_111208_A() );
		iconPulling = new Icon[3];

		for ( int i = 0; i < iconPulling.length; i ++ )
			iconPulling[i] = ireg.registerIcon( func_111208_A() + "_" + i );

	}


	@Override
	public Icon getIcon( ItemStack items, int renderPass, EntityPlayer eplayer, ItemStack usingItem, int remainTime ) {
		if ( usingItem != null && usingItem.itemID == itemID ) {
			int r = usingItem.getMaxItemUseDuration() - remainTime;
			if ( r >= 18.0F / chargeSpeed ) return iconPulling[2];
			if ( r >  13.0F / chargeSpeed ) return iconPulling[1];
			if ( r >   0.0F               ) return iconPulling[0];
		}

		return super.getIcon( items, renderPass, eplayer, usingItem, remainTime );
	}


	@Override
	@SideOnly(Side.CLIENT)
	public Icon getItemIconForUseDuration( int i ) {
		return iconPulling[i];
	}



	@Override
	public boolean getIsRepairable( ItemStack bow, ItemStack material ) {
		return bowCraftingMaterialID == material.itemID ? true : super.getIsRepairable( bow, material );
	}

}
