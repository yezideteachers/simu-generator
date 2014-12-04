package creatures;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.random;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.geom.Point2D;

public class StrategyHasard implements IStrategyDeplacement {
	
	private static final double MIN_SPEED = 3;
	private static final double MAX_SPEED = 10;
	private static final int NUMBER_OF_CYCLES_PER_CHANGE = 30;
	private int currCycle=0;
	
	public void deplacement(IEnvironment environment, Point2D position, double speed,
			double direction, Color color,AbstractCreature creature) {
		
		currCycle++;
		currCycle %= NUMBER_OF_CYCLES_PER_CHANGE;

		// every NUMBER_OF_CYCLES_PER_CHANGE we do the change
		if (currCycle == 0) {
			creature.speed += ((random() * 2) - 1);

			// maintain the speed within some boundaries
			if (creature.speed < MIN_SPEED) {
				creature.speed = MIN_SPEED;
			} else if (creature.speed > MAX_SPEED) {
				creature.speed = MAX_SPEED;
			}

			creature.setDirection(creature.direction
					+ ((random() * PI / 2) - (PI / 4)));
		}
		double newX = position.getX() + speed * cos(direction);
		// the reason there is a minus instead of a plus is that in our plane
		// Y coordinates rises downwards
		double newY = position.getY() - speed * sin(direction);
		creature.setPosition(newX, newY);
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "creatures.StrategyHasard";
	}

}
