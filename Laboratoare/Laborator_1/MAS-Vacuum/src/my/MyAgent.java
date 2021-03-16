package my;

import base.Action;
import base.Agent;
import base.Perceptions;
import gridworld.GridRelativeOrientation;

import java.util.*;

/**
 * Your implementation of a reactive cleaner agent.
 * 
 * @author Andrei Olaru
 */
public class MyAgent implements Agent
{
	@Override
	public Action response(Perceptions perceptions)
	{
		MyEnvironment.MyAgentPerceptions percept = (MyEnvironment.MyAgentPerceptions) perceptions;
		System.out.println("Agent sees current tile is " + (percept.isOverJtile() ? "dirty" : "clean")
				+ "; current orientation is " + percept.getAbsoluteOrientation() + "; obstacles at: "
				+ percept.getObstacles());
		System.out.println("<<<<<================================================================>>>>>");
		System.out.println("\n");

		// // clean
		if(percept.isOverJtile())
			return MyEnvironment.MyAction.PICK;

		// // turn
		if(percept.getObstacles().contains(GridRelativeOrientation.FRONT))
			return MyEnvironment.MyAction.TURN_RIGHT;

		// Generate random movement factor
		Map<MyEnvironment.MyAction, Integer> movementWeightsMap = new HashMap<>();
		movementWeightsMap.put(MyEnvironment.MyAction.FORWARD, 70);
		movementWeightsMap.put(MyEnvironment.MyAction.TURN_RIGHT, 20);
		movementWeightsMap.put(MyEnvironment.MyAction.TURN_LEFT, 10);

		int randomNumber = new Random().nextInt(100);

		int total = 0;
		for (Map.Entry<MyEnvironment.MyAction, Integer>  entry : movementWeightsMap.entrySet()) {
			total = total + entry.getValue();
			if (total > randomNumber) return entry.getKey();
		}

		return MyEnvironment.MyAction.FORWARD;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		// please use a single character
		return "1";
	}
	
}
