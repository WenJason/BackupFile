package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_data_factory extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("compare-versions.md");
//		uselessFileList.add("concepts-data-flow-column-pattern.md");
//		uselessFileList.add("concepts-data-flow-debug-mode.md");
//		uselessFileList.add("concepts-data-flow-expression-builder.md");
//		uselessFileList.add("concepts-data-flow-manage-graph.md");
//		uselessFileList.add("concepts-data-flow-monitoring.md");
//		uselessFileList.add("concepts-data-flow-overview.md");
//		uselessFileList.add("concepts-data-flow-performance.md");
//		uselessFileList.add("concepts-data-flow-schema-drift.md");
		uselessFileList.add("connector-azure-data-lake-store.md");
		uselessFileList.add("continuous-integration-deployment.md");
	//	uselessFileList.add("control-flow-execute-data-flow-activity.md");
//		uselessFileList.add("data-flow-aggregate.md");
	//	uselessFileList.add("data-flow-alter-row.md");
	//	uselessFileList.add("data-flow-conditional-split.md");
	//	uselessFileList.add("data-flow-create.md");
	//	uselessFileList.add("data-flow-derived-column.md");
	//	uselessFileList.add("data-flow-exists.md");
	//	uselessFileList.add("data-flow-expression-functions.md");
     //	uselessFileList.add("data-flow-filter.md");
	//	uselessFileList.add("data-flow-flatten.md");
	//	uselessFileList.add("data-flow-lookup.md");
	//	uselessFileList.add("data-flow-new-branch.md");
	//	uselessFileList.add("data-flow-pivot.md");
	//	uselessFileList.add("data-flow-script.md");
	//	uselessFileList.add("data-flow-sink.md");
	//	uselessFileList.add("data-flow-sort.md");
	//	uselessFileList.add("data-flow-source.md");
	//	uselessFileList.add("data-flow-surrogate-key.md");
	//	uselessFileList.add("data-flow-transformation-overview.md");
	//	uselessFileList.add("data-flow-troubleshoot-guide.md");
		uselessFileList.add("data-flow-tutorials.md");
	//	uselessFileList.add("data-flow-union.md");
	//	uselessFileList.add("data-flow-unpivot.md");
	//	uselessFileList.add("data-flow-window.md");
	//	uselessFileList.add("how-to-data-flow-error-rows.md");
//		uselessFileList.add("how-to-fixed-width.md");
		uselessFileList.add("how-to-invoke-ssis-package-ssdt.md");
	//	uselessFileList.add("how-to-sqldb-to-cosmosdb.md");
		uselessFileList.add("how-to-use-azure-key-vault-secrets-pipeline-activities.md");
		uselessFileList.add("lab-data-flow-data-share.md");
		uselessFileList.add("load-azure-data-lake-storage-gen2-from-gen1.md");
		uselessFileList.add("load-azure-data-lake-store.md");
//		uselessFileList.add("parameters-data-flow.md");
	//	uselessFileList.add("solution-template-databricks-notebook.md");
		uselessFileList.add("source-control.md");
	//	uselessFileList.add("transform-data-databricks-jar.md");
	//	uselessFileList.add("transform-data-databricks-notebook.md");
	//	uselessFileList.add("transform-data-databricks-python.md");
		uselessFileList.add("transform-data-machine-learning-service.md");
	//	uselessFileList.add("transform-data-using-databricks-notebook.md");
		uselessFileList.add("transform-data-using-data-lake-analytics.md");
		uselessFileList.add("transform-data-using-machine-learning.md");
	//	uselessFileList.add("tutorial-data-flow.md");
		uselessFileList.add("update-machine-learning-models.md");
	//	uselessFileList.add("wrangling-data-flow-functions.md");
	//	uselessFileList.add("wrangling-data-flow-overview.md");
	//	uselessFileList.add("wrangling-data-flow-tutorial.md");
//		uselessFileList.add("");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
