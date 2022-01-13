package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_data_box extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("data-box-disk-contact-microsoft-support.md");
//		uselessFileList.add("TOC.yml");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
