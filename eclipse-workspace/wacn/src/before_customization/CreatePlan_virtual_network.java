package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_virtual_network extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
//		uselessFileList.add("move-account.md");
//		uselessFileList.add("modules.md");

	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
