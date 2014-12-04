package creatures;

import java.awt.Color;
import java.awt.geom.Point2D;


import plug.IPlugin;

public interface IStrategyComportementBord extends IPlugin {
	
 	void ComportementBord(IEnvironment environment, Point2D position, double speed,
			double direction, Color color,AbstractCreature creature);
}
