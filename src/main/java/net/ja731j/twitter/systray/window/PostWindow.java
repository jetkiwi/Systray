package net.ja731j.twitter.systray.window;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.ja731j.twitter.systray.Config;
import net.ja731j.twitter.systray.SysTray;
import net.ja731j.twitter.systray.event.ExitEvent;
import net.ja731j.twitter.systray.event.ExitEventListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class PostWindow extends javax.swing.JFrame implements ExitEventListener {

    /**
     * Creates new form PostWindow2
     */
    public PostWindow() {
        SysTray.getInstance().addExitListener(this);
        initComponents();
        area.getDocument().addDocumentListener(new DocumentListenerImpl());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        message = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        postButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Post new tweet");

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+2f));
        jLabel1.setText("Post new Tweet");

        message.setFont(message.getFont().deriveFont(message.getFont().getSize()+2f));
        message.setText("Must have text");

        area.setColumns(20);
        area.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        area.setLineWrap(true);
        area.setRows(5);
        jScrollPane1.setViewportView(area);

        postButton.setText("Post");
        postButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(message)
                            .addComponent(postButton))
                        .addGap(0, 256, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void postButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postButtonActionPerformed
        TwitterFactory factory = new TwitterFactory(Config.getConfiguration());
        Twitter twitter = factory.getInstance();
        try {
            twitter.updateStatus(area.getText());
            this.dispose();
        } catch (TwitterException ex) {
            Logger.getLogger(PostWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_postButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel message;
    private javax.swing.JButton postButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onApplicationExit(ExitEvent ex) {
        SysTray.getInstance().removeExitListener(this);
        this.dispose();
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
