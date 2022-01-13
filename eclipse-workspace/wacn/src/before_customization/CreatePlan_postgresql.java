package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_postgresql extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("concept-reserved-pricing.md");
	//	uselessFileList.add("concepts-aad-authentication.md");
	//	uselessFileList.add("concepts-data-access-and-security-private-link.md");
		uselessFileList.add("concepts-data-access-and-security-threat-protection.md");
	//	uselessFileList.add("concepts-data-encryption-postgresql.md");
		uselessFileList.add("concepts-hyperscale-backup.md");
		uselessFileList.add("concepts-hyperscale-choose-distribution-column.md");
		uselessFileList.add("concepts-hyperscale-colocation.md");
		uselessFileList.add("concepts-hyperscale-configuration-options.md");
		uselessFileList.add("concepts-hyperscale-distributed-data.md");
		uselessFileList.add("concepts-hyperscale-extensions.md");
		uselessFileList.add("concepts-hyperscale-firewall-rules.md");
		uselessFileList.add("concepts-hyperscale-high-availability.md");
		uselessFileList.add("concepts-hyperscale-monitoring.md");
		uselessFileList.add("concepts-hyperscale-nodes.md");
		uselessFileList.add("concepts-hyperscale-ssl-connection-security.md");
	//	uselessFileList.add("howto-configure-privatelink-cli.md");
	//	uselessFileList.add("howto-configure-privatelink-portal.md");
	//	uselessFileList.add("howto-configure-sign-in-aad-authentication.md");
		uselessFileList.add("howto-database-threat-protection-portal.md");
	//	uselessFileList.add("howto-data-encryption-cli.md");
	//	uselessFileList.add("howto-data-encryption-portal.md");
	//	uselessFileList.add("howto-data-encryption-troubleshoot.md");
	//	uselessFileList.add("howto-data-encryption-validation.md");
		uselessFileList.add("howto-deny-public-network-access.md");
		uselessFileList.add("howto-hyperscale-alert-on-metric.md");
		uselessFileList.add("howto-hyperscale-create-users.md");
		uselessFileList.add("howto-hyperscale-high-availability.md");
		uselessFileList.add("howto-hyperscale-manage-firewall-using-portal.md");
		uselessFileList.add("howto-hyperscale-scaling.md");
		uselessFileList.add("howto-hyperscale-troubleshoot-common-connection-issues.md");
		uselessFileList.add("partners-migration-postgresql.md");
		uselessFileList.add("quickstart-create-hyperscale-portal.md");
	//	uselessFileList.add("security-baseline.md");
		uselessFileList.add("tutorial-design-database-hyperscale-multi-tenant.md");
		uselessFileList.add("tutorial-design-database-hyperscale-realtime.md");

	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
