package andmod.Nickname;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"andmod.Nickname"})
public class AndMod_Nickname implements IFMLLoadingPlugin {

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	//改変に使用するクラスの完全名(の配列)
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{ "andmod.Nickname.Transformer_EntityPlayer" };
	}

	//mod情報のクラス名
	@Override
	public String getModContainerClass() {
		return "andmod.Nickname.ModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

}
