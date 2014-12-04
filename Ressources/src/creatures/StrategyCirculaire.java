package creatures;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;

public class StrategyCirculaire implements IStrategyComportementBord {

	double direction;
	AbstractCreature creature;
	
	public String getName() {
		// TODO Auto-generated method stub
		return "creatures.StrategyCirculaire";
	}

	public void ComportementBord(IEnvironment environment, Point2D position,
			double speed, double direction, Color color,
			AbstractCreature creature) {
		this.direction=direction;
		this.creature=creature;
		// TODO Auto-generated method stub
		Dimension s = environment.getSize();
		

		
		
		double hh = s.getHeight() / 2;

		// newX and newY were just put on the border of the envt. It's not a bug
		// as long as the tests passed. Now, the mirroring position is computed.
		
		if (position.getX()  > s.getWidth() / 2) {
			position.setLocation((-s.getWidth() / 2), position.getY());  
		} else if (position.getX() < -s.getWidth() / 2) {
			position.setLocation((s.getWidth() / 2), position.getY()); 
		}
		
		if (position.getY() < -hh) {
			position.setLocation(position.getX(), - 2*hh - position.getY());
			// ERROR #2 direction is badly managed 
			setDirectionBounceY();
		} else if (position.getY() > hh) {
			// ERROR #3 (cut and paste led to "hw" instead of "hh")
			position.setLocation(position.getX(),  2*hh - position.getY());
			// ERROR #2 direction is badly managed 
			setDirectionBounceY();
		}
		
				
	}
	


	private void setDirectionBounceY() {
		creature.setDirection(PI * 2 - direction);
	}

}
