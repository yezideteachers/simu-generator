package plug.creatures;

import java.lang.reflect.*;
import java.util.Map;
import java.util.logging.Logger;
import java.awt.event.*;

import javax.swing.*;

import plug.IPlugin;
import creatures.ICreature;


public class PluginMenuItemBuilder {

  private JMenu menu;

  private Map<String,Class<? extends IPlugin>> plugins;

  private ActionListener listener;
  
  private static Logger logger = Logger.getLogger("plug.Menu");

  public PluginMenuItemBuilder(Map<String, Class<? extends IPlugin>> map,
                               ActionListener listener) {
    menu = new JMenu();
    this.plugins = map;
    this.listener = listener;
  }

  public void setMenuTitle(String title) {
    menu.setText(title);
  }

  public void buildMenu() {
    logger.info("Building plugin menu");
    menu.removeAll();
    for (String name : plugins.keySet()) {
      JMenuItem mi = new JMenuItem(name);
      // ActionCommand contains the name of the plugin = key in the map
      mi.setActionCommand(name);
      mi.addActionListener(listener);
      menu.add(mi);
    }
  }

  public JMenu getMenu() {
    return menu;
  }

}
