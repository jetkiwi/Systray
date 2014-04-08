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
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.ja731j.twitter.systray.Config;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class PostWindow extends JFrame implements ActionListener {

    private JLabel message;
    private JTextArea area;

    public PostWindow() {
        this.setSize(new Dimension(300, 200));
        Point point = MouseInfo.getPointerInfo().getLocation();
        point.translate(-300, -200);
        this.setLocation(point);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Post new Tweet");

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel("Post new Tweet");
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        layout.setConstraints(label, gbc);
        this.add(label);

        message = new JLabel();
        message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        gbc.gridy = 1;
        layout.setConstraints(message, gbc);
        this.add(message);

        area = new JTextArea(40, 4);
        area.getDocument().addDocumentListener(new DocumentListenerImpl());
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        layout.setConstraints(area, gbc);
        this.add(area);
        gbc.fill = GridBagConstraints.NONE;

        JButton confirm = new JButton("Post");
        confirm.addActionListener(this);
        gbc.gridy = 3;
        layout.setConstraints(confirm, gbc);
        this.add(confirm);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        TwitterFactory factory = new TwitterFactory(Config.getConfiguration());
        Twitter twitter = factory.getInstance();
        try {
            twitter.updateStatus(area.getText());
            this.dispose();
        } catch (TwitterException ex) {
            Logger.getLogger(PostWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class DocumentListenerImpl implements DocumentListener {

        public DocumentListenerImpl() {
        }

        @Override
        public void insertUpdate(DocumentEvent de) {

            verify(de.getDocument().getLength());
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            verify(de.getDocument().getLength());
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            verify(de.getDocument().getLength());
        }

        private void verify(int length) {
            if (length == 0) {
                message.setText("Must have text");
            } else if (length > 140) {
                message.setText("Must be under 140");
            } else {
                message.setText("");
            }
        }
    }

}
