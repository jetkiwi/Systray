package net.ja731j.twitter.systray.window;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.ja731j.twitter.systray.Config;
import net.ja731j.twitter.systray.SysTray;
import net.ja731j.twitter.systray.event.ExitEvent;
import net.ja731j.twitter.systray.event.ExitEventListener;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class TweetWindow extends javax.swing.JFrame implements ExitEventListener {

    private Status status = null;

    /**
     * Creates new form TweetWindow2
     *
     * @param status
     */
    public TweetWindow(Status status) {
        SysTray.getInstance().addExitListener(this);
        this.status = status;
        initComponents();
        sourceLabel.setText(status.getUser().getScreenName());
        contentLabel.setText(status.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sourceLabel = new javax.swing.JLabel();
        contentLabel = new javax.swing.JLabel();
        replyButton = new javax.swing.JButton();
        retweetButton = new javax.swing.JButton();
        favoriteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Displaying tweet");

        sourceLabel.setFont(sourceLabel.getFont().deriveFont(sourceLabel.getFont().getSize()+2f));

        contentLabel.setFont(contentLabel.getFont().deriveFont(contentLabel.getFont().getSize()+2f));

        replyButton.setText("Reply");
        replyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replyButtonActionPerformed(evt);
            }
        });

        retweetButton.setText("Retweet");
        retweetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retweetButtonActionPerformed(evt);
            }
        });

        favoriteButton.setText("Favorite");
        favoriteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoriteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sourceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(replyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(retweetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(favoriteButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sourceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(replyButton)
                    .addComponent(retweetButton)
                    .addComponent(favoriteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void retweetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retweetButtonActionPerformed
        TwitterFactory f = new TwitterFactory(Config.getConfiguration());
        Twitter twitter = f.getInstance();
        try {
            twitter.retweetStatus(status.getId());
        } catch (TwitterException ex) {
            Logger.getLogger(TweetWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_retweetButtonActionPerformed

    private void favoriteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoriteButtonActionPerformed
        TwitterFactory f = new TwitterFactory(Config.getConfiguration());
        Twitter twitter = f.getInstance();
        try {
            twitter.createFavorite(status.getId());
        } catch (TwitterException ex) {
            Logger.getLogger(TweetWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_favoriteButtonActionPerformed

    private void replyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replyButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_replyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contentLabel;
    private javax.swing.JButton favoriteButton;
    private javax.swing.JButton replyButton;
    private javax.swing.JButton retweetButton;
    private javax.swing.JLabel sourceLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onApplicationExit(ExitEvent ex) {
        SysTray.getInstance().removeExitListener(this);
        this.dispose();
    }
}
