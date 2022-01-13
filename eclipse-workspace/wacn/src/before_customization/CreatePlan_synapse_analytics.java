package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_synapse_analytics extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("manage-compute-with-azure-functions.md");
		uselessFileList.add("sql-data-warehouse-get-started-analyze-with-azure-machine-learning.md");
		uselessFileList.add("sql-data-warehouse-get-started-create-support-ticket.md");
		uselessFileList.add("sql-data-warehouse-get-started-visualize-with-power-bi.md");
		uselessFileList.add("sql-data-warehouse-load-from-azure-data-lake-store.md");
		uselessFileList.add("sql-data-warehouse-partner-business-intelligence.md");
		uselessFileList.add("sql-data-warehouse-partner-data-integration.md");
		uselessFileList.add("sql-data-warehouse-partner-data-management.md");
		uselessFileList.add("sql-data-warehouse-videos.md");
		uselessFileList.add("striim-quickstart.md");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
