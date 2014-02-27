package andmod.CommandCard;

import net.minecraft.creativetab.CreativeTabs;


/**
 * Clone of AndCore.Handler_CreativeTab
 * @author Andante
 *
 */
public class Handler_CreativeTab extends CreativeTabs {

	public int iconID;
	public String title;
	
	public Handler_CreativeTab( String label, int iconID, String title ) {
		super( label );
		this.iconID = iconID;
		this.title = title;
	}
	

	@Override
	public String getTranslatedTabLabel() {
		return title;
	}

	
	@Override
	public int getTabIconItemIndex() {
		return iconID;
	}
	

}
