package andmod.AndCore;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;

public class Struct_Arrow {

	public int arrowID;

	public ArrayList<Integer> EffectIDList = new ArrayList<Integer>();
	public ArrayList<Integer> AmplifierList = new ArrayList<Integer>();
	public ArrayList<Integer> DurationList = new ArrayList<Integer>();
	public ArrayList<Float> ProbabilityList = new ArrayList<Float>();

	public double velocity;
	public double power;
	public double knockback;
	public double gravity;
	public ResourceLocation texture;

	public static final int EFFECT_FLAME				= 0x100;	//撃った矢に火が付きます。
	public static final int EFFECT_ENDERSTRIKE			= 0x101;	//エンダーマンに当たります。
	public static final int EFFECT_LIGHTNING			= 0x102;	//着弾点に雷を落とします。
	public static final int EFFECT_EXPLOSION			= 0x103;	//爆発を発生させます。クリーパーのものに似ています。
	public static final int EFFECT_EXPLOSIONWITHFIRE	= 0x104;	//爆発を発生させます。炎も設置します。ガストの火炎弾に似ています。
	public static final int EFFECT_EXPLOSIONFIREONLY	= 0x105;	//爆発を発生させます。火を設置しますが、地形は破壊しません。
	public static final int EFFECT_INCONSUMABLE			= 0x106;	//敵に当たった時に、アイテムとしてドロップします。
	public static final int EFFECT_PLACEBLOCK			= 0x107;	//着弾時にブロックを設置します。
	public static final int EFFECT_SHARPNESS			= 0x108;	//追加でダメージを与えます。
	public static final int EFFECT_SMITE				= 0x109;	//ゾンビに対して高いダメージを与えます。
	public static final int EFFECT_BANEOFARTHROPODS		= 0x10a;	//虫に対して高いダメージを与えます。
	public static final int EFFECT_ASSASSINATION		= 0x10b;	//ダメージを「プレイヤーが与えた」扱いにしません。
	

	public Struct_Arrow() {
		arrowID = 0;
	}

	public Struct_Arrow( int arrowID, double velocity, double power, double knockback, double gravity, String texture ) {

		this.arrowID = arrowID;

		this.velocity = velocity;
		this.power = power;
		this.knockback = knockback;
		this.gravity = gravity;
		this.texture = new ResourceLocation( texture );
	}



	/**
	 * 指定した効果IDが存在しているかを調べます。
	 * @param effectID	効果ID。
	 * @return			存在していればtrueを返します。
	 */
	public boolean hasEffectID( int effectID ) {

		for( int e : EffectIDList )
			if ( e == effectID ) return true;

		return false;
	}


	/**
	 * 強化レベルを取得します。
	 * @param effectID	効果ID。
	 * @return			強化レベル。存在しなければ-1を返します。
	 */
	public int getAmplifier( int effectID ) {

		for( int i = 0; i < EffectIDList.size(); i ++ )
			if ( EffectIDList.get( i ) == effectID )
				return AmplifierList.get( i );

		return -1;
	}


	/**
	 * 効果時間を取得します。
	 * @param effectID	効果ID。
	 * @return			効果時間。存在しなければ-1を返します。
	 */
	public int getDuration( int effectID ) {

		for( int i = 0; i < EffectIDList.size(); i ++ )
			if ( EffectIDList.get( i ) == effectID )
				return DurationList.get( i );

		return -1;
	}


	/**
	 * 確率を取得します。
	 * @param effectID	効果ID。
	 * @return			確率。存在しなければ-1.0Fを返します。
	 */
	public float getProbability( int effectID ) {

		for( int i = 0; i < EffectIDList.size(); i ++ )
			if ( EffectIDList.get( i ) == effectID )
				return ProbabilityList.get( i );

		return -1.0F;
	}

}
