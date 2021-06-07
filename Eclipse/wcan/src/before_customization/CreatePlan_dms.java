package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_dms extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
	//	uselessFileList.add("quickstart-create-data-migration-service-hybrid-portal.md");
	//	uselessFileList.add("known-issues-dms-hybrid-mode.md");
	//	uselessFileList.add("tutorial-postgresql-azure-postgresql-online-portal.md");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
