package creatures;

import static commons.Utils.filter;
import static java.lang.Math.abs;

import java.awt.Color;
import java.awt.geom.Point2D;

import commons.Utils.Predicate;
import creatures.SmartCreature.CreaturesAroundCreature;

public class StrategyTroupeau implements IStrategyDeplacement {
	IEnvironment environment;
	AbstractCreature creature;
	/** Minimal distance between this creature and the ones around. */
	private final static double MIN_DIST = 10d;

	/** Minimal speed in pixels per loop. */
	private final static double MIN_SPEED = 3d;
	
	public void deplacement(IEnvironment environment, Point2D position, double speed,
			double direction, Color color,AbstractCreature creature) {
		// TODO Auto-generated method stub
		this.environment=environment;
		this.creature=creature;
		// speed - will be used to compute the average speed of the nearby
				// creatures including this instance
				double avgSpeed = speed;
				// direction - will be used to compute the average direction of the
				// nearby creatures including this instance
				double avgDir = direction;
				// distance - used to find the closest nearby creature
				double minDist = Double.MAX_VALUE;

				// iterate over all nearby creatures
				Iterable<ICreature> creatures = creaturesAround(creature);
				int count = 0;
				for (ICreature c : creatures) {
					avgSpeed += c.getSpeed();
					avgDir += c.getDirection();
					minDist = Math.min(minDist, c.distanceFromAPoint(creature.getPosition()));
					count++;
				}
				
				// average
				avgSpeed = avgSpeed / (count + 1);
				// min speed check
				if (avgSpeed < MIN_SPEED) {
					avgSpeed = MIN_SPEED;
				}
				// average
				avgDir = avgDir / (count + 1);

				// apply - change this creature state
				creature.direction = avgDir;
				creature.speed = avgSpeed;
				
				// if we are not too close move closer
				if (minDist > MIN_DIST) {
					// we move always the maximum
					double incX = speed * Math.cos(avgDir);
					double incY = - speed * Math.sin(avgDir);

					// we should not moved closer than a dist - MIN_DIST
					creature.move(incX, incY);
				}
			
	}
	static class CreaturesAroundCreature implements Predicate<ICreature> {
		private final AbstractCreature observer;

		public CreaturesAroundCreature(AbstractCreature observer) {
			this.observer = observer;
		}

		
		public boolean apply(ICreature input) {
			if (input == observer) {
				return false;
			}
			double dirAngle = input.directionFormAPoint(observer.getPosition(),
					observer.getDirection());

			return abs(dirAngle) < (observer.getFieldOfView() / 2)
					&& observer.distanceFromAPoint(input.getPosition()) <= observer
							.getLengthOfView();

		}
	}
	public Iterable<ICreature> creaturesAround(
			AbstractCreature smartCreature) {
		return filter(environment.getCreatures(), new CreaturesAroundCreature(creature));
	}
	public String getName() {
		// TODO Auto-generated method stub
		return "creatures.StrategyTroupeau";
	}

}
