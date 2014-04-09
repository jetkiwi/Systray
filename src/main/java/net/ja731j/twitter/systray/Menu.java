package net.ja731j.twitter.systray;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.ja731j.twitter.systray.menu.AuthListener;
import net.ja731j.twitter.systray.menu.PostListener;
import net.ja731j.twitter.systray.menu.QuitListener;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class Menu {

    private static Menu instance = null;
    private final PopupMenu menu;
    private final List<MenuItem> items;
    
    private Menu() {
        // Initialize menu
        menu = new PopupMenu();
        items = new ArrayList<>();
        
        MenuItem menuQuit = new MenuItem("Quit");
        menuQuit.addActionListener(new QuitListener());
        
        MenuItem menuPost = new MenuItem("Post");
        menuPost.addActionListener(new PostListener());
        if (!Config.isAuthNotNull()) {
            menuPost.setEnabled(false);
        }
        
        MenuItem menuAuth = new MenuItem("Authorize");
        menuAuth.addActionListener(new AuthListener());
        
        this.add(menuPost,menuAuth, menuQuit);
        
        for (MenuItem item : items) {
            menu.add(item);
        }
    }
    
    static public Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }
    
    private void add(MenuItem... itemArray) {
        items.addAll(Arrays.asList(itemArray));
    }
    
    public PopupMenu getPopupMenu() {
        return menu;
    }
}
