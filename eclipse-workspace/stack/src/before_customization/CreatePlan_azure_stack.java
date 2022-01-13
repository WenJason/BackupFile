package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_azure_stack extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		//uselessFileList.add("dns-alias.md");

	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
