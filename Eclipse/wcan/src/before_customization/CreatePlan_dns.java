package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_dns extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{

		uselessFileList.add("private-dns-migration-guide.md");

	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
