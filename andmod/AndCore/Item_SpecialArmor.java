package andmod.AndCore;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public class Item_SpecialArmor extends Item_Armor implements ISpecialArmor {

	protected ArrayList<Integer> IDList = new ArrayList<Integer>();
	protected ArrayList<Integer> AmplifierList = new ArrayList<Integer>();
	protected ArrayList<Integer> FlagList = new ArrayList<Integer>();
	protected boolean canReduceAllDamage 	= false;		//全てのダメージを軽減します。
	protected boolean isDisposable			= false;		//使用とともにAPが減少していきます。「使い捨て」の状態です。
	protected double consumptionRatio		= 1.0;			//ダメージを受けたときの耐久値の減少比です。多くすると壊れやすくなります。


	public static final int EFFECT_IGNITION			=  256 + 0;		//自分に点火します。火だるまです。
	public static final int EFFECT_EXTINGUISHMENT	=  256 + 1;		//自分に火がついているとき、自動で消火します。
	public static final int EFFECT_AUTOREPAIRING	=  256 + 2;		//耐久値が回復します。
	public static final int EFFECT_FRAGILE			=  256 + 3;		//耐久値が減少します。
	public static final int EFFECT_HAPPINESS		=  256 + 4;		//装備しているだけで経験値が入ります。お得です。
	public static final int EFFECT_SECRETEATING		=  256 + 5;		//満腹度を回復します。
	public static final int EFFECT_EXPLOSION		=  256 + 6;		//自爆します。
	public static final int EFFECT_HEALTHEXTEND		=  256 + 7;		//最大体力を上昇させます。(WIP)
	public static final int EFFECT_GRAVITY			=  256 + 8;		//重力を増大させます。
	public static final int EFFECT_FEATHERFALLING	=  256 + 9;		//落下速度を緩和します。
	public static final int EFFECT_LUMINESCENCE		=  256 + 10;	//発光させます。
	public static final int EFFECT_FREEZE			=  256 + 11;	//凍結させます。具体的には、足元の水源を氷に、溶岩源を黒曜石に、溶岩流を石に変化させます。
	public static final int EFFECT_INVINCIBLE		=  384 + 0;		//あらゆるダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTALL		=  384 + 1;		//鎧で防御可能なダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTFALLING	=  384 + 2;		//落下やテレポートのダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTEXPLOSION	=  384 + 3;		//爆発ダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTPROJECTILE	=  384 + 4;		//飛び道具によるダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTMAGIC		=  384 + 5;		//魔法やポーションによるダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTFIRE		=  384 + 6;		//火炎属性のダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTCACTUS		=  384 + 7;		//サボテンのトゲによるダメージに対し、抵抗力を得ます。
	public static final int EFFECT_RESISTSTATUS		=  512    ;		//状態異常に対し耐性を得ます。この値に状態異常IDを足して使います。
	public static final int EFFECT_COUNTERSTATUS	=  768    ;		//ダメージを受けたときに相手に状態異常を与えます。この値に状態異常IDを足して使います。
	public static final int EFFECT_COUNTERDAMAGE	= 1024 + 0;		//ダメージを受けたとき、相手に固定ダメージを与えます。
	public static final int EFFECT_COUNTERREFLECT	= 1024 + 1;		//ダメージを受けたとき、相手に受けたダメージに比例してダメージを与えます。
	public static final int EFFECT_COUNTERIGNITION	= 1024 + 2;		//ダメージを受けたとき、相手に火をつけます。
	public static final int EFFECT_COUNTEREXPLOSION	= 1024 + 3;		//ダメージを受けたとき、相手を爆破します。

	public static final int AMP_RESIST_WEAKNESS			= 0x1000000;	//むしろダメージが増えます
	public static final int AMP_RESIST_UNBREAKABLE		= 0x2000000;	//属性耐性のとき、耐久値が減らなくなります
	public static final int AMP_AMPLIFIERSHIFT			= 11;
	public static final int AMP_DURATIONSHIFT			= 19;
	public static final int AMP_COUNTER_PROJECTILE		= 1 << 27;		//遠隔攻撃も反射します
	public static final int AMP_EXPLOSION_DESTRUCTABLE	= 1 << 30;		//爆発が地形破壊するかどうか（反撃用）
	public static final int AMP_EXPLOSION_UNSAFE		= AMP_EXPLOSION_DESTRUCTABLE >> 1;	//自分も爆発ダメージを受けるか
	public static final int AMP_EXPLOSION_FLAMMABLE		= AMP_EXPLOSION_DESTRUCTABLE >> 2;	//火をばら撒くか

	public static final int FLAG_ANYTIME	= 0x0;		//いつでも
	public static final int FLAG_DAY		= 0x1;		//昼のみ
	public static final int FLAG_NIGHT		= 0x2;		//夜のみ
	public static final int FLAG_SUNLIGHT	= 0x4;		//直射日光のあたるところのみ
	public static final int FLAG_DARKNESS	= 0x8;		//直射日光のあたらないところのみ
	public static final int FLAG_WET		= 0x10;		//濡れている（水中か雨のなか）ときのみ
	public static final int FLAG_DRY		= 0x20;		//濡れていないとき
	public static final int FLAG_FULLEQ		= 0x40;		//完全装備（すべて同じ素材の鎧で揃える）のとき
	public static final int FLAG_LV5		= 0x80;		//Lv5以上のときのみ
	public static final int FLAG_LV10		= 0x100;	//Lv10以上のときのみ
	public static final int FLAG_LV20		= 0x200;	//Lv20以上のときのみ
	public static final int FLAG_MOONLIGHT	= 0x400;	//月光の下のみ


	/* ------------------------------------------------------------------------
		パラメータ設定資料
	   ------------------------------------------------------------------------
	   @コードを読んでいる方
		これはダメ手法を用いて開発されています。
	   	発狂するので、読まないほうがいいです。
	   	他の方のソースを参考にしてください。
	   ------------------------------------------------------------------------
		確率は基本的に(n/1000)/tick = (0.1n%)/tick. 1000以内の入力を想定。
		(/10)は設定値が指定値/10になる事。
	   ------------------------------------------------------------------------
		0-255(potionEffect)		強化レベル
		ignition				確率
		extinguishment			確率
		autoRepairing			確率 | 回復量<<amplifierShift
		fragile					確率 | 減少量<<amplifierShift
		happiness				確率 | 増加量<<amplifierShift
		secretEating			確率 | 満腹度回復量<<amplifierShift(<256) | 腹持ち回復量<<durationShift(/10)
		explosion				確率 | 爆発力(/10)<<amplifierShift | explosion_unsafe | explosion_flammable | explosion_destructable
		healthExtend			体力上昇量(WIP)
		gravity					重力増加率(/1000)
		featherFalling			重力軽減率(/1000)
		luminescence			発光ブロックのメタデータ
		freeze					(なし)
		resist***				防御率(/1000)(0=無敵) | resist_unbreakable | resist_weakness; 防御率に負値を指定しない!(代わりにresist_weaknessを使う)
		512-767(resistEffect)	確率
		counterStatus-+256		確率 | 効果時間<<durationShift | 効果レベル<<amplifierShift
		counterDamage			確率 | ダメージ<<amplifierShift
		counterReflect			確率 | 割合(%)<<amplifierShift
		counterIgnition			確率 | 効果時間<<durationShift
		counterExplosion		確率 | 爆発力(/10)<<amplifierShift | explosion_unsafe | explosion_flammable | explosion_destructable

	  ------------------------------------------------------------------------ */




	/**
	 * 新しい、特殊効果を持った鎧を定義します。
	 * @param itemID		アイテムのID。
	 * @param texture		鎧のテクスチャへのパス。[0]にレギンス以外の、[1]にレギンスのテクスチャを指定してください。
	 * @param material		素材。
	 * @param armorType		鎧のタイプ。0=ヘルメット, 1=チェストプレート, 2=レギンス, 3=ブーツ となります。
	 */
	public Item_SpecialArmor( int itemID, String[] texture,	EnumArmorMaterial material, int armorType ) {
		super( itemID, texture, material, armorType );
	}


	/**
	 * 新しい、特殊効果を持った鎧を定義します。
	 * @param itemID		アイテムのID。
	 * @param topTexture	鎧のテクスチャへのパス。レギンス以外のテクスチャを指定します。
	 * @param legsTexture	鎧のテクスチャへのパス。レギンスのテクスチャを指定します。
	 * @param material		素材。
	 * @param armorType		鎧のタイプ。0=ヘルメット, 1=チェストプレート, 2=レギンス, 3=ブーツ となります。
	 */
	public Item_SpecialArmor( int itemID, String topTexture, String legsTexture, EnumArmorMaterial material, int armorType ) {
		super( itemID, topTexture, legsTexture, material, armorType );
	}


	/**
	 * 全てのダメージを軽減できるかどうかを指定します。
	 * @param flag	設定値。既定値はfalseです。
	 * @return		設定されたアイテムを返します。
	 */
	public Item_SpecialArmor setCanReduceAllDamage( boolean flag ) {
		canReduceAllDamage = flag;
		return this;
	}


	/**
	 * 使用とともに防御力が低下する、「使い捨て」状態にするかを指定します。
	 * @param flag	設定値。既定値はfalseです。
	 * @return		設定されたアイテムを返します。
	 */
	public Item_SpecialArmor setIsDisposable( boolean flag ) {
		isDisposable = flag;
		return this;
	}


	/**
	 * 耐久値の減少比を指定します。
	 * @param ratio
	 * @return
	 */
	public Item_SpecialArmor setConsumptionRatio( double ratio ) {
		consumptionRatio = ratio;
		return this;
	}


	/**
	 * 特殊効果を指定します。
	 * @param effectID		効果ID。ポーションIDを指定すればその効果を与えます。そのほかはEFFECT定数を参照してください。
	 * @param amplifier		強化レベル。効果IDによって解釈が異なります。
	 * @param flag			発現フラグ。FLAG系定数を参照してください。|で複数指定できます。
	 * @return				設定されたアイテムを返します。
	 */
	public Item_SpecialArmor addEffect( int effectID, int amplifier, int flag ) {
		IDList.add( effectID );
		AmplifierList.add( amplifier );
		FlagList.add( flag );
		return this;
	}


	/**
	 * 防御力を計算します。
	 * @param items	鎧のデータ。
	 * @return		計算された防御力。
	 */
	protected int calcAP( ItemStack items ) {
		int maxAP = getArmorMaterial().getDamageReductionAmount( armorType );

		if ( !isDisposable ) return maxAP;
		return MathHelper.clamp_int( MathHelper.ceiling_double_int( maxAP * ( items.getMaxDamage() - items.getItemDamage() + 1 ) * 1.2 / items.getMaxDamage() ) , 1, maxAP );
	}



	/**
	 * 鎧を介したダメージを計算する際に使用する係数を取得します。
	 * 鎧は、より高い優先度を持つものからダメージ計算がなされます。
	 * 同じ優先順位の鎧が複数存在する場合、ダメージは吸収率に基づいて分配されます。
	 */
	@Override
	public ArmorProperties getProperties( EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot ) {
		double ratio = getReductionAmountByEffect( player, source );

		if ( ratio == Double.POSITIVE_INFINITY )
			return new ArmorProperties( 1, 1.0, Integer.MAX_VALUE );
		if ( canReduceAllDamage || !source.isUnblockable() )
			ratio += calcAP( armor ) / 25.0;

		return new ArmorProperties( 0, ratio, MathHelper.ceiling_double_int( ( armor.getMaxDamage() - armor.getItemDamage() + 1 ) / consumptionRatio ) );
	}


	@Override
	public int getArmorDisplay( EntityPlayer player, ItemStack armor, int slot ) {
		return calcAP( armor );
	}


	@Override
	public void damageArmor( EntityLivingBase eliv, ItemStack items, DamageSource source, int damage, int slot ) {

		double ra = getReductionAmountByEffect( eliv, source );
		if ( ( ra != 0.0 || canReduceAllDamage || !source.isUnblockable() ) && canDecreaseDurabilityByEffect( eliv, source ) ) {
			items.damageItem( MathHelper.ceiling_double_int( damage * consumptionRatio ), eliv );


			//point: カウンター処理
			for ( int i = 0; i < IDList.size(); i ++ ) {
				int id = IDList.get( i );
				int amp = AmplifierList.get( i );
				int flag = FlagList.get( i );

				if ( !( eliv instanceof EntityPlayer ) || !canProcessEffect( (EntityPlayer)eliv, flag ) || itemRand.nextInt( 1000 ) >= ( amp & ( ( 1 << ( AMP_AMPLIFIERSHIFT - 1 ) ) - 1 ) ) ) continue;

				EntityLivingBase esrc;
				if ( source.getSourceOfDamage() != null && ( source.getSourceOfDamage() instanceof EntityLivingBase ) )
					esrc = (EntityLivingBase)source.getSourceOfDamage();	//直接攻撃を対象にします。
				else if ( ( amp & AMP_COUNTER_PROJECTILE ) != 0 && source.getEntity() != null && ( source.getEntity() instanceof EntityLivingBase ) )
					esrc = (EntityLivingBase)source.getEntity();			//遠隔攻撃を対象にします。
				else continue;

				if ( EFFECT_COUNTERSTATUS <= id && id < EFFECT_COUNTERSTATUS + 256 )
					esrc.addPotionEffect( new PotionEffect( id - EFFECT_COUNTERSTATUS, amp >> AMP_DURATIONSHIFT, ( amp >> AMP_AMPLIFIERSHIFT ) & 0xFF ) );
				else if ( id == EFFECT_COUNTERDAMAGE )
					esrc.attackEntityFrom( DamageSource.causeThornsDamage( eliv ), amp >> AMP_AMPLIFIERSHIFT );
				else if ( id == EFFECT_COUNTERREFLECT )
					esrc.attackEntityFrom( DamageSource.causeThornsDamage( eliv ), MathHelper.ceiling_double_int( ( amp >> AMP_AMPLIFIERSHIFT ) * damage / 100.0 ) );
				else if ( id == EFFECT_COUNTERIGNITION )
					esrc.setFire( amp >> AMP_DURATIONSHIFT );
				else if ( id == EFFECT_COUNTEREXPLOSION )
					if ( !eliv.worldObj.isRemote )
						eliv.worldObj.newExplosion( ( ( amp & AMP_EXPLOSION_UNSAFE ) != 0 ? null : eliv ), esrc.posX, esrc.posY, esrc.posZ, ( amp << 5 >> ( 5 + AMP_AMPLIFIERSHIFT ) ) / 10.0F, ( amp & AMP_EXPLOSION_FLAMMABLE ) != 0, ( amp & AMP_EXPLOSION_DESTRUCTABLE ) != 0 );


			}

		}

	}


	@Override
	public void onArmorTickUpdate( World world, EntityPlayer eplayer, ItemStack items ) {
		
		for ( int i = 0; i < IDList.size(); i ++ ) {
			int id = IDList.get( i );
			int amp = AmplifierList.get( i );
			int flag = FlagList.get( i );

			if ( !canProcessEffect( eplayer, flag ) ) continue;

			
			if ( !world.isRemote ) {
				
				if ( id < 256 ) { //fixme: HealthBoost/Absorption は正常に動作しません。
					int time = 2;
					if ( id == Potion.nightVision.id )
						time = 219;
					else if ( id == Potion.confusion.id )
						time = 99;
					else if ( id == Potion.regeneration.id ) 
						time = 52;
					else if ( id == Potion.poison.id ) 
						time = 27;
					else if ( id == Potion.wither.id ) 
						time = 42;
					
					if ( id == Potion.regeneration.id || id == Potion.poison.id || id == Potion.wither.id ) {
						PotionEffect pe = eplayer.getActivePotionEffect( Potion.potionTypes[id] );
						if ( pe == null || pe.duration <= 2 )
							eplayer.addPotionEffect( new PotionEffect( id, time, amp ) );
						
					} else if ( id == Potion.field_76444_x.id || id == Potion.field_76434_w.id ) {		//HealthBoost/Absorption
						PotionEffect pe = eplayer.getActivePotionEffect( Potion.potionTypes[id] );
						if ( pe == null )
							eplayer.addPotionEffect( new PotionEffect( id, time, amp ) );
						else if ( pe.duration <= 2 )
							pe.duration = time;
						
					} else eplayer.addPotionEffect( new PotionEffect( id, time, amp ) );

				} else if ( id < 512 )
					switch( id ) {
					case EFFECT_IGNITION:
						if ( itemRand.nextInt( 1000 ) < amp )
							eplayer.setFire( 1 ); break;
					case EFFECT_EXTINGUISHMENT:
						if ( itemRand.nextInt( 1000 ) < amp )
							eplayer.extinguish(); break;
					case EFFECT_AUTOREPAIRING:
						if ( itemRand.nextInt( 1000 ) < ( amp & ( ( 1 << AMP_AMPLIFIERSHIFT ) - 1 ) ) )
							items.setItemDamage( items.getItemDamage() - ( amp >> AMP_AMPLIFIERSHIFT ) );
						break;
					case EFFECT_FRAGILE:
						if ( itemRand.nextInt( 1000 ) < ( amp & ( ( 1 << AMP_AMPLIFIERSHIFT ) - 1 ) ) )
							items.damageItem( amp >> AMP_AMPLIFIERSHIFT, eplayer );
						break;
					case EFFECT_HAPPINESS:
						if ( itemRand.nextInt( 1000 ) < ( amp & ( ( 1 << AMP_AMPLIFIERSHIFT ) - 1 ) ) ) {
							eplayer.addExperience( amp >> AMP_AMPLIFIERSHIFT );
							eplayer.playSound( "random.orb", 0.1F, 0.5F * ( (itemRand.nextFloat() - itemRand.nextFloat() ) * 0.7F + 1.8F ) );
						} break;
					case EFFECT_SECRETEATING:
						if ( itemRand.nextInt( 1000 ) < ( amp & ( ( 1 << AMP_AMPLIFIERSHIFT ) - 1 ) ) )
							eplayer.getFoodStats().addStats( ( amp >> AMP_AMPLIFIERSHIFT ) & 0xFF, ( amp >> AMP_DURATIONSHIFT ) / 10.0F ); break;
					case EFFECT_EXPLOSION:
						if ( itemRand.nextInt( 1000 ) < ( amp & ( ( 1 << AMP_AMPLIFIERSHIFT ) - 1 ) ) )
							eplayer.worldObj.newExplosion( ( ( amp & AMP_EXPLOSION_UNSAFE ) != 0 ? null : eplayer ), eplayer.posX, eplayer.posY, eplayer.posZ, ( amp << 5 >> ( 5 + AMP_AMPLIFIERSHIFT ) ) / 10.0F, ( amp & AMP_EXPLOSION_FLAMMABLE ) != 0, ( amp & AMP_EXPLOSION_DESTRUCTABLE ) != 0 );
						break;
					case EFFECT_LUMINESCENCE:
						{
							int bx = MathHelper.floor_double( eplayer.posX ), by = MathHelper.floor_double( eplayer.posY ), bz = MathHelper.floor_double( eplayer.posZ );
							if ( world.isAirBlock( bx, by, bz ) ) {
								world.setBlock( bx, by, bz, AndMod_AndCore.getBlockID( "Luminescence" ), amp | 12, 2 );
							}
						} break;
						
					case EFFECT_FREEZE:
						{
							int bx = MathHelper.floor_double( eplayer.posX ), by = MathHelper.floor_double( eplayer.posY ), bz = MathHelper.floor_double( eplayer.posZ );
							for ( int y = -1; y <= 0; y ++ ) {
								for ( int x = 0; x <= 0; x ++ ) {
									for ( int z = 0; z <= 0; z ++ ) {
										
										int bid = world.getBlockId( bx + x, by + y, bz + z );
										int meta = world.getBlockMetadata( bx + x, by + y, bz + z );
										
										if ( ( bid == Block.waterStill.blockID || bid == Block.waterMoving.blockID ) ) {
											if ( meta == 0 ) {
												world.setBlock( bx + x, by + y, bz + z, Block.ice.blockID, 0, 3 );
											} else {
												//world.setBlock( bx + x, by + y, bz + z, Block.snow.blockID, 7 - meta, 3 );
											}
										}
										if ( bid == Block.lavaStill.blockID || bid == Block.lavaMoving.blockID ) {
											if ( meta == 0 ) {
												world.setBlock( bx + x, by + y, bz + z, Block.obsidian.blockID, 0, 3 );
											} else {
												world.setBlock( bx + x, by + y, bz + z, Block.stone.blockID, 0, 3 );
											}
										}
										if ( bid == Block.fire.blockID ) {
											world.setBlockToAir( bx + x, by + y, bz + z );
										}
										
									}
								}
							}
						}
						break;
					}

				
				else if ( id < 768 && itemRand.nextInt( 1000 ) < amp )
					eplayer.removePotionEffect( id - 512 );
				
				
			} else {
				switch( id ) {	//checkme
				case EFFECT_GRAVITY:
					if ( !eplayer.onGround ) {
						eplayer.motionY -= amp / 1000.0;
					} break;
				case EFFECT_FEATHERFALLING:
					if ( !eplayer.onGround && !eplayer.isSneaking() && eplayer.motionY < -amp / 1000.0 ) {
						eplayer.motionY = -amp / 1000.0;
						eplayer.fallDistance = 0;
					} break;
				}

			}

			
		}
	}


	/**
	 * ダメージを軽減できるかどうかを取得します。
	 * @param id		効果ID。
	 * @param source	ダメージソース。
	 * @return			軽減可能ならtrueを返します。
	 */
	protected boolean canReduceDamageByEffect( int id, DamageSource source ) {

		return ( ( id == EFFECT_INVINCIBLE ) ||
				( id == EFFECT_RESISTALL && !source.isUnblockable() ) ||
				( id == EFFECT_RESISTFALLING && source.getDamageType().equals( "fall" ) ) ||
				( id == EFFECT_RESISTEXPLOSION && source.isExplosion() ) ||
				( id == EFFECT_RESISTPROJECTILE && source.isProjectile() ) ||
				( id == EFFECT_RESISTMAGIC && source.isMagicDamage() ) ||
				( id == EFFECT_RESISTFIRE && source.isFireDamage() ) ||
				( id == EFFECT_RESISTCACTUS && source.getDamageType().equals( "cactus" ) ) ) ;
	}


	/**
	 * 攻撃に対する防御力を取得します。
	 * @param eliv		装備しているプレイヤー。
	 * @param source	ダメージソース。
	 * @return			防御力を返します。そのダメージソースに対して無敵だった場合、Double.POSITIVE_INFINITYを返します。
	 */
	protected double getReductionAmountByEffect( EntityLivingBase eliv, DamageSource source ) {

		double ret = 0.0;

		for ( int i = 0; i < IDList.size(); i ++ ) {
			int id = IDList.get( i );
			int amp = AmplifierList.get( i );
			int flag = FlagList.get( i );

			if ( !( eliv instanceof EntityPlayer ) || !canProcessEffect( (EntityPlayer)eliv, flag ) ) continue;

			if ( canReduceDamageByEffect( id, source ) ) {

				amp = amp & ~AMP_RESIST_UNBREAKABLE;
				if ( ( amp & AMP_RESIST_WEAKNESS ) != 0 ) {
					amp = amp & ~AMP_RESIST_WEAKNESS;
					amp *= -1;
				}

				if ( amp == 0 ) return Double.POSITIVE_INFINITY;
				ret += amp / 1000.0;
			}
		}

		return ret;
	}


	/**
	 * 鎧の耐久値を減少させるかどうかを取得します。
	 * @param eliv		装備している生物。
	 * @param source	ダメージソース。
	 * @return			減少させられる時はtrueを返します。
	 */
	protected boolean canDecreaseDurabilityByEffect( EntityLivingBase eliv, DamageSource source ) {

		double ret = 0.0;

		for ( int i = 0; i < IDList.size(); i ++ ) {
			int id = IDList.get( i );
			int amp = AmplifierList.get( i );
			int flag = FlagList.get( i );

			if ( !( eliv instanceof EntityPlayer ) || !canProcessEffect( (EntityPlayer)eliv, flag ) ) continue;

			if ( canReduceDamageByEffect( id, source ) )
				if ( ( amp & AMP_RESIST_UNBREAKABLE ) != 0 ) return false;
		}

		return true;
	}






	/**
	 * 条件に合致しているかどうかを調べます。
	 * @param eplayer	調査対象。
	 * @param flag		条件。
	 * @return			合致していればtrueを返します。
	 */
	protected boolean canProcessEffect( EntityPlayer eplayer, int flag ) {
		//これは「～でないなら」falseを返す、という処理になっています。

		if ( ( flag & 0x1 ) != 0 ) 		//昼のみ
			if ( !eplayer.worldObj.isDaytime() ) return false;

		if ( ( flag & 0x2 ) != 0 )		//夜のみ
			if (  eplayer.worldObj.isDaytime() ) return false;

		if ( ( flag & 0x4 ) != 0 )		//日光のあるときのみ　ゾンビの炎上ルーチンと同じ
			if ( !eplayer.worldObj.isDaytime() ||
					eplayer.getBrightness( 1.0F ) <= 0.5F ||
					!eplayer.worldObj.canBlockSeeTheSky( MathHelper.floor_double( eplayer.posX ), MathHelper.floor_double( eplayer.posY ), MathHelper.floor_double( eplayer.posZ ) ) )
				return false;

		if ( ( flag & 0x8 ) != 0 )		//日光のないときのみ
			if ( eplayer.worldObj.isDaytime() &&
					eplayer.getBrightness( 1.0F ) > 0.5F &&
					eplayer.worldObj.canBlockSeeTheSky( MathHelper.floor_double( eplayer.posX ), MathHelper.floor_double( eplayer.posY ), MathHelper.floor_double( eplayer.posZ ) ) )
				return false;

		if ( ( flag & 0x10 ) != 0 )		//濡れているときのみ////
			if ( !eplayer.isWet() ) return false;

		if ( ( flag & 0x20 ) != 0 )		//濡れていないときのみ
			if ( eplayer.isWet() ) return false;

		if ( ( flag & 0x40 ) != 0 )	//全て装備を統一した場合
			if ( ! (
					eplayer.inventory.armorInventory[0] != null && eplayer.inventory.armorInventory[1] != null &&
					eplayer.inventory.armorInventory[2] != null && eplayer.inventory.armorInventory[3] != null &&
					eplayer.inventory.armorInventory[0].getItem() instanceof Item_SpecialArmor &&
					eplayer.inventory.armorInventory[1].getItem() instanceof Item_SpecialArmor &&
					eplayer.inventory.armorInventory[2].getItem() instanceof Item_SpecialArmor &&
					eplayer.inventory.armorInventory[3].getItem() instanceof Item_SpecialArmor &&
					((ItemArmor)eplayer.inventory.armorInventory[0].getItem()).getArmorMaterial() == getArmorMaterial() &&
					((ItemArmor)eplayer.inventory.armorInventory[1].getItem()).getArmorMaterial() == getArmorMaterial() &&
					((ItemArmor)eplayer.inventory.armorInventory[2].getItem()).getArmorMaterial() == getArmorMaterial() &&
					((ItemArmor)eplayer.inventory.armorInventory[3].getItem()).getArmorMaterial() == getArmorMaterial() ) )
				return false;

		if ( ( flag & 0x80 ) != 0 )	//レベル5以上のときのみ
			if ( eplayer.experienceLevel < 5  ) return false;

		if ( ( flag & 0x100 ) != 0 )	//レベル10以上のときのみ
			if ( eplayer.experienceLevel < 10 ) return false;

		if ( ( flag & 0x200 ) != 0 )	//レベル20以上のときのみ
			if ( eplayer.experienceLevel < 20 ) return false;

		if ( ( flag & 0x400 ) != 0 )	//月光を浴びているときのみ　具体的には暗い・空が見える・夜である　の条件を満たしたときのみ
			if (  eplayer.worldObj.isDaytime() ||
					eplayer.getBrightness( 1.0F ) >= 0.5F ||
					!eplayer.worldObj.canBlockSeeTheSky( MathHelper.floor_double( eplayer.posX ), MathHelper.floor_double( eplayer.posY ), MathHelper.floor_double( eplayer.posZ ) ) )
				return false;

		return true;
	}



	
	
	
}
