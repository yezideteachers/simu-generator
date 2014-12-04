package creatures;

import java.awt.Color;
import java.awt.geom.Point2D;

import plug.IPlugin;

public interface IStrategyDeplacement extends IPlugin{
	void deplacement(IEnvironment environment, Point2D position, double speed,
			double direction, Color color,AbstractCreature creature);
}
