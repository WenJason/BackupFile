package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_mysql extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("concept-reserved-pricing.md");
	//	uselessFileList.add("concepts-azure-ad-authentication.md");
		uselessFileList.add("concepts-data-access-and-security-threat-protection.md");
	//	uselessFileList.add("concepts-data-access-security-private-link.md");
	//	uselessFileList.add("concepts-data-encryption-mysql.md");
	//	uselessFileList.add("howto-configure-privatelink-cli.md");
	//	uselessFileList.add("howto-configure-privatelink-portal.md");
	//	uselessFileList.add("howto-configure-sign-in-azure-ad-authentication.md");
		uselessFileList.add("howto-database-threat-protection-portal.md");
	//	uselessFileList.add("howto-data-encryption-cli.md");
	//	uselessFileList.add("howto-data-encryption-portal.md");
	//	uselessFileList.add("howto-data-encryption-troubleshoot.md");
	//	uselessFileList.add("howto-data-encryption-validation.md");
		uselessFileList.add("howto-deny-public-network-access.md");
		uselessFileList.add("partners-migration-mysql.md");
	//	uselessFileList.add("security-baseline.md");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	 
	
}
