package andmod.Personality;

import java.util.ArrayList;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class Event_Naming {
	
	public ArrayList<String> nameList;
	public boolean alwaysRenderNameTag;
	
	public Event_Naming() {
		nameList = new ArrayList<String>();
		alwaysRenderNameTag = false;
	}

	
	public void addName( String name ) {
		nameList.add( name );
	}
	
	
	
	@ForgeSubscribe
	public void onLivingSpawn( LivingSpawnEvent e ) {
		
		if ( e.entityLiving instanceof EntityLiving ) {
			
			EntityLiving eliv = (EntityLiving)e.entityLiving;
			
			if ( !eliv.hasCustomNameTag() ) {
				eliv.setCustomNameTag( nameList.get( e.world.rand.nextInt( nameList.size() ) ) );
				eliv.setAlwaysRenderNameTag( alwaysRenderNameTag );
			}
			
		} 
	}
}
