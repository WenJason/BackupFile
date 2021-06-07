package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_scheduler extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
//		uselessFileList.add("cache-best-practices.md");
//		uselessFileList.add("TOC.yml");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
