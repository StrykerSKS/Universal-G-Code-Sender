/*
    Copywrite 20152016 Will Winder

    This file is part of Universal Gcode Sender (UGS).

    UGS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UGS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UGS.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.willwinder.ugs.nbp.core.filebrowser;

import com.willwinder.ugs.nbp.core.filebrowser.Bundle;
import com.willwinder.ugs.nbp.lookup.CentralLookup;
import com.willwinder.universalgcodesender.listeners.UGSEventListener;
import com.willwinder.universalgcodesender.model.BackendAPI;
import com.willwinder.universalgcodesender.model.UGSEvent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.willwinder.universalgcodesender.nbp.filebrowser//FileBrowserTopComponent//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "FileBrowserTopComponentTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "middle_left", openAtStartup = true)
@ActionID(category = "Window", id = "com.willwinder.universalgcodesender.nbp.filebrowser.FileBrowserTopComponentTopComponent")
@ActionReference(path = "Menu/Window/Classic" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_FileBrowserTopComponentAction",
        preferredID = "FileBrowserTopComponentTopComponent"
)
@Messages({
    "CTL_FileBrowserTopComponentAction=File browser",
    "CTL_FileBrowserTopComponentTopComponent=File Browser",
    "HINT_FileBrowserTopComponentTopComponent=File browers"
})
public final class FileBrowserTopComponent extends TopComponent implements UGSEventListener {
    BackendAPI backend;
    
    public FileBrowserTopComponent() {
        initComponents();
        setName(Bundle.CTL_FileBrowserTopComponentTopComponent());
        setToolTipText(Bundle.HINT_FileBrowserTopComponentTopComponent());
    }

    public static void openGcodeFileDialog() {
        OpenGcodeFile.openGcodeFileDialog();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        browseButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(FileBrowserTopComponent.class, "FileBrowserTopComponent.browseButton.text")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(FileBrowserTopComponent.class, "FileBrowserTopComponent.jLabel1.text")); // NOI18N

        fileTextField.setText(org.openide.util.NbBundle.getMessage(FileBrowserTopComponent.class, "FileBrowserTopComponent.fileTextField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseButton)
                    .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        openGcodeFileDialog();
    }//GEN-LAST:event_browseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        backend = CentralLookup.getDefault().lookup(BackendAPI.class);

        backend.addUGSEventListener(this);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void UGSEvent(UGSEvent cse) {
        if (cse.isFileChangeEvent()) {
            fileTextField.setText(backend.getGcodeFile().getAbsolutePath());
        }
    }
}
