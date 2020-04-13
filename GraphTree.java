package map;

import java.util.List;

public class GraphTree extends ExtendableMap {
	
	
	public static final String S = "S";
	public static final String A = "A";
	public static final String F = "F";
	public static final String K = "K";
	public static final String B = "B";
	public static final String H = "H";
	public static final String L = "L";
	public static final String C = "C";
	public static final String I = "I";
	public static final String D = "D";
	public static final String J = "J";
	public static final String E = "E";
	public static final String G = "G";

	public GraphTree() {
		initGraph(this);
	}
	
	public static void initGraph(ExtendableMap map) {
		map.clear();
		
		map.addBidirectionalLink(S, A, 2.0);
		map.addBidirectionalLink(S, F, 1.0);
		map.addBidirectionalLink(S, K, 2.0);
		map.addBidirectionalLink(A, B, 2.0);
		map.addBidirectionalLink(F, H, 1.0);
		map.addBidirectionalLink(K, L, 1.0);
		map.addBidirectionalLink(B, C, 2.0);
		map.addBidirectionalLink(H, I, 1.0);
		map.addBidirectionalLink(B, G, 3.0);
		map.addBidirectionalLink(C, D, 2.0);
		map.addBidirectionalLink(I, J, 1.0);
		map.addBidirectionalLink(D, E, 1.0);
		map.addBidirectionalLink(D, G, 5.0);
		map.addBidirectionalLink(J, G, 1.0);
		
	}
	
	/*public static List<String> initGraphHeuristicDistance(ExtendableMap map) {
		List<String> Location = map.getLocations();
		for(String a : Location) {
			map.addHeuristics(S, 4.0);
		}
	}*/
}
