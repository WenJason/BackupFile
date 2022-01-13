package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_mariadb extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("concept-reserved-pricing.md");
		uselessFileList.add("concepts-data-access-and-security-threat-protection.md");
	//	uselessFileList.add("concepts-data-access-security-private-link.md");
	//	uselessFileList.add("howto-configure-privatelink-cli.md");
	//	uselessFileList.add("howto-configure-privatelink-portal.md");
		uselessFileList.add("howto-database-threat-protection-portal.md");
		uselessFileList.add("howto-deny-public-network-access.md");
	//	uselessFileList.add("security-baseline.md");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
