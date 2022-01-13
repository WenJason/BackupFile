package before_customization;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreatePlan {

	public static void main(String[] args) throws IOException {
		
		Map<String, List<FileStatus>> changedListMap = new TreeMap<String, List<FileStatus>>();
		
		changedListMap.put("azure-stack",                 new CreatePlan_azure_stack().getChangedFilesList("azure-stack"));
		
		Help.writeToExcel(Help.EXCLE_FILE_PATH, changedListMap); 
	}
	

}
