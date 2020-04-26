package map;

import java.util.List;

public interface Map {
	
	public List<String> getLocations();
	public List<String> getPossibleNextLocations(String location);
	public List<String> getPossiblePrevLocations(String location);
	public Double getDistance(String fromLocation, String toLocation);
	
	
}
