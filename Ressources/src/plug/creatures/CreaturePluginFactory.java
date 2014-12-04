package plug.creatures;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import plug.IPlugin;
import plug.PluginLoader;
import creatures.ConcreteCreature;
import creatures.IColorStrategy;
import creatures.ICreature;
import creatures.IEnvironment;
import creatures.IStrategyComportementBord;
import creatures.IStrategyDeplacement;

public class CreaturePluginFactory {
	
	/**
	 * singleton for the abstract factory
	 */
	protected static CreaturePluginFactory _singleton;
	
	private double maxSpeed;
	
	protected PluginLoader pluginLoader;
	protected PluginLoader pluginLoaderTest;
	
	private final String pluginDir = "/target/myplugins/repository";
	
	protected Map<String,Class<? extends IPlugin>> pluginMap; 

	/**
	   * logger facilities to trace plugin loading...
	   */
	private static Logger logger = Logger.getLogger("plug.CreaturePluginFactory");
	
	
    public static void init(double inMaxSpeed) {
        if (_singleton != null) {
            throw new RuntimeException("CreatureFactory already created by " 
				  + _singleton.getClass().getName());
        } else {
             _singleton = new CreaturePluginFactory(inMaxSpeed);
        }
     }

    public static CreaturePluginFactory getInstance() {
    	return _singleton;
    }

    private CreaturePluginFactory(double inMaxSpeed) {
    	try {
    		/*Properties props = new Properties();
    		props.load(this.getClass().getResourceAsStream("project.properties"));
    		String basedir = (String) props.get("basedir");
    		String trueDirectory = (basedir + pluginDir).replace("\\", "/");*/
    		/*InputStream inputPath=Thread.currentThread().getContextClassLoader().getResourceAsStream("main/resources/project.properties");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(inputPath));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }*/
            java.net.URL url= ClassLoader.getSystemResource("project.properties");
            java.util.Properties props = new java.util.Properties();
            props.load(url.openStream());
            String trueDirectory=props.getProperty("project.root");
            
    		pluginLoader = new PluginLoader((trueDirectory+pluginDir).replace("\\","/"),IPlugin.class);
    		
    	}
    	catch (MalformedURLException ex) {
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maxSpeed=inMaxSpeed;
		pluginMap = new HashMap<String,Class<? extends IPlugin>>();
    	load();
    }
	
    public void load() {
    	pluginLoader.loadPlugins();
    
    	buildPluginMap();
    }
    
    public void reload() {
    	pluginLoader.reloadPlugins();
    	pluginMap.clear();
    	buildPluginMap();
    }
    //OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
	@SuppressWarnings("unchecked")
	private void buildPluginMap() {
		for (Class<? extends IPlugin> p : pluginLoader.getPluginClasses()) {
			if (p != null)
				pluginMap.put(p.getName(),p);
		}
	}
	
	public Map<String, Class<? extends IPlugin>> getPluginMap() {
		return pluginMap;
	}

	private final Random rand = new Random();

	@SuppressWarnings("unchecked")
	public <T extends ICreature> Collection<T> createCreatures(IEnvironment env, int count, 
								IColorStrategy colorStrategy, Class < ? extends IStrategyDeplacement> deplacement ,Class <? extends  IStrategyComportementBord> comportement ) {
		Collection<T> creatures = new ArrayList<T>();		
		Dimension s = env.getSize();		
		for (int i=0; i<count; i++) {	
			// X coordinate
			double x = (rand.nextDouble() * s.getWidth()) - s.getWidth() / 2;
			// Y coordinate
			double y = (rand.nextDouble() * s.getHeight()) - s.getHeight() / 2;
			// direction
			double direction = (rand.nextDouble() * 2 * Math.PI);
			// speed
			int speed = (int) (rand.nextDouble() * maxSpeed);			
			
			try {ConcreteCreature creature = new ConcreteCreature(env, new Point2D.Double(x,y),speed, direction, colorStrategy.getColor(),comportement.newInstance(),deplacement.newInstance());
			creatures.add((T) creature);
			} catch (Exception e) {
				logger.info("calling plugin  comportement " + comportement.getName() + " ou du plugin deplacement "+ deplacement.getName() + " failed with exception " + e.getLocalizedMessage());
				e.printStackTrace();
			}
			
		}		
		return creatures;
	}

}
