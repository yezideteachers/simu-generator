package creatures;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;

public class StrategyTorique implements IStrategyComportementBord {

	public void ComportementBord(IEnvironment environment, Point2D position, double speed,
			double direction, Color color,AbstractCreature creature)
	{
Dimension s = environment.getSize();
		double x =position.getX();
		double y = position.getY();
		if (x > s.getWidth() / 2) {
			x = -s.getWidth() / 2;
		} else if (x < -s.getWidth() / 2) {
			x = s.getWidth() / 2;
		}

		if (y > s.getHeight() / 2) {
			y = -s.getHeight() / 2;
		} else if (y < -s.getHeight() / 2) {
			y = s.getHeight() / 2;
		}
		creature.position = new Point2D.Double(x, y);
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "creatures.StrategyTorique";
	}

}
