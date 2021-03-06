package my;

import base.Action;
import base.Perceptions;
import gridworld.AbstractGridEnvironment;
import gridworld.GridOrientation;
import gridworld.GridPosition;
import gridworld.GridRelativeOrientation;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Your implementation of the environment in which cleaner agents work.
 *
 * @author Andrei Olaru
 */
public class MyEnvironment extends AbstractGridEnvironment
{
	/**
	 * Actions for the cleaner agent.
	 *
	 * @author Andrei Olaru
	 */
	public static enum MyAction implements Action {
		
		/**
		 * The robot must move forward.
		 */
		FORWARD,
		
		/**
		 * The robot must turn to the left.
		 */
		TURN_LEFT,
		
		/**
		 * The robot must turn to the right.
		 */
		TURN_RIGHT,
		
		/**
		 * The robot must clean the current tile.
		 */
		PICK,
		
	}
	
	/**
	 * The perceptions of an agent.
	 * 
	 * @author andreiolaru
	 */
	public static class MyAgentPerceptions implements Perceptions
	{
		/**
		 * Obstacles perceived. They are percieved as a relative orientation at which an obstacled is neighbor to the
		 * agent. The orientation is relative to the absolute orientation of the agent.
		 */
		protected Set<GridRelativeOrientation>	obstacles;
		/**
		 * <code>true</code> if there is trash in the current tile.
		 */
		protected boolean						isOverJtile;
		/**
		 * The indication of the agent's compass.
		 */
		protected GridOrientation				absoluteOrientation;
		
		/**
		 * @param obstacleOrientations
		 *            - Obstacles perceived. They are percieved as a relative orientation at which an obstacled is
		 *            neighbor to the agent. The orientation is relative to the absolute orientation of the agent.
		 * @param jTile
		 *            - <code>true</code> if there is trash in the current tile.
		 * @param orientation
		 *            - The indication of the agent's compass.
		 */
		public MyAgentPerceptions(Set<GridRelativeOrientation> obstacleOrientations, boolean jTile,
				GridOrientation orientation)
		{
			obstacles = new HashSet<>(obstacleOrientations);
			isOverJtile = jTile;
			absoluteOrientation = orientation;
		}
		
		/**
		 * @return the obstacles, each obstacle perceived as a relative orientation at which the obstacled is neighbor
		 *         to the agent's current position. The orientation is relative to the absolute orientation of the
		 *         agent.
		 */
		public Set<GridRelativeOrientation> getObstacles()
		{
			return obstacles;
		}
		
		/**
		 * @return <code>true</code> if there is trash at the agent's current position.
		 */
		public boolean isOverJtile()
		{
			return isOverJtile;
		}
		
		/**
		 * @return the indication of the agent's compass.
		 */
		public GridOrientation getAbsoluteOrientation()
		{
			return absoluteOrientation;
		}
		
	}
	
	/**
	 * Default constructor. This should call one of the {@link #initialize} methods offered by the super class.
	 */
	public MyEnvironment()
	{
		long seed = System.currentTimeMillis(); // new random experiment
		// long seed = 42L; // existing random experiment
		System.out.println("seed: [" + seed + "]");
		Random rand = new Random(seed);
		
		super.initialize(10, 10, 10, 5, rand);
	}
	
	@Override
	public void step() {

		// TODO Auto-generated method stub
		// this should iterate through all agents, provide them with perceptions, and apply the
		// action they return.

		List<GridAgentData> agentDataList = getAgentsData();
		if (agentDataList == null || agentDataList.isEmpty()) throw new RuntimeException("Invalid Agents data. Please run again.");

		Set<GridPosition> Jtiles;
		Set<GridPosition> Xtiles;

		for (GridAgentData gridAgentData : agentDataList) {
			boolean isOverJtile = false;
			Set<GridRelativeOrientation> obstacles = new HashSet<>();

			// get agent position
			GridPosition agentPosition = gridAgentData.getPosition();
			System.out.println("Agent position: [X, Y] = " + agentPosition.toString());

			// get agent orientation
			GridOrientation agentOrientation = gridAgentData.getOrientation();
			System.out.println("Agent orientation: " + agentOrientation.toString());

			// initialize Jtiles information
			Jtiles = getJtiles();
			if (Jtiles == null || Jtiles.isEmpty()) return;

			// check if agent is over Jtile
			for (GridPosition Jtile : Jtiles) {
				if (agentPosition.getDistanceTo(Jtile) == 0) {
					System.out.println("Agent is on top of Jtile");
					isOverJtile = true;
				}
			}

			// initialize Xtiles information
			Xtiles = getXtiles();
			if (Xtiles == null || Xtiles.isEmpty()) throw new RuntimeException("Invalid Xtiles data. Please run again.");

			// compute neighbor positions
			Set<GridPosition> neighbours = new HashSet<>();
			for (GridRelativeOrientation gridRelativeOrientation : GridRelativeOrientation.values()) {
				neighbours.add(agentPosition.getNeighborPosition(agentOrientation, gridRelativeOrientation));
			}

			// check if neighbours are among Xtiles
			// if yes -> add to obstacle array
			for (GridPosition neighbour : neighbours) {
				if (Xtiles.contains(neighbour)) {
					obstacles.add(agentPosition.getRelativeOrientation(agentOrientation, neighbour));
				}
			}

			MyAgentPerceptions myAgentPerceptions = new MyAgentPerceptions(obstacles, isOverJtile, agentOrientation);

			MyAction action = (MyAction) gridAgentData.getAgent().response(myAgentPerceptions);
			switch (action) {
				case PICK: cleanTile(agentPosition);
				break;

				case FORWARD: {
					switch (agentOrientation) {
						case NORTH: gridAgentData.setPosition(new GridPosition(agentPosition.getX(), agentPosition.getY() + 1)); break;
						case EAST: gridAgentData.setPosition(new GridPosition(agentPosition.getX() + 1, agentPosition.getY())); break;
						case SOUTH: gridAgentData.setPosition(new GridPosition(agentPosition.getX(), agentPosition.getY() - 1)); break;
						case WEST: gridAgentData.setPosition(new GridPosition(agentPosition.getX() - 1, agentPosition.getY())); break;
						default: break;
					}
				}
				break;

				case TURN_RIGHT: {
					switch (agentOrientation) {
						case NORTH: gridAgentData.setOrientation(GridOrientation.EAST); break;
						case EAST: gridAgentData.setOrientation(GridOrientation.SOUTH); break;
						case SOUTH: gridAgentData.setOrientation(GridOrientation.WEST); break;
						case WEST: gridAgentData.setOrientation(GridOrientation.NORTH); break;
						default: break;
					}
				}
				break;

				case TURN_LEFT: {
					switch (agentOrientation) {
						case NORTH: gridAgentData.setOrientation(GridOrientation.WEST); break;
						case EAST: gridAgentData.setOrientation(GridOrientation.NORTH); break;
						case SOUTH: gridAgentData.setOrientation(GridOrientation.EAST); break;
						case WEST: gridAgentData.setOrientation(GridOrientation.SOUTH); break;
						default: break;
					}
				}
				break;

				default: break;
			}


		}
	}
}
