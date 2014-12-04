package creatures;

import static commons.Utils.filter;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.geom.Point2D;

import commons.Utils.Predicate;


public class ToriqueCreature extends AbstractCreature {


		

		public ToriqueCreature(IEnvironment environment, Point2D position, double direction, double speed,
				Color color) {
			super(environment, position);
			this.direction = direction;
			this.speed = speed;
			this.color = color;
		}

		public void act() {
			
			double incX = speed * cos(direction);
			double incY = speed * sin(direction);
			
			move(incX, incY);
			}
		
		/*
		public void setMove(IStrategyComportementBord comportement)
		{
			@Override
			
		}
		*/
	
		
	}
