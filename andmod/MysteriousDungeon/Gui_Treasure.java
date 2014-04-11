package andmod.MysteriousDungeon;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class Gui_Treasure extends GuiContainer {
	
	protected static final ResourceLocation guiImage = new ResourceLocation( "textures/gui/treasure.png" );
	
	public Gui_Treasure( Container par1Container ) {
		super( par1Container );

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
	}

}
