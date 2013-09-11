package andmod.AndCore;

import net.minecraft.creativetab.CreativeTabs;

public class Handler_CreativeTab extends CreativeTabs {
	
	public int iconID;
	public String title;
	
	public Handler_CreativeTab( String label, int iconID, String title ) {
		super( label );
		this.iconID = iconID;
		this.title = title;
		// TODO 自動生成されたコンストラクター・スタブ
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
