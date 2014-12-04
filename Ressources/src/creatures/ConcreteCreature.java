package creatures;

import java.awt.Color;
import java.awt.geom.Point2D;

public class ConcreteCreature extends AbstractCreature {

	private IStrategyComportementBord comportement;
	private IStrategyDeplacement deplacement;
	public ConcreteCreature(IEnvironment environment, Point2D position, double direction, double speed,
			Color color,IStrategyComportementBord comportement,IStrategyDeplacement deplacement) {
		super(environment, position);
		this.direction = direction;
		this.speed = speed;
		this.color = color;
		// TODO Auto-generated constructor stub
		this.comportement=comportement;
		this.deplacement=deplacement;
	}

	public void act() {
		// TODO Auto-generated method stub
		deplacement.deplacement(environment, position, speed, direction, color, this);
		comportement.ComportementBord(environment, position, speed, direction, color, this);
	}

}
