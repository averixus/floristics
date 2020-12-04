package land.jay.floristics.compat;

import me.nologic.vivaldi.api.VivaldiAPI;

public class VivaldiWrapper {

	private static VivaldiAPI api = new VivaldiAPI();

	public static VivaldiAPI getAPI() {
		return api;
	}
	
}