package my;

import base.Action;
import base.Agent;
import base.Perceptions;
import blocksworld.BlocksWorld;
import blocksworld.BlocksWorldPerceptions;
import blocksworld.PlanningAction;
import blocksworld.PlanningAction.PlanningActionType;

/**
 * Agent to implement.
 */
public class MyAgent implements Agent
{
	/**
	 * Name of the agent.
	 */
	String agentName;
	
	/**
	 * Constructor for the agent.
	 * 
	 * @param desiredState
	 *            - the desired state of the world.
	 * @param name
	 *            - the name of the agent.
	 */
	public MyAgent(BlocksWorld desiredState, String name)
	{
		agentName = name;
		// TODO
	}
	
	@Override
	public Action response(Perceptions input)
	{
		@SuppressWarnings("unused")
		BlocksWorldPerceptions perceptions = (BlocksWorldPerceptions) input;
		
		// TODO: revise beliefs; if necessary, make a plan; return an action.
		
		return new PlanningAction(PlanningActionType.NEW_PLAN);
	}
	
	@Override
	public String statusString()
	{
		// TODO: return information about the agent's current state and current plan.
		return toString() + ": All good.";
	}
	
	@Override
	public String toString()
	{
		return "" + agentName;
	}
}
