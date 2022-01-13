package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_azure_signalr extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
//		uselessFileList.add("howto-network-access-control.md");
//		uselessFileList.add("howto-private-endpoints.md");
		uselessFileList.add("howto-service-tags.md");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
