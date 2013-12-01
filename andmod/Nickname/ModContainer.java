package andmod.Nickname;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class ModContainer extends DummyModContainer {

	public ModContainer() {
		super( new ModMetadata() );
		
		ModMetadata meta = getMetadata();
		
		meta.modId = "AndMod_Nickname";
		meta.name = "Nickname";
		meta.version = "1.6.2.0";
		this.setEnabledState( true );
	}

	
	@Override
	public boolean registerBus( EventBus bus, LoadController controller ) {
	
		bus.register( this );
		return true;
	}

	
}
