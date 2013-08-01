package andmod.AndCore;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;

public class Event_MobEquipment {

	private int masterProb = 100;

	private ArrayList<ItemStack> ItemList = new ArrayList<ItemStack>();
	private ArrayList<Integer> FlagList = new ArrayList<Integer>();
	private ArrayList<Double> ProbabilityList = new ArrayList<Double>();

	public static final int FLAG_ZOMBIE			= 0x1;
	public static final int FLAG_SKELETON		= 0x2;
	public static final int FLAG_WITHERSKELETON	= 0x4;
	public static final int FLAG_ZOMBIEPIGMAN	= 0x8;

	public static final int FLAG_OVERWORLD		= FLAG_ZOMBIE | FLAG_SKELETON;
	public static final int FLAG_NETHER			= FLAG_WITHERSKELETON | FLAG_ZOMBIEPIGMAN;
	public static final int FLAG_SWORDMAN		= FLAG_ZOMBIE | FLAG_WITHERSKELETON | FLAG_ZOMBIEPIGMAN;
	public static final int FLAG_ALLMOB			= FLAG_ZOMBIE | FLAG_SKELETON | FLAG_WITHERSKELETON | FLAG_ZOMBIEPIGMAN;

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
		ItemList.add( weapon );
		FlagList.add( flag );
		ProbabilityList.add( probability );
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
		ItemList.add( helmet );
		ItemList.add( chestplate );
		ItemList.add( leggings );
		ItemList.add( boots );
		FlagList.add( flag | FLAG_ISARMOR );
		ProbabilityList.add( probability );
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


		int cur = 0;
		for ( int i = 0; i < FlagList.size(); i ++ ) {

			int flag = FlagList.get( i );


			//note: 確率基本値は0.15, 0.28で以前のHardと同じぐらいです
			if ( e.entityLiving.worldObj.rand.nextDouble() < masterProb / 100.0 * ProbabilityList.get( i ) * e.entityLiving.worldObj.func_110746_b( e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ ) ) {
				if ( ( ( flag & FLAG_ZOMBIE ) != 0 && e.entityLiving instanceof EntityZombie && !( e.entityLiving instanceof EntityPigZombie ) ) ||
					 ( ( flag & FLAG_SKELETON ) != 0 && e.entityLiving instanceof EntitySkeleton && ( (EntitySkeleton)e.entityLiving ).getSkeletonType() == 0 ) ||
					 ( ( flag & FLAG_WITHERSKELETON ) != 0 && e.entityLiving instanceof EntitySkeleton && ( (EntitySkeleton)e.entityLiving ).getSkeletonType() == 1 ) ||
					 ( ( flag & FLAG_ZOMBIEPIGMAN ) != 0 && e.entityLiving instanceof EntityPigZombie ) ||
					 ( ( flag & FLAG_ANYMOB ) != 0 ) ) {

					if ( ( flag & FLAG_ISARMOR ) != 0 )
						tryToEquipArmor( new ItemStack[]{ ItemList.get( cur ), ItemList.get( cur + 1 ), ItemList.get( cur + 2 ), ItemList.get( cur + 3 ) }, flag, e.entityLiving );
					else
						tryToEquipWeapon( ItemList.get( cur ), flag, e.entityLiving );
				}
			}
			
			if ( ( flag & FLAG_ISARMOR ) != 0 ) cur += 3;

			cur ++;
		}
	}



	/**
	 * 武器の装備を試みます。
	 * @param items		装備させる武器。
	 * @param flag		フラグ。
	 * @param eliv		装備させるmob。
	 */
	private void tryToEquipWeapon( ItemStack items, int flag, EntityLivingBase eliv ) {
		
		if ( ( flag & FLAG_NONOVERRIDABLE ) == 0 || eliv.getCurrentItemOrArmor( 0 ) == null )
			eliv.setCurrentItemOrArmor( 0, items );
	}


	/**
	 * 鎧の装備を試みます。
	 * @param armor		装備させる鎧の配列。
	 * @param flag		フラグ。
	 * @param eliv		装備させるmob。
	 */
	private void tryToEquipArmor( ItemStack[] armor, int flag, EntityLivingBase eliv ) {
		
		if ( ( flag & FLAG_NONOVERRIDABLE ) == 0 || eliv.getCurrentItemOrArmor( 4 ) == null ) {		//ヘルメットから装備するので、ヘルメットがなければ未装備とみなします
			for ( int i = 3; i >= 0; i -- ) {
				if ( i < 3 && eliv.worldObj.rand.nextFloat() < ( eliv.worldObj.difficultySetting == 3 ? 0.1F : 0.25F ) ) break;
	
				eliv.setCurrentItemOrArmor( i + 1, armor[3 - i] );
			}
		}
		
	}

}
