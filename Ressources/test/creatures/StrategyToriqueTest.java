package creatures;

import static java.lang.Math.toRadians;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import creatures.visual.CreatureSimulator;

public class StrategyToriqueTest {
	CreatureSimulator environment = mock(CreatureSimulator.class);
	final double w = 200;
	final double h = 100;
	
	@Before
	public void setup() {
		when(environment.getSize()).thenReturn(new Dimension((int)w, (int)h));
	}

	@Test
	public void testDirectLeft() throws Exception {
		ConcreteCreature creature = new ConcreteCreature(environment, new Point2D.Double(-w/2+1, 0), toRadians(180),10, Color.RED, new StrategyTorique(), new StrategyHasard());	
		creature.act();
		assertEquals(toRadians(180), creature.getDirection(), 0.01);
		assertTrue(w/2<=creature.getPosition().getX());
		
    }	

	
	@Test
	public void testDirectRight() throws Exception {
		
		ConcreteCreature creature = new ConcreteCreature(environment, new Point2D.Double(w/2-1, 0), toRadians(360),10, Color.RED, new StrategyTorique(), new StrategyHasard());	
		creature.act();
		assertEquals(toRadians(360), creature.getDirection(), 0.01);
		assertTrue(-w/2>=creature.getPosition().getX());
		
    }	
	
	@Test
	public void testDirectUp() throws Exception {
	
		ConcreteCreature creature = new ConcreteCreature(environment, new Point2D.Double(0, -h/2+1), toRadians(90),3, Color.RED, new StrategyTorique(), new StrategyHasard());	
		creature.act();		
		assertEquals(toRadians(90), creature.getDirection(), 0.01);
		assertTrue(h/2<=creature.getPosition().getY());
		
    }	
	
	@Test
	public void testDirectDown() throws Exception {
		
		ConcreteCreature creature = new ConcreteCreature(environment, new Point2D.Double(0, h/2-1), toRadians(270),3, Color.RED, new StrategyTorique(), new StrategyHasard());	
		creature.act();
		assertEquals(toRadians(270), creature.getDirection(), 0.01);
		assertTrue(-h/2>=creature.getPosition().getY());
		
    }	

}
