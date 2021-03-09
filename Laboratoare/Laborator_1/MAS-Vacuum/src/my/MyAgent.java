package my;

import base.Action;
import base.Agent;
import base.Perceptions;
import gridworld.GridRelativeOrientation;

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
		// // clean
		if(percept.isOverJtile())
			return MyEnvironment.MyAction.PICK;
		// // turn
		if(percept.getObstacles().contains(GridRelativeOrientation.FRONT))
			return MyEnvironment.MyAction.TURN_RIGHT;
		// forward
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
