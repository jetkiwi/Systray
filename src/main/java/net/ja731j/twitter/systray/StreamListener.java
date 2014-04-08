package net.ja731j.twitter.systray;

import java.awt.TrayIcon;
import twitter4j.Status;
import twitter4j.UserStreamAdapter;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class StreamListener extends UserStreamAdapter {

    @Override
    public void onStatus(Status status) {
        super.onStatus(status);
        SysTray.getInstance().updateStatus(status);
        TrayIcon icon = SysTray.getInstance().getIcon();
        icon.displayMessage(status.getUser().getScreenName(), status.getText(), TrayIcon.MessageType.NONE);
    }
}
