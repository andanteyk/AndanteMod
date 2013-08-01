package andmod.AndCore;

import net.minecraft.entity.Entity;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class Proxy_Client extends Proxy_Common {

	@Override
	public void registerArrowRenderer ( Class<? extends Entity> entity ) {
		RenderingRegistry.registerEntityRenderingHandler(entity, new Render_Arrow());
	}

}
