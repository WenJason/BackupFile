package before_customization;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreatePlan {

	public static void main(String[] args) throws IOException {
		
		Map<String, List<FileStatus>> changedListMap = new TreeMap<String, List<FileStatus>>();
		
		changedListMap.put("storage",                    new CreatePlan_storage().getChangedFilesList("storage"));
		changedListMap.put("mysql",                 new CreatePlan_mysql().getChangedFilesList("mysql"));
		changedListMap.put("automation",                 new CreatePlan_automation().getChangedFilesList("automation"));
		changedListMap.put("dms",      new CreatePlan_dms().getChangedFilesList("dms"));
		changedListMap.put("data-factory",      new CreatePlan_data_factory().getChangedFilesList("data-factory"));
	    changedListMap.put("dns",      new CreatePlan_dns().getChangedFilesList("dns"));
		changedListMap.put("media-services",      new CreatePlan_media_services().getChangedFilesList("media-services"));
		changedListMap.put("azure-sql",      new CreatePlan_azure_sql().getChangedFilesList("azure-sql"));
		changedListMap.put("import-export",      new CreatePlan_import_export().getChangedFilesList("import-export"));
		changedListMap.put("vpn-gateway",      new CreatePlan_vpn_gateway().getChangedFilesList("vpn-gateway"));
		changedListMap.put("scheduler",      new CreatePlan_scheduler().getChangedFilesList("scheduler"));
		changedListMap.put("synapse-analytics",      new CreatePlan_synapse_analytics().getChangedFilesList("synapse-analytics"));
		changedListMap.put("mariadb",      new CreatePlan_mariadb().getChangedFilesList("mariadb"));
		changedListMap.put("databox",      new CreatePlan_data_box().getChangedFilesList("databox"));
		changedListMap.put("postgresql",      new CreatePlan_postgresql().getChangedFilesList("postgresql"));
		changedListMap.put("azure-signalr",      new CreatePlan_azure_signalr().getChangedFilesList("azure-signalr"));
		
		Help.writeToExcel(Help.EXCLE_FILE_PATH, changedListMap); 
	}
	

}
