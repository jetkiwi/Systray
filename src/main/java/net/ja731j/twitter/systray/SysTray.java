package net.ja731j.twitter.systray;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.ja731j.twitter.systray.window.TweetWindow;
import twitter4j.Status;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class SysTray implements ActionListener{

    private SystemTray tray = SystemTray.getSystemTray();
    private TrayIcon icon;
    private static SysTray instance = null;
    private Thread thread = null;
    private Status status = null;
    
    protected void updateStatus(Status status){
        this.status = status;
    }

    static public SysTray getInstance() {
        try {
            if (instance == null) {
                instance = new SysTray();
            }
            return instance;
        } catch (IOException | AWTException ex) {
            throw new RuntimeException(ex);
        }
    }

    private SysTray() throws IOException, AWTException {
        // Initialize icon
        icon = new TrayIcon(ImageIO.read(Class.class.getResourceAsStream("/icon.png")));
        icon.setImageAutoSize(true);
        
        icon.addActionListener(this);
        icon.setPopupMenu(Menu.getInstance().getPopupMenu());

    }

    /**
     * @return the icon
     */
    public TrayIcon getIcon() {
        return icon;
    }

    public void init() {
        if (thread == null) {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        tray.add(icon);
                    } catch (AWTException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            thread.start();
        }
    }

    public void quit() {
        if (thread != null) {
            tray.remove(icon);
            thread = null;
        }
    }

    public void displayMessage(String str1, String str2, MessageType mt) {
        icon.displayMessage(str2, str1, mt);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(status != null){
            new TweetWindow(status).setVisible(true);
        }
    }

}
