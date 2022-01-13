package before_customization;

import java.util.ArrayList;
import java.util.List;

public class CreatePlan_vpn_gateway extends Help{

	private List<String> uselessFileList = new ArrayList<String>();
	
	{
	    uselessFileList.add("about-zone-redundant-vnet-gateways.md");
		uselessFileList.add("create-zone-redundant-vnet-gateway.md");
		uselessFileList.add("nva-work-remotely-support.md");
	//	uselessFileList.add("vpn-gateway-howto-setup-alerts-virtual-network-gateway-log.md");
	//	uselessFileList.add("vpn-gateway-radiuis-mfa-nsp.md");
	}
	
	public List<String> getUselessFiles() {
		return uselessFileList;
	}
	
}
