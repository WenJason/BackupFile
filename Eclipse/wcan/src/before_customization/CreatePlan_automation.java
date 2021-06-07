package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_automation extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("move-account.md");
//		uselessFileList.add("modules.md");
		uselessFileList.add("change-tracking.md");
		uselessFileList.add("collect-data-microsoft-azure-automation-case.md");
//		uselessFileList.add("shared-resources.md");
		uselessFileList.add("start-stop-vm.md");
		uselessFileList.add("automation-config-aws-account.md");
		uselessFileList.add("automation-orchestrator-migration.md");
		uselessFileList.add("automation-scenario-aws-deployment.md");
//		uselessFileList.add("automation-runbook-gallery.md");
//		uselessFileList.add("automation-scenario-aws-deployment.md");
		uselessFileList.add("automation-send-email.md");
		uselessFileList.add("automation-solution-vm-management.md");
		uselessFileList.add("automation-solution-vm-management-config.md");
		uselessFileList.add("automation-solution-vm-management-enable.md");
		uselessFileList.add("automation-solution-vm-management-logs.md");
		uselessFileList.add("automation-tutorial-installed-software.md");
		uselessFileList.add("automation-tutorial-troubleshoot-changes.md");
//		uselessFileList.add("automation-update-azure-modules.md");
		uselessFileList.add("automation-use-azure-ad.md");
//		uselessFileList.add("automation-vm-inventory.md");
		uselessFileList.add("automation-watchers-tutorial.md");
//		uselessFileList.add("change-tracking.md");
//		uselessFileList.add("change-tracking-file-contents.md");
		uselessFileList.add("manage-office-365.md");
		uselessFileList.add("migrate-oms-update-deployments.md");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
