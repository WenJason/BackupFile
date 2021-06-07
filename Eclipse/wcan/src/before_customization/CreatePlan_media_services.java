package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_media_services extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
		uselessFileList.add("live-transcription.md");
		uselessFileList.add("media-services-community.md");
		uselessFileList.add("offline-widevine-for-android.md");
		uselessFileList.add("scale-streaming-cdn.md");
		uselessFileList.add("widevine-license-template-overview.md");
		uselessFileList.add("media-services-axinoabs-integration.md");
		uselessFileList.add("media-services-commm-integration.md");
		uselessFileList.add("media-services-castlunity.md");
		uselessFileList.add("media-services-process-content-with-indexer2.md");
		uselessFileList.add("media-services-static-packaging.md");
		uselessFileList.add("media-services-upload-files-from-storsimple.md");
		uselessFileList.add("media-services-widevine-license-template-overview.md");
		uselessFileList.add("migrate-indexer-v1-v2.md");
		uselessFileList.add("offline-widevine-for-android.md");
		
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
