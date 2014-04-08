package net.ja731j.twitter.systray.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.ja731j.twitter.systray.window.PostWindow;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class PostListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        new PostWindow().setVisible(true);
    }
    
}
