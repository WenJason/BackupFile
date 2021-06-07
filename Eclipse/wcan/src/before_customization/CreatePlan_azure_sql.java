package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_azure_sql extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("conditional-access-configure.md");
		uselessFileList.add("connect-query-r.md");
		uselessFileList.add("elastic-jobs-migrate.md");
		uselessFileList.add("gateway-migration.md");
//		uselessFileList.add("private-endpoint-overview.md");
		uselessFileList.add("quota-increase-request.md");
		uselessFileList.add("reserved-capacity-overview.md");
		uselessFileList.add("saas-dbpertenant-dr-geo-replication.md");
		uselessFileList.add("saas-dbpertenant-dr-geo-restore.md");
		uselessFileList.add("saas-dbpertenant-get-started-deploy.md");
		uselessFileList.add("saas-dbpertenant-log-analytics.md");
		uselessFileList.add("saas-dbpertenant-performance-monitoring.md");
		uselessFileList.add("saas-dbpertenant-provision-and-catalog.md");
		uselessFileList.add("saas-dbpertenant-restore-single-tenant.md");
		uselessFileList.add("saas-dbpertenant-wingtip-app-overview.md");
		uselessFileList.add("saas-multitenantdb-adhoc-reporting.md");
		uselessFileList.add("saas-multitenantdb-get-started-deploy.md");
		uselessFileList.add("saas-multitenantdb-performance-monitoring.md");
		uselessFileList.add("saas-multitenantdb-provision-and-catalog.md");
		uselessFileList.add("saas-multitenantdb-schema-management.md");
		uselessFileList.add("saas-multitenantdb-tenant-analytics.md");
		uselessFileList.add("saas-standaloneapp-get-started-deploy.md");
		uselessFileList.add("saas-standaloneapp-provision-and-catalog.md");
		uselessFileList.add("saas-tenancy-app-design-patterns.md");
		uselessFileList.add("saas-tenancy-cross-tenant-reporting.md");
		uselessFileList.add("saas-tenancy-elastic-tools-multi-tenant-row-level-security.md");
		uselessFileList.add("saas-tenancy-schema-management.md");
		uselessFileList.add("saas-tenancy-tenant-analytics.md");
		uselessFileList.add("saas-tenancy-tenant-analytics-adf.md");
		uselessFileList.add("saas-tenancy-video-index-wingtip-brk3120-20171011.md");
		uselessFileList.add("saas-tenancy-welcome-wingtip-tickets-app.md");
		uselessFileList.add("saas-tenancy-wingtip-app-guidance-tips.md");
//		uselessFileList.add("security-baseline.md");
//		uselessFileList.add("sql-data-sync-monitor-sync.md");
		uselessFileList.add("stream-data-stream-analytics-integration.md");
		uselessFileList.add("instance-pools-configure.md");
	    uselessFileList.add("instance-pools-overview.md");
		uselessFileList.add("machine-learning-services-differences.md");
		uselessFileList.add("machine-learning-services-overview.md");

	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
