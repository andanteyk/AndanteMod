package andmod.ExCompass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class Item_CompassEnder extends Item_Compass {

	protected final int maxtick = 20 * 3;
	protected final double teleportRangeH = 256.0;
	protected final double teleportRangeV = 16.0;

	public Item_CompassEnder( int itemID, int durability ) {
		super( itemID );
		setMaxDamage( durability );
	}


	@Override
	public int getMaxItemUseDuration( ItemStack items ) {
		return 72000;
	}


	@Override
	public EnumAction getItemUseAction( ItemStack items ) {
		return EnumAction.block;
	}



	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		NBTTagCompound nbt = items.getTagCompound();

		if ( !world.isRemote && eplayer.isSneaking() ) {

			if ( nbt == null ) {
				nbt = new NBTTagCompound();
				items.setTagCompound(nbt);
				initNBT( nbt );
			}

			setNBT( nbt, (int)Math.floor( eplayer.posX ), (int)Math.floor( eplayer.posY ), (int)Math.floor( eplayer.posZ ), eplayer.worldObj.provider.dimensionId );


		} else
			eplayer.setItemInUse( items, getMaxItemUseDuration( items ) );

		return items;
	}


	@Override
	public void onPlayerStoppedUsing( ItemStack items, World world, EntityPlayer eplayer, int tick ) {

		if ( !world.isRemote && getMaxItemUseDuration( items ) - tick >= maxtick ) {

			NBTTagCompound nbt = items.getTagCompound();

			
			if ( nbt == null || !isSameWorld( items, world ) )
				doTeleportation( eplayer,
						(int)Math.round( eplayer.posX + ( itemRand.nextDouble() - 0.5  ) * teleportRangeH ),
						(int)Math.round( eplayer.posY + ( itemRand.nextDouble() - 0.25 ) * teleportRangeV ),
						(int)Math.round( eplayer.posZ + ( itemRand.nextDouble() - 0.5  ) * teleportRangeH ), 4 );
			else {
				doTeleportation( eplayer, nbt.getInteger("px"), nbt.getInteger("py"), nbt.getInteger("pz"), 0 );
				items.damageItem( 1, eplayer );
			}

		}

	}


	@Override
	public void onUsingItemTick( ItemStack items, EntityPlayer eplayer, int tick ) {

		//eplayer.worldObj.spawnParticle("portal", eplayer.posX, eplayer.posY + itemRand.nextDouble() * 2.0D, eplayer.posZ, itemRand.nextGaussian(), 0.0D, itemRand.nextGaussian());
		for ( int i = 0; i < 4; i ++ )
			eplayer.worldObj.spawnParticle( "portal", eplayer.posX + ( eplayer.worldObj.rand.nextDouble() - 0.5D ) * eplayer.width, eplayer.posY + eplayer.worldObj.rand.nextDouble() * eplayer.height - 0.25D, eplayer.posZ + ( eplayer.worldObj.rand.nextDouble() - 0.5D ) * eplayer.width, ( eplayer.worldObj.rand.nextDouble() - 0.5D ) * 2.0D, -eplayer.worldObj.rand.nextDouble(), ( eplayer.worldObj.rand.nextDouble() - 0.5D ) * 2.0D );


		if ( getMaxItemUseDuration( items ) - tick >= maxtick )
			eplayer.stopUsingItem();

	}


	/**
	 * テレポートを実行します。
	 * @param eliv		転移対象。
	 * @param px		転移先座標（X）。
	 * @param py		転移先座標（Y）。
	 * @param pz		転移先座標（Z）。
	 * @param damage	転移時に受けるダメージ。
	 */
	protected void doTeleportation ( EntityLivingBase eliv, int px, int py, int pz, int damage ) {

		//teleport code from ender pearl
		double prevx = eliv.posX, prevy = eliv.posY, prevz = eliv.posZ;

		for ( int i = 0; i < 16; i ++ )
			eliv.worldObj.spawnParticle( "portal", eliv.posX + ( eliv.worldObj.rand.nextDouble() - 0.5D ) * eliv.width, eliv.posY + eliv.worldObj.rand.nextDouble() * eliv.height - 0.25D, eliv.posZ + ( eliv.worldObj.rand.nextDouble() - 0.5D ) * eliv.width, ( eliv.worldObj.rand.nextDouble() - 0.5D ) * 2.0D, -eliv.worldObj.rand.nextDouble(), ( eliv.worldObj.rand.nextDouble() - 0.5D ) * 2.0D );


		EnderTeleportEvent event = new EnderTeleportEvent( eliv, px + 0.5, py + 0.5, pz + 0.5, damage );
		if ( !MinecraftForge.EVENT_BUS.post( event ) ){
			eliv.setPositionAndUpdate( event.targetX, event.targetY, event.targetZ );
			eliv.fallDistance = 0.0F;
			if ( event.attackDamage > 0 )
				eliv.attackEntityFrom( DamageSource.fall, event.attackDamage );
		}


		int partnum = 128;
		for ( int i = 0; i < partnum; i ++ ) {
            double d6 = i / ( partnum - 1.0D );
            float f = ( eliv.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
            float f1 = (eliv.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
            float f2 = (eliv.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
            double d7 = prevx + ( eliv.posX - prevx ) * d6 + (eliv.worldObj.rand.nextDouble() - 0.5D) * eliv.width * 2.0D;
            double d8 = prevy + ( eliv.posY - prevy ) * d6 + eliv.worldObj.rand.nextDouble() * eliv.height;
            double d9 = prevz + ( eliv.posZ - prevz ) * d6 + (eliv.worldObj.rand.nextDouble() - 0.5D) * eliv.width * 2.0D;
            eliv.worldObj.spawnParticle("portal", d7, d8, d9, f, f1, f2);
        }

		eliv.worldObj.playSoundEffect( prevx, prevy, prevz, "mob.endermen.portal", 1.0F, 1.0F );
		eliv.worldObj.playSoundEffect( eliv.posX, eliv.posY, eliv.posZ, "mob.endermen.portal", 1.0F, 1.0F );
		//eliv.playSound( "mob.endermen.portal", 1.0F, 1.0F );

	}


	@Override
	public boolean onLeftClickEntity( ItemStack items, EntityPlayer eplayer, Entity etarget ) {

		if ( !eplayer.worldObj.isRemote ) {

			NBTTagCompound nbt = items.getTagCompound();

			if ( etarget instanceof EntityLivingBase && nbt != null && isSameWorld( items, eplayer.worldObj ) ) {

				doTeleportation( (EntityLivingBase)etarget, nbt.getInteger("px"), nbt.getInteger("py"), nbt.getInteger("pz"), 0 );

				items.damageItem( 1, eplayer );

				if ( items.stackSize <= 0 )
					eplayer.destroyCurrentEquippedItem();


				return true;

			}

		}

		return false;
	}





}
