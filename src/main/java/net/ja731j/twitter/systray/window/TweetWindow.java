package net.ja731j.twitter.systray.window;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class TweetWindow extends JFrame {

    private final JLabel user = new JLabel();
    private final JLabel post = new JLabel();
    private Status status = null;

    private TweetWindow() {
    }

    public TweetWindow(Status status) {
        this.status = status;
        user.setText(status.getUser().getScreenName());
        post.setText(status.getText());

        this.setSize(new Dimension(300, 200));
        Point point = MouseInfo.getPointerInfo().getLocation();
        point.translate(-300, -200);
        this.setLocation(point);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Tweet");

        BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        this.setLayout(layout);

        user.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        this.add(user);

        post.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        this.add(post);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton retweet = new JButton("retweet");
        retweet.addActionListener(new RetweetListener(this, status));
        JButton favorite = new JButton("favorite");
        favorite.addActionListener(new FavoriteListener(this, status));
        
        buttons.add(retweet);
        buttons.add(favorite);
        
        this.add(buttons);
    }

    private class RetweetListener implements ActionListener {

        Status status;
        TweetWindow window;

        RetweetListener(TweetWindow window, Status status) {
            this.status = status;
            this.window = window;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Twitter twitter = TwitterFactory.getSingleton();
            try {
                twitter.retweetStatus(status.getId());
            } catch (TwitterException ex) {
                Logger.getLogger(TweetWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.dispose();
        }

    }

    private class FavoriteListener implements ActionListener {

        Status status;
        TweetWindow window;

        FavoriteListener(TweetWindow window, Status status) {
            this.status = status;
            this.window = window;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Twitter twitter = TwitterFactory.getSingleton();
            try {
                twitter.createFavorite(status.getId());
            } catch (TwitterException ex) {
                Logger.getLogger(TweetWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.dispose();
        }

    }
}
