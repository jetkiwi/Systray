package net.ja731j.twitter.systray.window;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import net.ja731j.twitter.systray.Config;
import net.ja731j.twitter.systray.SysTray;
import net.ja731j.twitter.systray.event.ExitEventListener;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class AuthWindow extends JFrame implements ActionListener,ExitEventListener{

    private JTextField field;
    private RequestToken rt;
    private OAuthAuthorization oauth = new OAuthAuthorization(Config.getConfiguration());

    public AuthWindow() {
        SysTray.getInstance().addExitListener(this);
        
        try {
            rt = oauth.getOAuthRequestToken();
        } catch (TwitterException ex) {
            Logger.getLogger(AuthWindow.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        String url = rt.getAuthorizationURL();

        this.setSize(new Dimension(300, 200));
        Point point = MouseInfo.getPointerInfo().getLocation();
        point.translate(-300, -200);
        this.setLocation(point);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Authorize Systray");

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridy=0;
        JLabel label = new JLabel("Authorize Systray by entering the PIN");
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        layout.setConstraints(label, gbc);
        this.add(label);

        gbc.gridy=1;
        JTextField urllabel = new JTextField(url);
        urllabel.setEditable(false);
        urllabel.setBorder(null);
        urllabel.setForeground(UIManager.getColor("Label.foreground"));
        urllabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        layout.setConstraints(urllabel, gbc);
        this.add(urllabel);

        field = new JTextField();
        field.setColumns(10);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridy=2;
        layout.setConstraints(field, gbc);
        this.add(field);

        JButton confirm = new JButton("Authorize");
        confirm.addActionListener(this);
        gbc.gridy = 3;
        layout.setConstraints(confirm, gbc);
        this.add(confirm);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String pin = field.getText();
        AccessToken accessToken;
        try {
            accessToken = oauth.getOAuthAccessToken(rt, pin);
        } catch (TwitterException ex) {
            Logger.getLogger(AuthWindow.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        Config.updateConfiguraiton(accessToken);
        this.dispose();
    }

    @Override
    public void onApplicationExit() {
        this.dispose();
    }

}
