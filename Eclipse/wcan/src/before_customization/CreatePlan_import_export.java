package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_import_export extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{

	//	uselessFileList.add("load-balancer-standard-availability-zones.md");
	//	uselessFileList.add("quickstart-load-balancer-standard-public-template.md");
	//	uselessFileList.add("tutorial-load-balancer-standard-public-zonal-portal.md");
	//	uselessFileList.add("tutorial-load-balancer-standard-public-zone-redundant-portal.md");

	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
