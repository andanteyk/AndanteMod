package andmod.AndCore;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;

public class Event_MobEquipment {

	private int masterProb = 100;

	private ArrayList<ItemStack> WeaponList = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> ArmorList = new ArrayList<ItemStack>();
	private ArrayList<Integer> WeaponFlagList = new ArrayList<Integer>();
	private ArrayList<Integer> ArmorFlagList = new ArrayList<Integer>();
	private ArrayList<Double> WeaponProbabilityList = new ArrayList<Double>();
	private ArrayList<Double> ArmorProbabilityList = new ArrayList<Double>();
	private int[] weaponSelectionList = null, armorSelectionList = null;
	
	public static final int FLAG_ZOMBIE			= 0x1;
	public static final int FLAG_SKELETON		= 0x2;
	public static final int FLAG_WITHERSKELETON	= 0x4;
	public static final int FLAG_ZOMBIEPIGMAN	= 0x8;

	public static final int FLAG_OVERWORLD		= FLAG_ZOMBIE | FLAG_SKELETON;
	public static final int FLAG_NETHER			= FLAG_WITHERSKELETON | FLAG_ZOMBIEPIGMAN;
	public static final int FLAG_SWORDMAN		= FLAG_ZOMBIE | FLAG_WITHERSKELETON | FLAG_ZOMBIEPIGMAN;
	public static final int FLAG_BONE			= FLAG_SKELETON | FLAG_WITHERSKELETON;
	public static final int FLAG_ROTTEN			= FLAG_ZOMBIE | FLAG_ZOMBIEPIGMAN;
	public static final int FLAG_ALLMOB			= FLAG_ZOMBIE | FLAG_SKELETON | FLAG_WITHERSKELETON | FLAG_ZOMBIEPIGMAN;

	public static final int FLAG_DISABLEENCHANT	= 0x8000000;		//装備をエンチャントしません。
	public static final int FLAG_NONOVERRIDABLE	= 0x10000000;		//置換不能にします。既存の装備がある場合、そちらを優先します。
	public static final int FLAG_ANYMOB			= 0x20000000;		//全てのmobに適用します。蜘蛛も装備するなど、不具合が発生します。デバッグ用です。
	public static final int FLAG_ISARMOR		= 0x40000000;		//内部フラグです。意識する必要はありません。鎧データかどうかを判別します。


	/**
	 * mobの装備する武器を追加します。
	 * @param weapon		追加する武器。
	 * @param flag			フラグ。FLAG系定数を参照してください。
	 * @param probability	装備確率。0.15が基本値です。
	 */
	public void addMobWeaponEquipment( ItemStack weapon, int flag, double probability ) {
		WeaponList.add( weapon );
		WeaponFlagList.add( flag );
		WeaponProbabilityList.add( probability );
	}


	/**
	 * mobの装備する鎧を追加します。
	 * @param helmet		追加するヘルメット。
	 * @param chestplate	追加するチェストプレート。
	 * @param leggings		追加するレギンス。
	 * @param boots			追加するブーツ。
	 * @param flag			フラグ。FLAG系定数を参照してください。
	 * @param probability	装備確率。0.15が基本値です。
	 */
	public void addMobArmorEquipment( ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, int flag, double probability ) {
		ArmorList.add( helmet );
		ArmorList.add( chestplate );
		ArmorList.add( leggings );
		ArmorList.add( boots );
		ArmorFlagList.add( flag | FLAG_ISARMOR );
		ArmorProbabilityList.add( probability );
	}


	/**
	 * 装備確率のマスター値を設定します。
	 * @param probability	マスター値。100が基本値です。
	 */
	public void setMasterProbability( int probability ) {
		masterProb = probability;
	}



	//よいこはまねしないでください
	@ForgeSubscribe
	public void onLivingPackSize( LivingPackSizeEvent e ) {
		if ( e.entityLiving == null ) return;

		int equipped = 0;
		int cur = 0;
		EntityLivingBase eliv = e.entityLiving;
		World world = e.entityLiving.worldObj;
		
		
		if ( weaponSelectionList == null )
			weaponSelectionList = new int[ WeaponFlagList.size() ];
		if ( armorSelectionList == null )
			armorSelectionList = new int[ ArmorFlagList.size() ];
		
		initSelection( weaponSelectionList, world.rand );
		initSelection(  armorSelectionList, world.rand );
		
		
		for ( int i = 0; i < WeaponFlagList.size(); i ++ ) {
			int index = weaponSelectionList[i];
			
			if ( canEquip( eliv, WeaponFlagList.get( index ), WeaponProbabilityList.get( index ) ) ) {
				if ( tryToEquipWeapon( WeaponList.get( index ), WeaponFlagList.get( index ), eliv ) )
					break;
			}
		}
		
		for ( int i = 0; i < ArmorFlagList.size(); i ++ ) {
			int index = armorSelectionList[i];
			
			if ( canEquip( eliv, ArmorFlagList.get( index ), ArmorProbabilityList.get( index ) ) ) {
				tryToEquipArmor( new ItemStack[]{ ArmorList.get( index * 4 + 0 ), ArmorList.get( index * 4 + 1 ), ArmorList.get( index * 4 + 2 ), ArmorList.get( index * 4 + 3 ) }, ArmorFlagList.get( index ), eliv );
			}
		}
		
		/*
		for ( int i = 0; i < FlagList.size(); i ++ ) {

			int flag = FlagList.get( i );


			//note: 確率基本値は0.15, 0.28で以前のHardと同じぐらいです
			if ( e.entityLiving.worldObj.rand.nextDouble() < masterProb / 100.0 * ProbabilityList.get( i ) * e.entityLiving.worldObj.func_110746_b( e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ ) ) {
				if ( ( ( flag & FLAG_ZOMBIE ) != 0 && e.entityLiving instanceof EntityZombie && !( e.entityLiving instanceof EntityPigZombie ) ) ||
					 ( ( flag & FLAG_SKELETON ) != 0 && e.entityLiving instanceof EntitySkeleton && ( (EntitySkeleton)e.entityLiving ).getSkeletonType() == 0 ) ||
					 ( ( flag & FLAG_WITHERSKELETON ) != 0 && e.entityLiving instanceof EntitySkeleton && ( (EntitySkeleton)e.entityLiving ).getSkeletonType() == 1 ) ||
					 ( ( flag & FLAG_ZOMBIEPIGMAN ) != 0 && e.entityLiving instanceof EntityPigZombie ) ||
					 ( ( flag & FLAG_ANYMOB ) != 0 ) ) {

					if ( ( flag & FLAG_ISARMOR ) != 0 && ( equipped & 2 ) == 0 ) 
						equipped |= tryToEquipArmor( new ItemStack[]{ ItemList.get( cur ), ItemList.get( cur + 1 ), ItemList.get( cur + 2 ), ItemList.get( cur + 3 ) }, flag, e.entityLiving ) ? 2 : 0;
					else if ( ( equipped & 1 ) == 0 )
						equipped |= tryToEquipWeapon( ItemList.get( cur ), flag, e.entityLiving ) ? 1 : 0;
					
				}
			}
			
			if ( ( flag & FLAG_ISARMOR ) != 0 ) cur += 3;

			cur ++;
		}
		*/
	}


	private boolean canEquip( EntityLivingBase eliv, int flag, double probability ) {
		return ( ( ( flag & FLAG_ZOMBIE ) != 0 && eliv instanceof EntityZombie && !( eliv instanceof EntityPigZombie ) ) ||
			 	 ( ( flag & FLAG_SKELETON ) != 0 && eliv instanceof EntitySkeleton && ( (EntitySkeleton)eliv ).getSkeletonType() == 0 ) ||
				 ( ( flag & FLAG_WITHERSKELETON ) != 0 && eliv instanceof EntitySkeleton && ( (EntitySkeleton)eliv ).getSkeletonType() == 1 ) || 
				 ( ( flag & FLAG_ZOMBIEPIGMAN ) != 0 && eliv instanceof EntityPigZombie ) ||
				 ( ( flag & FLAG_ANYMOB ) != 0 ) ) && 
				 eliv.worldObj.rand.nextDouble() < masterProb / 100.0 * probability * eliv.worldObj.func_110746_b( eliv.posX, eliv.posY, eliv.posZ );
	}

	
	
	
	/**
	 * 武器の装備を試みます。
	 * @param items		装備させる武器。
	 * @param flag		フラグ。
	 * @param eliv		装備させるmob。
	 * @return			装備に成功したか。
	 */
	private boolean tryToEquipWeapon( ItemStack items, int flag, EntityLivingBase eliv ) {
		
		if ( ( flag & FLAG_NONOVERRIDABLE ) == 0 || eliv.getCurrentItemOrArmor( 0 ) == null ) {
			eliv.setCurrentItemOrArmor( 0, items.copy() );
			
			if ( ( flag & FLAG_DISABLEENCHANT ) == 0 ) 
				enchantWeapon( eliv );
			
			return true;
		}
		
		return false;
	}


	/**
	 * 鎧の装備を試みます。
	 * @param armor		装備させる鎧の配列。
	 * @param flag		フラグ。
	 * @param eliv		装備させるmob。
	 * @return			装備に成功したか。
	 */
	private boolean tryToEquipArmor( ItemStack[] armor, int flag, EntityLivingBase eliv ) {
		
		if ( ( flag & FLAG_NONOVERRIDABLE ) == 0 || 
				( eliv.getCurrentItemOrArmor( 1 ) == null && eliv.getCurrentItemOrArmor( 2 ) == null && eliv.getCurrentItemOrArmor( 3 ) == null && eliv.getCurrentItemOrArmor( 4 ) == null ) ) {	
			for ( int i = 3; i >= 0; i -- ) {
				if ( i < 3 && eliv.worldObj.rand.nextFloat() < ( eliv.worldObj.difficultySetting == 3 ? 0.1F : 0.25F ) ) break;
	
				eliv.setCurrentItemOrArmor( i + 1, armor[3 - i].copy() );
				if ( ( flag & FLAG_DISABLEENCHANT ) == 0 )
					enchantArmor( eliv, i );
			}
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 装備している武器をエンチャントします。
	 * @param eliv	対象となる生物。
	 */
	private void enchantWeapon( EntityLivingBase eliv ) {
		float f = eliv.worldObj.func_110746_b( eliv.posX, eliv.posY, eliv.posZ );

        if ( eliv.getHeldItem() != null && !eliv.getHeldItem().isItemEnchanted() && eliv.worldObj.rand.nextFloat() < 0.25F * f )
            EnchantmentHelper.addRandomEnchantment( eliv.worldObj.rand, eliv.getHeldItem(), (int)( 5.0F + f * (float)eliv.worldObj.rand.nextInt( 18 ) ) );
        
	}
	
	
	/**
	 * 装備している防具をエンチャントします。
	 * @param eliv	対象となる生物。
	 * @param index	鎧スロットの場所。
	 */
	private void enchantArmor( EntityLivingBase eliv, int index ) {
		float f = eliv.worldObj.func_110746_b( eliv.posX, eliv.posY, eliv.posZ );

        ItemStack items = eliv.getCurrentItemOrArmor( index + 1 );

        if ( items != null && !items.isItemEnchanted() && eliv.worldObj.rand.nextFloat() < 0.5F * f )
            EnchantmentHelper.addRandomEnchantment( eliv.worldObj.rand, items, (int)( 5.0F + f * (float)eliv.worldObj.rand.nextInt( 18 ) ) );
        
	}

	
	/** 判定配列を初期化、シャッフルします */
	private void initSelection( int[] selection, Random rand ) {
		
		for ( int i = 0; i < selection.length; i ++ )
			selection[i] = i;
		
		for( int i = selection.length - 1; i > 0; i -- ) {
			int t = rand.nextInt( i + 1 );
			
			int a = selection[t];
			selection[t] = selection[i];
			selection[i] = a;
		}
	}
	
}
