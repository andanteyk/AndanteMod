package andmod.AndCore;

import java.util.ArrayList;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

public class Item_SpecialSword extends Item_Sword {

	private ArrayList<Integer> IDList = new ArrayList<Integer>();
	private ArrayList<Integer> DurationList = new ArrayList<Integer>();
	private ArrayList<Integer> AmplifierList = new ArrayList<Integer>();
	private ArrayList<Float> ProbabilityList = new ArrayList<Float>();

	/** エンチャントされたかのような輝きを与えます。 */
	protected boolean hasFoiledEffect = false;
	
	public static final int EFFECT_IGNITION		= 256 + 0;	//着火します。
	public static final int EFFECT_EXPLOSION	= 256 + 1;	//爆破します。
	public static final int EFFECT_LIFEDRAIN	= 256 + 2;	//一定の割合で体力を吸い取ります。
	public static final int EFFECT_KNOCKBACK	= 256 + 3;	//ノックバックさせます。
	public static final int EFFECT_KILL			= 256 + 4;	//即死させます。禁呪です。
	public static final int EFFECT_USURPATION	= 256 + 5;	//装備を叩き落としてドロップさせます。
	public static final int EFFECT_PENETRATION	= 256 + 6;	//防具やのけぞりを無視したダメージを与えます。

	public static final int AMP_EXPLOSION_DESTRUCTABLE	= 1 << 30;							//爆発が地形破壊するかどうか
	public static final int AMP_EXPLOSION_UNSAFE		= AMP_EXPLOSION_DESTRUCTABLE >> 1;	//自分も爆発ダメージを受けるか
	public static final int AMP_EXPLOSION_FLAMMABLE		= AMP_EXPLOSION_DESTRUCTABLE >> 2;	//火をばら撒くか



	/**
	 * 新しい、特殊な剣を定義します。
	 * @param itemID	アイテムのID。
	 * @param material	アイテムの素材。
	 */
	public Item_SpecialSword( int itemID, EnumToolMaterial material ) {
		super( itemID, material );
	}


	/**
	 * 新しい、特殊な剣を定義します。
	 * @param itemID			アイテムのID。
	 * @param material			アイテムの素材。
	 * @param durability		耐久値。
	 * @param power				攻撃力。
	 * @param enchantability	エンチャント適性。
	 */
	public Item_SpecialSword( int itemID, EnumToolMaterial material, int durability, float efficiency, float power, int enchantability ) {
		super( itemID, material, durability, efficiency, power, enchantability );

	}


	public Item_SpecialSword addEffect( int effectID, int amplifier, int duration, float probability ) {
		IDList.add( effectID );
		AmplifierList.add( amplifier );
		DurationList.add( duration );
		ProbabilityList.add( probability );
		return this;
	}


	/**
	 * エンチャントされているかのようなエフェクトをかけるかを設定します。
	 * @param flag	設定値。
	 * @return		設定されたアイテムを返します。
	 */
	public Item_SpecialSword setHasFoiledEffect( boolean flag ) {
		hasFoiledEffect = flag;
		return this;
	}


	@Override
	public boolean hitEntity( ItemStack items, EntityLivingBase defender, EntityLivingBase attacker ) {

		if ( !attacker.worldObj.isRemote )
			for ( int i = 0; i < IDList.size(); i ++ )
				if ( itemRand.nextFloat() < ProbabilityList.get( i ) ) {

					int id = IDList.get( i );
					int amp = AmplifierList.get( i );
					int dur = DurationList.get( i );
					boolean critEffect = true;			//エンチャントされた剣で攻撃した時のエフェクトを発生させるかどうか

					if ( id < 256 )
						defender.addPotionEffect( new PotionEffect( id , dur, amp ) );

					else if ( id < 512 )
						switch ( id ) {
						case EFFECT_IGNITION:
							defender.setFire( dur );
							break;
						case EFFECT_EXPLOSION:
							defender.worldObj.newExplosion( ( ( amp & AMP_EXPLOSION_UNSAFE ) != 0 ? null : attacker ), defender.posX, defender.posY, defender.posZ, ( amp << 4 >> 4 ) / 10.0F, ( amp & AMP_EXPLOSION_FLAMMABLE ) != 0, ( amp & AMP_EXPLOSION_DESTRUCTABLE ) != 0 );
							break;
						case EFFECT_LIFEDRAIN:
							attacker.heal( MathHelper.ceiling_double_int( weaponDamage * amp / 100.0 ) );
							break;
						case EFFECT_KNOCKBACK:
							defender.addVelocity(
									-MathHelper.sin(attacker.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(attacker.rotationPitch / 180.0F * (float)Math.PI) * (double)amp / 200.0,
									amp / 1000.0F, /*-MathHelper.sin(attacker.rotationPitch / 180.0F * (float)Math.PI) * (double)amp / 100.0,*/
									MathHelper.cos(attacker.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(attacker.rotationPitch / 180.0F * (float)Math.PI) * (double)amp / 200.0 );
							break;
						case EFFECT_KILL:
							if ( !defender.isDead )
								defender.setEntityHealth( 0 );
							break;
						case EFFECT_USURPATION:
							int fg = 0, fg2 = 0;
							for ( int j = 0; j < 5; j ++ )
								if ( defender.getCurrentItemOrArmor( j ) != null )
									fg += 1 << j;

							if ( fg == 0 ) { critEffect = false; break; }

							do
								fg2 = MathHelper.floor_float( itemRand.nextFloat() * 5.0F );
							while ( ( ( 1 << fg2 ) & fg ) == 0 );


							ItemStack idrop = defender.getCurrentItemOrArmor( fg2 );

							if ( idrop.isItemStackDamageable() && idrop.getItemDamage() == 0 ) {
								int k = Math.max( idrop.getMaxDamage() - 25 , 1 );
								idrop.setItemDamage( MathHelper.clamp_int( idrop.getMaxDamage() - itemRand.nextInt( itemRand.nextInt( k ) + 1 ), 1, k ) );
							}


							defender.entityDropItem( idrop, 1.0F );
							defender.setCurrentItemOrArmor( fg2, null );
							if ( defender instanceof EntityLiving ) ( (EntityLiving)defender ).setCanPickUpLoot( false );

							break;
						case EFFECT_PENETRATION:		//wip
							if ( !defender.isDead )
								if ( defender.func_110143_aJ() <= amp )
									defender.setEntityHealth( 0 );
								else
									defender.setEntityHealth( defender.func_110143_aJ() - amp );
							break;

						}

					if ( critEffect && attacker instanceof EntityPlayer ) ( (EntityPlayer) attacker ).onEnchantmentCritical( defender );
				}


		return super.hitEntity( items, defender, attacker );
	}


	@Override
	public boolean hasEffect( ItemStack items, int pass ) {
		return hasFoiledEffect ? true : super.hasEffect( items, pass );
	}

}
