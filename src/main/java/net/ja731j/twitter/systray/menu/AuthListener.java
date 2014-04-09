package net.ja731j.twitter.systray.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.ja731j.twitter.systray.window.AuthWindow;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class AuthListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent ae) {
        new AuthWindow().setVisible(true);
    }
    
}
