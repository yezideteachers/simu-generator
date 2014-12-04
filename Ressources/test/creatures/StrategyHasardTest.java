package creatures;

import static java.lang.Math.toRadians;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;





import org.junit.Before;
import org.junit.Test;

import creatures.visual.CreatureSimulator;

public class StrategyHasardTest {


	CreatureSimulator environment = mock(CreatureSimulator.class);
	final double w = 200;
	final double h = 100;
	
	@Before
	public void setup() {
		when(environment.getSize()).thenReturn(new Dimension((int)w, (int)h));
	}

	@Test
	public void testDeplacement() throws Exception {
		ConcreteCreature creature = new ConcreteCreature(environment,new Point2D.Double(0, 0), toRadians(0), 5, Color.RED, new StrategyTorique(), new StrategyHasard());	
		double x= creature.getPosition().getX();
		double y= creature.getPosition().getY();
		 creature.act();
		
		assertTrue(creature.getPosition().getX()!=x);
	
	
		
	}
}
