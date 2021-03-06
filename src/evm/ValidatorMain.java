/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evm;

import MFS100.DeviceInfo;
import MFS100.FingerData;
import MFS100.MFS100;
import MFS100.MFS100Event;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ValidatorMain extends javax.swing.JFrame implements MFS100Event{
    
    private MFS100 mfs100;
    private FPImage m_FingerPrintImage;
    private FingerData fingerData = null;
    private RXTXTEST rxtx;
    private VoteHandler voteHandler;
    private Buzzer buzzer;
    private String username;
    
    private final int THRESHOLD = 60;
    private final int TIMEOUT = 10000;

    /**
     * Creates new form ValidatorMain
     */
    public ValidatorMain() {
        initComponents();
        mfs100 = new MFS100(this);
        m_FingerPrintImage = new FPImage(fpicon.getWidth(), fpicon.getHeight());
        rxtx = new RXTXTEST();
        
        rxtx.setCastListener(new RXTXTEST.CastListener() {
            @Override
            public void cast(int nominee) {
                castVote(nominee);
            }
        });
        
        voteHandler = new VoteHandler();
        //buzzer = new Buzzer();
        sensorInit();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fpicon = new javax.swing.JLabel();
        deactivateFP = new javax.swing.JButton();
        activateFP1 = new javax.swing.JButton();
        voterLabel = new javax.swing.JLabel();
        outputLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fpicon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        deactivateFP.setText("Deactivate");
        deactivateFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deactivateFPActionPerformed(evt);
            }
        });

        activateFP1.setText("Activate");
        activateFP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activateFP1ActionPerformed(evt);
            }
        });

        voterLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        voterLabel.setToolTipText("");
        voterLabel.setBorder(javax.swing.BorderFactory.createTitledBorder("Voter"));

        outputLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        outputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(voterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fpicon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deactivateFP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(activateFP1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(outputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(fpicon, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(voterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activateFP1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deactivateFP, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void activateFP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activateFP1ActionPerformed
        int ret = mfs100.StartCapture(THRESHOLD, TIMEOUT, true);
        
        if(ret != 0){
            outputLabel.setText("ERROR E01");
        }else
            outputLabel.setText("Scan Finger");
    }//GEN-LAST:event_activateFP1ActionPerformed

    private void deactivateFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deactivateFPActionPerformed
        mfs100.StopCapture();
    }//GEN-LAST:event_deactivateFPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ValidatorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ValidatorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ValidatorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ValidatorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ValidatorMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton activateFP1;
    private javax.swing.JButton deactivateFP;
    private javax.swing.JLabel fpicon;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JLabel voterLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void OnPreview(FingerData fd) {
        Runnable runnable = new Runnable() {
            public void run() {
                m_FingerPrintImage.setImage(mfs100.BytesToBitmap(fd.FingerImage()));
                fpicon.setIcon(m_FingerPrintImage);
                fpicon.repaint();
                
                int quality = fd.Quality();
                int percentage = quality/THRESHOLD * 100; 
                if(percentage < 100)
                    outputLabel.setText(Float.toString(percentage)+"%");
                else
                    outputLabel.setText("Finger print captured");
                outputLabel.repaint();
           }
        };
        Thread trd = new Thread(runnable);
        trd.start();
    }

    @Override
    public void OnCaptureCompleted(boolean bln, int i, String string, FingerData fd) {
         if(bln){
             try {
                 byte[] fingerprintData = fd.ANSITemplate();
                 FPMatcher matcher = new FPMatcher(fingerprintData);
                 if(matcher.findMatch()){
                     username = matcher.getUsername();
                     
                     if(username.equals("NA")){
                         JOptionPane.showMessageDialog(this, "BHAGOOOOO!!!!!");
                     }else{
                         voterLabel.setText(username);
                         outputLabel.setText("Finger matched");
                         activateEVM();
                     }
                     
                     
                 }else{
                     outputLabel.setText("Finger not matched");
                     JOptionPane.showMessageDialog(this, "Finger not matched");
                 }} catch (Exception ex) {
                 Logger.getLogger(ValidatorMain.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        }
    }
    
    private void activateEVM(){
        rxtx.send("activate");
        enableButtons(false);
    }
    
    private void enableButtons(boolean bool){
        activateFP1.setEnabled(bool);
        deactivateFP.setEnabled(bool);
    }
    
    private int sensorInit() {
        int ret = mfs100.Init();
        
        if(ret != 0){
            outputLabel.setText(mfs100.GetErrorMsg(ret));
        }
        return ret;
    }
    
    


    public void castVote(int nominee) {
        System.out.println(nominee);
        voteHandler.voteNominee(username, nominee);
        //buzzer.buzz();
        JOptionPane.showMessageDialog(this, "Vote done.");
        enableButtons(true);
        
    }
    
     public class FPImage implements Icon {

        int _Width = 0;
        int _Height = 0;

        public FPImage(int Width, int Height) {
            this._Width = Width;
            this._Height = Height;
            m_Image = null;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (m_Image != null) {
                g.drawImage(m_Image, x, y, getIconWidth(), getIconHeight(), null);
            } else {
                g.fillRect(x, y, getIconWidth(), getIconHeight());
            }
        }

        @Override
        public int getIconWidth() {
            return _Width;
        }

        @Override
        public int getIconHeight() {
            return _Height;
        }

        public boolean LoadImage(String path) {
            boolean bRetCode = false;
            Image newImg;
            try {
                File f = new File(path);
                newImg = ImageIO.read(f);
                bRetCode = true;
                setImage(newImg);
            } catch (IOException e) {
            }

            return bRetCode;
        }

        public void setImage(Image Img) {
            if (Img != null) {
                m_Image = Img.getScaledInstance(getIconWidth(), getIconHeight(), Image.SCALE_FAST);
            } else {
                m_Image = null;
            }
        }

        private Image m_Image;
    }

}
