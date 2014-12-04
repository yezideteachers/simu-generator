package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import plug.IPlugin;
import plug.creatures.CreaturePluginFactory;
import plug.creatures.PluginMenuItemBuilder;
import creatures.ICreature;
import creatures.IStrategyComportementBord;
import creatures.IStrategyDeplacement;
import creatures.visual.ColorCube;
import creatures.visual.CreatureInspector;
import creatures.visual.CreatureSimulator;
import creatures.visual.CreatureVisualizer;


/**
 * Just a simple test of the simulator.
 * 
 */
@SuppressWarnings("serial")
public class Launcher extends JFrame {

	private final CreaturePluginFactory factory;
	
	private final CreatureInspector inspector;
	private final CreatureVisualizer visualizer;
	private final CreatureSimulator simulator;
	
	private PluginMenuItemBuilder menuBuilder;
	private JMenuBar mb = new JMenuBar();
	private JMenu sousMb = new JMenu("Ajout d'une creature durant la simulation");
	
	Class<? extends IStrategyDeplacement> pluginDeplacement = null;
	Class<? extends IStrategyComportementBord> pluginComportement = null;
	private JTextArea test;
	  
	public Launcher() {
		factory = CreaturePluginFactory.getInstance();

		setName("Creature Simulator Plugin Version");
		setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel(new GridLayout(1,2));
		
		
		JButton loader = new JButton("Load plugins");
		loader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				factory.load();
				buildPluginMenus();
			}
		});
		buttons.add(loader);

		JButton reloader = new JButton("Reload plugins");
		reloader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				factory.reload();
				buildPluginMenus();
			}
		});
		buttons.add(reloader);

		JButton restart = new JButton("(Re-)start simulation");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((pluginDeplacement != null)&&(pluginComportement!=null)) {
					synchronized(simulator) {
						if (simulator.isRunning()) {
							simulator.stop();
						}
					}
					simulator.clearCreatures();
					
					Collection<? extends ICreature> creatures;
					
						creatures = factory.createCreatures(simulator, Constante.NB_CREATURE, Constante.getColor(),pluginDeplacement,pluginComportement);
						simulator.addAllCreatures(creatures);
					
					
					simulator.start();
				}
			}
		});
		buttons.add(restart);		
		
	        test = new JTextArea("");
	        test.setEditable(true);
	       
	        add(test,BorderLayout.EAST);
	   
		
		JButton checkTest = new JButton("check test");
		checkTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pluginComportement != null) {
					String creatureTypeComportement=pluginComportement.getName();
					String creatureTypeDeplacement=pluginDeplacement.getName();
					try{
						java.net.URL url= ClassLoader.getSystemResource("project.properties");
			            java.util.Properties props = new java.util.Properties();
			            props.load(url.openStream());
			            String trueDirectory=props.getProperty("project.root");
						BufferedReader br = new BufferedReader(new FileReader((trueDirectory + "/target/surefire-reports/" + creatureTypeComportement + "Test.txt").replace("\\","/"))) ;
				        StringBuilder sb = new StringBuilder();
				        String line = br.readLine();

				        while (line != null) {
				            sb.append(line);
				            sb.append(System.lineSeparator());
				            line = br.readLine();
				        }
				        br.close();
				        br = new BufferedReader(new FileReader((trueDirectory + "/target/surefire-reports/" + creatureTypeDeplacement + "Test.txt").replace("\\","/"))) ;
				        
				        line = br.readLine();

				        while (line != null) {
				            sb.append(line);
				            sb.append(System.lineSeparator());
				            line = br.readLine();
				        }
				        br.close();
				        String everything = sb.toString();
				        
				        test.setText(everything);
				        
				        System.out.println("rapport " + everything);
				        
				    } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally{}
				}
			}
		});
		buttons.add(checkTest);
		
		this.add(test,BorderLayout.EAST);
		
		
		add(buttons, BorderLayout.SOUTH);
				
		simulator = new CreatureSimulator(new Dimension(640, 480));		
		inspector = new CreatureInspector();
		inspector.setFocusableWindowState(false);
		visualizer = new CreatureVisualizer(simulator);
		visualizer.setDebug(false);
		visualizer.setPreferredSize(simulator.getSize());
		
		add(visualizer, BorderLayout.CENTER);
	
	    buildPluginMenus();

	    pack();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exit(evt);
			}
		});
		
	}
	
	private void exit(WindowEvent evt) {
		System.exit(0);
	}

	public void buildPluginMenus() {
		sousMb.removeAll();
		mb.removeAll();
		
		
		ActionListener listener = new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				// the name of the plugin is in the ActionCommand
				String tempS=((JMenuItem) e.getSource()).getActionCommand();
				System.out.println(tempS);
				for (String s :  factory.getPluginMap().keySet())
				{
					System.out.println(s);
				}
				
				Class <? extends IPlugin> temp =factory.getPluginMap().get(((JMenuItem) e.getSource()).getActionCommand());
				
			
			try {
				
				if (Class.forName("creatures.IStrategyComportementBord").isAssignableFrom(temp))
				{
					pluginComportement=(Class<? extends IStrategyComportementBord>) temp;
					boolean comp= (pluginComportement != null);
					System.out.println("class comp charger  : " + comp );
				}
				if (Class.forName("creatures.IStrategyDeplacement").isAssignableFrom(temp))
				{
					pluginDeplacement=(Class<? extends IStrategyDeplacement>) temp;
					
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
		};
		ActionListener listenerAdd = new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				// the name of the plugin is in the ActionCommand
			
			
				
				Class <? extends IPlugin> temp =factory.getPluginMap().get(((JMenuItem) e.getSource()).getActionCommand());
				boolean really= (temp==null);
				System.out.println("class charger retrouver dans le map  : " + really );
			try {
				
				if (Class.forName("creatures.IStrategyDeplacement").isAssignableFrom(temp))
				{
					pluginDeplacement=(Class<? extends IStrategyDeplacement>) temp;
					
					if ((pluginDeplacement != null)&&(pluginComportement!=null)) {
						synchronized(simulator) {
							if (simulator.isRunning()) {
								
							}
						}
						
						
						Collection<? extends ICreature> creatures;
						
							creatures = factory.createCreatures(simulator, 1, new ColorCube(50),pluginDeplacement,pluginComportement);
							simulator.addAllCreatures(creatures);
					}
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
		};
		
		menuBuilder = new PluginMenuItemBuilder(factory.getPluginMap(),listener);
		menuBuilder.setMenuTitle("Choix du Comportement au Bord et Deplacement de base");
		menuBuilder.buildMenu();
		JMenu jmenu=menuBuilder.getMenu();
		
	    
	    for (String plug : factory.getPluginMap().keySet()) {
	    	Class deplacementTemp=factory.getPluginMap().get(plug);
	    	try {
				if (Class.forName("creatures.IStrategyDeplacement").isAssignableFrom(deplacementTemp))
				{
					JMenuItem mi = new JMenuItem(deplacementTemp.getName());
					mi.setActionCommand(deplacementTemp.getName());
				  	mi.addActionListener(listenerAdd);
				  	sousMb.add(mi);
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      
	      // ActionCommand contains the name of the plugin = key in the map
	     
	 
	    }
		//jmenu.add(sousMb);
		mb.add(jmenu);
		mb.add(sousMb);
		setJMenuBar(mb);
	}
	
	
	public static void main(String args[]) {
	    Logger.getLogger("plug").setLevel(Level.INFO);
		double myMaxSpeed = 5;
		CreaturePluginFactory.init(myMaxSpeed);
		Launcher launcher = new Launcher();
	
		launcher.setVisible(Constante.visu);
		
	}
	
}


