package creatures;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;



public class StrategyFerme  implements IStrategyComportementBord {

	double direction;
	AbstractCreature creature;
	
	public void ComportementBord(IEnvironment environment, Point2D position, double speed,
			double direction, Color color,AbstractCreature creature) {
		// TODO Auto-generated method stub
		this.direction=direction;
		this.creature=creature;
		Dimension s = environment.getSize();
		
		
		double hw = s.getWidth() / 2;
		double hh = s.getHeight() / 2;

		// newX and newY were just put on the border of the envt. It's not a bug
		// as long as the tests passed. Now, the mirroring position is computed.
		
		if (position.getX() < -hw) {
			position.setLocation( - 2*hw - position.getX(),position.getY());
			// ERROR #2 direction is badly managed 
			setDirectionBounceX();	
		} else if (position.getX() > hw) {
			position.setLocation(2*hw - position.getX(),position.getY());
			// ERROR #2 direction is badly managed 
			setDirectionBounceX();
		} // else // ERROR #1 (NO ELSE, we need to check X and Y independently)
		
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
	

	
	
	private void setDirectionBounceX() {
		if (direction >= PI)
			creature.setDirection(3*PI - direction);
		else
			creature.setDirection(PI - direction);
	}

	private void setDirectionBounceY() {
		creature.setDirection(PI * 2 - direction);
	}




	public String getName() {
		// TODO Auto-generated method stub
		return "creatures.StrategyFerme";
	}
}

