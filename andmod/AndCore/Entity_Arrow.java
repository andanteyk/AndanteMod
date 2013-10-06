package andmod.AndCore;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Entity_Arrow extends EntityArrow implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile;
	private int inData;
	private boolean inGround;

	//public int canBePickedUp;
	//public int arrowShake;
	//public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;

	private double knockbackStrength;

	protected final Struct_Arrow defaultArrow = new Struct_Arrow( Item.arrow.itemID, 1.0, 2.0, 0.0, 0.05, "arrow" );



	//基幹システム(?)から呼ばれるようです
	public Entity_Arrow( World world ) {
		super( world );
		renderDistanceWeight = 10.0D;
		setSize( 0.5F, 0.5F );
	}

	//ディスペンサーから呼ばれます
	public Entity_Arrow( World world, double x, double y, double z, int arrowID ) {
		super( world );
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		setPosition( x, y, z );
		yOffset = 0.0F;

		setBowID( (short)-1 );
		setArrowID( (short)arrowID );
		if ( ( getArrowProperty().hasEffectID( Struct_Arrow.EFFECT_FLAME ) ) &&
				( rand.nextFloat() < getArrowProperty().getProbability( Struct_Arrow.EFFECT_FLAME ) ) )
			setFire( 100 );
	}


	/*
	//スケさんですかね？
	public Entity_Arrow( World world, EntityLivingBase attacker, EntityLivingBase defender, float par4, float par5 ) {
		super( world );
		renderDistanceWeight = 10.0D;
		shootingEntity = attacker;

		if ( attacker instanceof EntityPlayer )
			canBePickedUp = 1;


		posY = attacker.posY + attacker.getEyeHeight() - 0.1D;
		double d0 = defender.posX - attacker.posX;
		double d1 = defender.boundingBox.minY + defender.height / 3.0F - posY;
		double d2 = defender.posZ - attacker.posZ;
		double d3 = MathHelper.sqrt_double( d0 * d0 + d2 * d2 );

		if ( d3 >= 1.0E-7D ) {
			float f2 = (float)( Math.atan2(d2, d0) * 180.0D / Math.PI ) - 90.0F;
			float f3 = (float)( -( Math.atan2( d1, d3 ) * 180.0D / Math.PI ) );
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			setLocationAndAngles( attacker.posX + d4, posY, attacker.posZ + d5, f2, f3);
			yOffset = 0.0F;
			float f4 = (float)d3 * 0.2F;
			this.setThrowableHeading( d0, d1 + f4, d2, par4, par5 );
		}

		System.out.println( "Entity_Arrow( World, attacker, defender, float, float ) * it may call by skeleton" );
	}
	 */



	/*
	//???
	public Entity_Arrow( World world, EntityLivingBase eliv, float speed ) {
		super( world );
		renderDistanceWeight = 10.0D;
		shootingEntity = eliv;

		if ( eliv instanceof EntityPlayer )
			canBePickedUp = 1;


		setSize( 0.5F, 0.5F );
		setLocationAndAngles( eliv.posX, eliv.posY + eliv.getEyeHeight(), eliv.posZ, eliv.rotationYaw, eliv.rotationPitch );
		posX -= MathHelper.cos( rotationYaw / 180.0F * (float)Math.PI ) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin( rotationYaw / 180.0F * (float)Math.PI ) * 0.16F;
		setPosition( posX, posY, posZ );
		yOffset = 0.0F;
		motionX = -MathHelper.sin( rotationYaw / 180.0F * (float)Math.PI ) * MathHelper.cos( rotationPitch / 180.0F * (float)Math.PI );
		motionZ = MathHelper.cos( rotationYaw / 180.0F * (float)Math.PI ) * MathHelper.cos( rotationPitch / 180.0F * (float)Math.PI );
		motionY = ( -MathHelper.sin( rotationPitch / 180.0F * (float)Math.PI ) );
		this.setThrowableHeading( motionX, motionY, motionZ, speed * 1.5F, 1.0F );

		System.out.println( "Entity_Arrow( World, eliv, speed )" );
	}
	 */


	//自作弓からの発射用
	public Entity_Arrow( World world, EntityLivingBase eliv, double speed, int bowID, int arrowID ) {
		super( world );
		renderDistanceWeight = 10.0D;
		shootingEntity = eliv;

		if ( eliv instanceof EntityPlayer )
			canBePickedUp = 1;


		setSize( 0.5F, 0.5F );
		setLocationAndAngles( eliv.posX, eliv.posY + eliv.getEyeHeight(), eliv.posZ, eliv.rotationYaw, eliv.rotationPitch );
		posX -= MathHelper.cos( rotationYaw / 180.0F * (float)Math.PI ) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin( rotationYaw / 180.0F * (float)Math.PI ) * 0.16F;
		setPosition( posX, posY, posZ );
		yOffset = 0.0F;
		motionX = -MathHelper.sin( rotationYaw / 180.0F * (float)Math.PI ) * MathHelper.cos( rotationPitch / 180.0F * (float)Math.PI );
		motionZ = MathHelper.cos( rotationYaw / 180.0F * (float)Math.PI ) * MathHelper.cos( rotationPitch / 180.0F * (float)Math.PI );
		motionY = ( -MathHelper.sin( rotationPitch / 180.0F * (float)Math.PI ) );
		this.setThrowableHeading( motionX, motionY, motionZ, speed * 1.5, 1.0F );

		//todo: set item ID, ignition
		setBowID( (short)bowID );
		setArrowID( (short)arrowID );
		if ( ( getArrowProperty().hasEffectID( Struct_Arrow.EFFECT_FLAME ) || getBowProperty().hasEffectID( Struct_Arrow.EFFECT_FLAME ) ) &&
				( rand.nextFloat() < getArrowProperty().getProbability( Struct_Arrow.EFFECT_FLAME ) || rand.nextFloat() < getBowProperty().getProbability( Struct_Arrow.EFFECT_FLAME ) ) )
			setFire( 100 );
	}


	@Override
	protected void entityInit() {
		dataWatcher.addObject( 16, Byte.valueOf( (byte) 0 ) );			//isCritical
		dataWatcher.addObject( 17, Short.valueOf( (short) -1 ) );		//bowID
		dataWatcher.addObject( 18, Short.valueOf( (short) -1 ) );		//arrowID
	}


	/**
	 * 矢の向きを設定します。
	 * @param vx		X座標の速さ。
	 * @param vy		Y座標の速さ。
	 * @param vz		Z座標の速さ。
	 * @param speed		速度係数。
	 * @param wobbling	ブレ係数。
	 */
	public void setThrowableHeading( double vx, double vy, double vz, double speed, double wobbling ) {
		double baseSpeed = Math.sqrt( vx * vx + vy * vy + vz * vz );
		vx /= baseSpeed;
		vy /= baseSpeed;
		vz /= baseSpeed;
		vx += rand.nextGaussian() * ( rand.nextBoolean() ? -1 : 1 ) * 0.0075D * wobbling;
		vy += rand.nextGaussian() * ( rand.nextBoolean() ? -1 : 1 ) * 0.0075D * wobbling;
		vz += rand.nextGaussian() * ( rand.nextBoolean() ? -1 : 1 ) * 0.0075D * wobbling;
		vx *= speed;
		vy *= speed;
		vz *= speed;
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		double horizontalSpeed = Math.sqrt( vx * vx + vz * vz );
		prevRotationYaw = rotationYaw = (float)( Math.atan2( vx, vz ) * 180.0D / Math.PI );
		prevRotationPitch = rotationPitch = (float)( Math.atan2( vy, horizontalSpeed ) * 180.0D / Math.PI );
		ticksInGround = 0;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2( double px, double py, double pz, float yaw, float pitch, int par9 ) {
		setPosition( px, py, pz );
		setRotation( yaw, pitch );
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity( double vx, double vy, double vz ) {
		motionX = vx;
		motionY = vy;
		motionZ = vz;

		if ( prevRotationPitch == 0.0F && prevRotationYaw == 0.0F ) {
			double f = MathHelper.sqrt_double( vx * vx + vz * vz);
			prevRotationYaw = rotationYaw = (float)( Math.atan2( vx, vz ) * 180.0D / Math.PI );
			prevRotationPitch = rotationPitch = (float)( Math.atan2( vy, f ) * 180.0D / Math.PI );
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles( posX, posY, posZ, rotationYaw, rotationPitch );
			ticksInGround = 0;
		}
	}


	@Override
	public void onUpdate() {
		onEntityUpdate();
		
		if ( prevRotationPitch == 0.0F && prevRotationYaw == 0.0F ) {
			float f = MathHelper.sqrt_double( motionX * motionX + motionZ * motionZ );
			prevRotationYaw = rotationYaw = (float)( Math.atan2( motionX, motionZ ) * 180.0D / Math.PI );
			prevRotationPitch = rotationPitch = (float)( Math.atan2( motionY, f ) * 180.0D / Math.PI );
		}

		int blockID = worldObj.getBlockId( xTile, yTile, zTile );

		if ( blockID > 0 ) {
			Block.blocksList[blockID].setBlockBoundsBasedOnState( worldObj, xTile, yTile, zTile );
			AxisAlignedBB axisalignedbb = Block.blocksList[blockID].getCollisionBoundingBoxFromPool( worldObj, xTile, yTile, zTile );

			if ( axisalignedbb != null && axisalignedbb.isVecInside( worldObj.getWorldVec3Pool().getVecFromPool( posX, posY, posZ ) ) )
				inGround = true;
		}

		if ( arrowShake > 0 )
			arrowShake --;


		if ( inGround ) {
			int blockID2 = worldObj.getBlockId( xTile, yTile, zTile );		//debug:これってblockIDと同じになる気がするのですが
			int meta = worldObj.getBlockMetadata( xTile, yTile, zTile );

			if ( blockID2 == inTile && meta == inData ) {
				ticksInGround ++;

				if ( ticksInGround >= 1200 ) setDead();


			} else {
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
			}

		} else if ( ticksInAir >= 2000 )
			//note: 重力値を 0 に設定したり超高速で撃ち出したりした場合、地面に刺さらず削除されないことがあります。そのため、空中で100秒経過したら削除するようにしました。
			setDead();

		else {
			//if ( getArrowID() == 0 ) setDead();

			ticksInAir ++;
			Vec3 vec3 = worldObj.getWorldVec3Pool().getVecFromPool( posX, posY, posZ );
			Vec3 vec31 = worldObj.getWorldVec3Pool().getVecFromPool( posX + motionX, posY + motionY, posZ + motionZ );
			MovingObjectPosition mop = worldObj.rayTraceBlocks_do_do( vec3, vec31, false, true );
			vec3 = worldObj.getWorldVec3Pool().getVecFromPool( posX, posY, posZ );
			vec31 = worldObj.getWorldVec3Pool().getVecFromPool( posX + motionX, posY + motionY, posZ + motionZ );

			if ( mop != null )
				vec31 = worldObj.getWorldVec3Pool().getVecFromPool( mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord );

			Entity entity = null;
			List list = worldObj.getEntitiesWithinAABBExcludingEntity( this, boundingBox.addCoord( motionX, motionY, motionZ ).expand( 1.0D, 1.0D, 1.0D ) );
			double d0 = 0.0D;
			int l;
			float f1;

			for ( l = 0; l < list.size(); l ++ ) {
				Entity entity1 = (Entity)list.get( l );

				if ( entity1.canBeCollidedWith() && ( entity1 != shootingEntity || ticksInAir >= 5 ) ) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand( f1, f1, f1 );
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept( vec3, vec31 );

					if ( movingobjectposition1 != null ) {
						double d1 = vec3.distanceTo( movingobjectposition1.hitVec );

						if ( d1 < d0 || d0 == 0.0D ) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if ( entity != null )
				mop = new MovingObjectPosition( entity );

			if ( mop != null && mop.entityHit != null && mop.entityHit instanceof EntityPlayer ) {
				EntityPlayer entityplayer = (EntityPlayer)mop.entityHit;

				if ( entityplayer.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !( (EntityPlayer)shootingEntity ).func_96122_a( entityplayer ) )
					mop = null;
			}

			double speed;
			float f3;

			if ( mop != null )
				if ( mop.entityHit != null ) {
					speed = MathHelper.sqrt_double( motionX * motionX + motionY * motionY + motionZ * motionZ );
					double dmg = ( speed * damage );		//point: ダメージの設定
					boolean hitEnder =  mop.entityHit instanceof EntityEnderman   && ( getArrowProperty().hasEffectID( Struct_Arrow.EFFECT_ENDERSTRIKE ) || getBowProperty().hasEffectID( Struct_Arrow.EFFECT_ENDERSTRIKE ) );
					boolean canHit = !( mop.entityHit instanceof EntityEnderman ) || ( getArrowProperty().hasEffectID( Struct_Arrow.EFFECT_ENDERSTRIKE ) || getBowProperty().hasEffectID( Struct_Arrow.EFFECT_ENDERSTRIKE ) );
					boolean isAssassination = getArrowProperty().hasEffectID( Struct_Arrow.EFFECT_ASSASSINATION ) || getBowProperty().hasEffectID( Struct_Arrow.EFFECT_ASSASSINATION );
					
					if ( getIsCritical() )
						dmg += rand.nextDouble() * ( dmg / 2.0 + 2.0 );

					if ( mop.entityHit instanceof EntityLivingBase ) {
						dmg += applyEnhancedDamage( (EntityLivingBase)mop.entityHit, getArrowProperty() );
						dmg += applyEnhancedDamage( (EntityLivingBase)mop.entityHit, getBowProperty() );
					}


					//debug
					/*
					if ( ! worldObj.isRemote )
						System.out.println( String.format( "[EntityArrow] %1$.2f damage", dmg ) );
					 */

					//§ エンダーマンにも当たるように処理　「弓矢属性の直接攻撃ダメージ」として処理しています
					DamageSource damagesource = null;

					if ( shootingEntity == null || isAssassination )
						if ( hitEnder )
							damagesource = new EntityDamageSource( "arrow", this );
						else
							damagesource = DamageSource.causeArrowDamage( this, this );
					else if ( hitEnder )
						damagesource = new EntityDamageSource( "arrow", shootingEntity );
					else
						damagesource = DamageSource.causeArrowDamage( this, shootingEntity );


					if ( isBurning() && canHit )
						mop.entityHit.setFire( 5 );

					if ( mop.entityHit.attackEntityFrom( damagesource, (float)dmg ) ) {
						if ( mop.entityHit instanceof EntityLivingBase ) {			//point:エンティティに当たった

							EntityLivingBase eliv = (EntityLivingBase)mop.entityHit;

							if ( !worldObj.isRemote )
								eliv.setArrowCountInEntity( eliv.getArrowCountInEntity() + 1 );

							if ( knockbackStrength != 0.0 )	{	//point: ノックバック
								f3 = MathHelper.sqrt_double( motionX * motionX + motionZ * motionZ );

								if ( f3 > 0.0F )
									mop.entityHit.addVelocity( motionX * knockbackStrength * 0.6D / f3, 0.1D, motionZ * knockbackStrength * 0.6D / f3 );
							}


							if ( canHit && !worldObj.isRemote ) {
								applyEffect( (EntityLivingBase)mop.entityHit, getArrowProperty() );
								applyEffect( (EntityLivingBase)mop.entityHit, getBowProperty() );
							}


							if ( shootingEntity != null )
								EnchantmentThorns.func_92096_a( shootingEntity, eliv, rand );

							if ( shootingEntity != null && mop.entityHit != shootingEntity && mop.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP )
								( (EntityPlayerMP)shootingEntity ).playerNetServerHandler.sendPacketToPlayer( new Packet70GameEvent( 6, 0 ) );
						}


						if ( canHit ) {
							playSound( "random.bowhit", 1.0F, 1.2F / ( rand.nextFloat() * 0.2F + 0.9F ) );
							setDead();
						}


					} else {	//当たらなかった・無効化されたら
						motionX *= -0.1D;
						motionY *= -0.1D;
						motionZ *= -0.1D;
						rotationYaw += 180.0F;
						prevRotationYaw += 180.0F;
						ticksInAir = 0;
					}


				} else {

					xTile = mop.blockX;
					yTile = mop.blockY;
					zTile = mop.blockZ;
					inTile = worldObj.getBlockId( xTile, yTile, zTile );
					inData = worldObj.getBlockMetadata( xTile, yTile, zTile );
					motionX = ( (float)( mop.hitVec.xCoord - posX ) );
					motionY = ( (float)( mop.hitVec.yCoord - posY ) );
					motionZ = ( (float)( mop.hitVec.zCoord - posZ ) );
					speed = MathHelper.sqrt_double( motionX * motionX + motionY * motionY + motionZ * motionZ );
					posX -= motionX / speed * 0.05D;
					posY -= motionY / speed * 0.05D;
					posZ -= motionZ / speed * 0.05D;
					playSound( "random.bowhit", 1.0F, 1.2F / ( rand.nextFloat() * 0.2F + 0.9F ) );
					inGround = true;
					arrowShake = 7;
					setIsCritical( false );

					if ( inTile != 0 )
						Block.blocksList[inTile].onEntityCollidedWithBlock( worldObj, xTile, yTile, zTile, this );

					//point: 着弾時の効果
					if ( !worldObj.isRemote ) {
						boolean flag1 = onHitGround( mop.sideHit, getArrowProperty() );
						boolean flag2 = onHitGround( mop.sideHit, getBowProperty() );
						if ( flag1 || flag2 )
							setDead();
					}

					//checkme: 火矢を消すためのコードです。位置があっているかどうか、確認してください
					if ( isBurning() && isInFire( xTile, yTile, zTile, mop.sideHit ) )
						setDead();

				}

			if ( getIsCritical() )
				for ( l = 0; l < 4; l++ )
					worldObj.spawnParticle( "crit", posX + motionX * l / 4.0D, posY + motionY * l / 4.0D, posZ + motionZ * l / 4.0D, -motionX, -motionY + 0.2D, -motionZ );

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			speed = MathHelper.sqrt_double( motionX * motionX + motionZ * motionZ );
			rotationYaw = (float)( Math.atan2( motionX, motionZ ) * 180.0D / Math.PI );

			for ( rotationPitch = (float)( Math.atan2( motionY, speed ) * 180.0D / Math.PI ); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F );

			while ( rotationPitch - prevRotationPitch >= 180.0F ) prevRotationPitch += 360.0F;
			while ( rotationYaw - prevRotationYaw < -180.0F ) prevRotationYaw -= 360.0F;
			while ( rotationYaw - prevRotationYaw >= 180.0F ) prevRotationYaw += 360.0F;

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			double resistance = 0.99;		//（空気）抵抗値
			//f1 = 0.05F;	//gravity

			if ( isInWater() ) {
				for ( int j1 = 0; j1 < 4; j1 ++ ) {
					f3 = 0.25F;
					worldObj.spawnParticle( "bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ );
				}

				resistance = 0.8;	//水中抵抗値
			}

			motionX *= resistance;
			motionY *= resistance;
			motionZ *= resistance;
			motionY -= getArrowProperty().gravity;
			setPosition( posX, posY, posZ );
			doBlockCollisions();
		}
	}




	@Override
	public void readEntityFromNBT( NBTTagCompound nbt ) {
		xTile = nbt.getShort( "xTile" );
		yTile = nbt.getShort( "yTile" );
		zTile = nbt.getShort( "zTile" );
		inTile = nbt.getByte( "inTile" ) & 255;
		inData = nbt.getByte( "inData" ) & 255;
		arrowShake = nbt.getByte( "shake" ) & 255;
		inGround = nbt.getByte( "inGround" ) == 1;

		setBowID( nbt.getShort( "bowID" ) );
		setArrowID( nbt.getShort( "arrowID" ) );

		if ( nbt.hasKey( "damage" ) )
			damage = nbt.getDouble( "damage" );

		if ( nbt.hasKey( "pickup" ) )
			canBePickedUp = nbt.getByte( "pickup" );
		else if ( nbt.hasKey( "player" ) )
			canBePickedUp = nbt.getBoolean( "player" ) ? 1 : 0;
	}


	@Override
	public void writeEntityToNBT( NBTTagCompound nbt ) {
		nbt.setShort( "xTile", (short)xTile );
		nbt.setShort( "yTile", (short)yTile );
		nbt.setShort( "zTile", (short)zTile );
		nbt.setByte( "inTile", (byte)inTile );
		nbt.setByte( "inData", (byte)inData );
		nbt.setByte( "shake", (byte)arrowShake );
		nbt.setByte( "inGround", (byte)( inGround ? 1 : 0 ) );
		nbt.setByte( "pickup", (byte)canBePickedUp );
		nbt.setDouble( "damage", damage );
		nbt.setShort( "bowID", getBowID() );
		nbt.setShort( "arrowID", getArrowID() );
	}




	@Override
	public void onCollideWithPlayer( EntityPlayer eplayer ) {
		if ( !worldObj.isRemote && inGround && arrowShake <= 0 ) {
			boolean flag = canBePickedUp == 1 || canBePickedUp == 2 && eplayer.capabilities.isCreativeMode;

			if ( canBePickedUp == 1 ) {
				if ( Item.itemsList[getArrowID()].getMaxDamage() <= 0 )
					if ( !eplayer.inventory.addItemStackToInventory( new ItemStack( getArrowID(), 1, 0 ) ) )
						flag = false;
					else;
				
				else if ( !eplayer.inventory.addItemStackToInventory( new ItemStack( Item.arrow, 1, 0 ) ) )
					flag = false;
			}

			if ( flag ) {
				playSound( "random.pop", 0.2F, ( ( rand.nextFloat() - rand.nextFloat() ) * 0.7F + 1.0F ) * 2.0F );
				eplayer.onItemPickup( this, 1 );
				setDead();
			}
		}
	}




	@Override
	public void setDamage( double damage ) {
		this.damage = damage;
	}


	@Override
	public double getDamage() {
		return damage;
	}


	//念のため上書きします。
	@Override
	public void setKnockbackStrength( int knockback ) {
		knockbackStrength = knockback;
	}


	public void setKnockbackStrength( double knockback ) {
		knockbackStrength = knockback;
	}




	public short getBowID() {
		return dataWatcher.getWatchableObjectShort( 17 );
	}

	public void setBowID( short id ) {
		dataWatcher.updateObject( 17, Short.valueOf( id ) );
	}


	public short getArrowID() {
		return dataWatcher.getWatchableObjectShort( 18 );
	}

	public void setArrowID( short id ) {
		dataWatcher.updateObject( 18, Short.valueOf( id ) );
	}


	/**
	 * 矢の情報を取得します。
	 * @return	矢の情報。
	 */
	protected Struct_Arrow getArrowProperty() {
		if ( getArrowID() == -1 || !( Item.itemsList[getArrowID()] instanceof Item_Arrow ) ) return defaultArrow;
		return ( (Item_Arrow)Item.itemsList[getArrowID()] ).property;
	}

	/**
	 * 弓の情報を取得します。
	 * @return	弓からの、矢の情報。
	 */
	protected Struct_Arrow getBowProperty() {
		if ( getBowID() == -1 || !( Item.itemsList[getBowID()] instanceof Item_Bow ) ) return defaultArrow;		//指定ミスではありません、念のため
		return ( (Item_Bow)Item.itemsList[getBowID()] ).property;
	}



	/**
	 * 特殊効果によるダメージの増加量を求めます。
	 * @param eliv		対象となる生物。
	 * @param arrow		特殊効果データ。
	 * @return			ダメージの増加量を返します。
	 */
	protected double applyEnhancedDamage( EntityLivingBase eliv, Struct_Arrow arrow ) {

		double damage = 0.0;

		for( int i = 0; i < arrow.EffectIDList.size(); i ++ )
			if ( rand.nextFloat() < arrow.ProbabilityList.get( i ) ) {

				int id = arrow.EffectIDList.get( i );
				int amp = arrow.AmplifierList.get( i );

				if ( id == Struct_Arrow.EFFECT_SHARPNESS )
					damage += amp * 1.25 * rand.nextDouble();

				else if ( id == Struct_Arrow.EFFECT_SMITE && eliv.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD )
					damage += amp * 2.5 * rand.nextDouble();

				else if ( id == Struct_Arrow.EFFECT_BANEOFARTHROPODS && eliv.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD )
					damage += amp * 2.5 * rand.nextDouble();

			}


		//debug:
		//if( damage > 0.0 ) System.out.println( "Entity_Arrow: Enhanced Damage = " + damage );
		return damage;
	}


	/**
	 * 特殊効果を適用します。
	 * @param eliv		適用する生物。
	 * @param arrow		特殊効果データ。	
	 * @return			適用されればtrueを返します。
	 */
	protected boolean applyEffect( EntityLivingBase eliv, Struct_Arrow arrow ) {
		
		boolean ret = false;

		for ( int i = 0; i < arrow.EffectIDList.size(); i ++ )

			if ( rand.nextFloat() < arrow.ProbabilityList.get( i ) ) {
				int id = arrow.EffectIDList.get( i );
				int amp = arrow.AmplifierList.get( i );
				int dur = arrow.DurationList.get( i );

				if ( id < 256 )
					eliv.addPotionEffect( new PotionEffect( id, dur, amp ) );

				else if ( id == Struct_Arrow.EFFECT_LIGHTNING )
					worldObj.addWeatherEffect( new EntityLightningBolt( worldObj, eliv.posX, eliv.posY, eliv.posZ ) );

				else if ( id == Struct_Arrow.EFFECT_EXPLOSION )
					worldObj.createExplosion( this, eliv.posX, eliv.posY, eliv.posZ, amp, true );

				else if ( id == Struct_Arrow.EFFECT_EXPLOSIONWITHFIRE )
					worldObj.newExplosion( this, eliv.posX, eliv.posY, eliv.posZ, amp, true, true );

				else if ( id == Struct_Arrow.EFFECT_EXPLOSIONFIREONLY )
					worldObj.newExplosion( this, eliv.posX, eliv.posY, eliv.posZ, amp, true, false );

				else if ( id == Struct_Arrow.EFFECT_INCONSUMABLE ) {
					float f = 0.7F;
					double dx = rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double dy = rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double dz = rand.nextFloat() * f + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem( worldObj, eliv.posX + dx, eliv.posY + dy, eliv.posZ + dz, new ItemStack( getArrowID(), 1, 0 ) );
					entityitem.delayBeforeCanPickup = 10;
					worldObj.spawnEntityInWorld( entityitem );
				}
				else continue;
				
				ret = true;
			}

		return ret;
	}


	/**
	 * ブロックに刺さった時に特殊効果を適用します。
	 * @param side		刺さった面。
	 * @param arrow		特殊効果。
	 * @return			適用されればtrueを返します。
	 */
	protected boolean onHitGround( int side, Struct_Arrow arrow ) {

		boolean ret = false;
		
		for ( int i = 0; i < arrow.EffectIDList.size(); i ++ )

			if ( rand.nextFloat() < arrow.ProbabilityList.get( i ) ) {
				int id = arrow.EffectIDList.get( i );
				int amp = arrow.AmplifierList.get( i );
				int dur = arrow.DurationList.get( i );

				if ( id == Struct_Arrow.EFFECT_LIGHTNING )
					worldObj.addWeatherEffect( new EntityLightningBolt( worldObj, xTile + 0.5, yTile + 0.5, zTile + 0.5 ) );

				else if ( id == Struct_Arrow.EFFECT_EXPLOSION )
					worldObj.createExplosion( this, xTile + 0.5, yTile + 0.5, zTile + 0.5, amp, true );

				else if ( id == Struct_Arrow.EFFECT_EXPLOSIONWITHFIRE )
					worldObj.newExplosion( this, xTile + 0.5, yTile + 0.5, zTile + 0.5, amp, true, true );

				else if ( id == Struct_Arrow.EFFECT_EXPLOSIONFIREONLY )
					worldObj.newExplosion( this, xTile + 0.5, yTile + 0.5, zTile + 0.5, amp, true, false );

				else if ( id == Struct_Arrow.EFFECT_PLACEBLOCK )
					if ( placeBlock( xTile, yTile, zTile, side, amp & 0xFFF, amp >> 12 ) ) setDead();
					else;

				else continue;
				
				ret = true;
			}


		if ( !isDead && isBurning() ) {
			placeBlock( xTile, yTile, zTile, side, Block.fire.blockID, 0 );
		}
		
		return ret;
	}



	protected boolean placeBlock( int bx, int by, int bz, int side, int id, int meta ) {
		int ax = xTile;
		int ay = yTile;
		int az = zTile;

		switch ( side ) {
		case 0:
			ay--; break;
		case 1:
			ay++; break;
		case 2:
			az--; break;
		case 3:
			az++; break;
		case 4:
			ax--; break;
		case 5:
			ax++; break;
		}

		if ( worldObj.isAirBlock( ax, ay, az ) ) {

			if ( worldObj.setBlock( ax, ay, az, id, meta, 3 ) )
				if ( worldObj.getBlockId( ax, ay, az ) == id )
					//Block.blocksList[id].onBlockPlacedBy( worldObj, ax, ay, az, player, stack);
					Block.blocksList[id].onPostBlockPlaced( worldObj, ax, ay, az, meta );
			return true;
		}

		return false;
	}


	//火の中にいるかどうか判定します。
	protected boolean isInFire( int bx, int by, int bz, int side ) {
		switch ( side ) {
		case 0:
			by--; break;
		case 1:
			by++; break;
		case 2:
			bz--; break;
		case 3:
			bz++; break;
		case 4:
			bx--; break;
		case 5:
			bx++; break;
		}

		return ( worldObj.getBlockId( bx, by, bz ) == Block.fire.blockID );
	}

}
