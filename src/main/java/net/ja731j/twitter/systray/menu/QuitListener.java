package net.ja731j.twitter.systray.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.ja731j.twitter.systray.Stream;
import net.ja731j.twitter.systray.SysTray;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class QuitListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        SysTray.getInstance().quit();
        Stream.getInstance().quit();
    }
    
}
