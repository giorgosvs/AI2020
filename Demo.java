package map;

import aima.core.agent.Agent;
import aima.core.agent.EnvironmentListener;
import aima.core.agent.impl.DynamicPercept;
import aima.core.agent.impl.SimpleEnvironmentView;
import aima.core.environment.map.MapAgent;
import aima.core.environment.map.MapEnvironment;
import aima.core.environment.map.MapFunctions;
import aima.core.environment.map.MoveToAction;
import aima.core.environment.map.SimpleMapAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;

public class Demo {
	
	public static void main(String[] args) {
		
		ExtendableMap map = new ExtendableMap();
		GraphTree.initGraph(map);
		
		MapEnvironment env = new MapEnvironment(map);
		EnvironmentListener<Object, Object> envView = new SimpleEnvironmentView();
		env.addEnvironmentListener(envView);
		
		String agentLoc = GraphTree.S;
		String destination = GraphTree.G;
		
		SearchForActions<String, MoveToAction> aStarSearch;
		SearchForActions<String, MoveToAction> DepthFirstSearch;
		SearchForActions<String, MoveToAction> BreadthFirstSearch;
		
		aStarSearch = new AStarSearch<>(new GraphSearch<>(), MapFunctions.createSLDHeuristicFunction(destination, map));
		DepthFirstSearch  = new DepthFirstSearch<>(new GraphSearch<>());
		BreadthFirstSearch = new BreadthFirstSearch<>(new GraphSearch<>());
		
		System.out.println("---Initiating A* Search Concept---\n");
		Agent<DynamicPercept, MoveToAction> agent;
		agent = new MapAgent(map, aStarSearch, destination);
		env.addAgent(agent, agentLoc);
		env.stepUntilDone();
		envView.notify(aStarSearch.getMetrics().toString());
		System.out.println("\n---A* Search Concept done---\n");
		
		System.out.println("---Initiating Depth First Search concept---\n");
		agent = new SimpleMapAgent (map, DepthFirstSearch , destination);
		env.addAgent(agent, agentLoc);
		env.stepUntilDone();
		envView.notify(aStarSearch.getMetrics().toString());
		System.out.println("\n---Depth First Concept Done---");
		
		System.out.println("\n---Initiating Breadth First Search concept---\n");
		agent = new MapAgent(map, BreadthFirstSearch, destination);
		env.addAgent(agent, agentLoc);
		env.stepUntilDone();
		envView.notify(aStarSearch.getMetrics().toString());
		System.out.println("\n---Breadth First Search concept done---\n");
	}
}
