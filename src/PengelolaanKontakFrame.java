import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PengelolaanKontakFrame extends javax.swing.JFrame {
    private final db_Kontak dbHelper;
    /**
     * Creates new form PengelolaanKontakFrame
     */
    public PengelolaanKontakFrame() {
        initComponents();
        dbHelper = new db_Kontak (); // Inisialisasi dbHelper di sini
        loadKontakData(); // Memuat data kontak pada saat form pertama kali dibuka
        btnTambah.addActionListener(this::btnTambahActionPerformed);
        btnEdit.addActionListener(this::btnEditActionPerformed);
        btnHapus.addActionListener(this::btnHapusActionPerformed);
        btnCari.addActionListener(this::btnCariActionPerformed);
        btnEkspor.addActionListener(e -> eksporKeCSV());
        btnImpor.addActionListener(e -> imporDariCSV());
        
tblKontak.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting() && tblKontak.getSelectedRow() != -1) {
        int selectedRow = tblKontak.getSelectedRow();
        jNama.setText((String) tblKontak.getValueAt(selectedRow, 1)); // Kolom Nama
        jNomor.setText((String) tblKontak.getValueAt(selectedRow, 2)); // Kolom Nomor
        jcbList.setSelectedItem((String) tblKontak.getValueAt(selectedRow, 3)); // Kolom Kategori
    }
});

    }

private void eksporKeCSV() {
    try (FileWriter writer = new FileWriter("kontak.csv")) {
        // Tulis header kolom
        writer.append("ID,Nama,Nomor,Kategori\n");

        // Dapatkan daftar kontak dari database dan tulis ke CSV
        for (String[] kontak : dbHelper.getKontakList()) {
            writer.append(String.join(",", kontak));
            writer.append("\n");
        }
        
        JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke kontak.csv!");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengekspor data!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void imporDariCSV() {
    try (BufferedReader reader = new BufferedReader(new FileReader("kontak.csv"))) {
        String line;
        boolean header = true;
        
        while ((line = reader.readLine()) != null) {
            if (header) { // Lewati baris header
                header = false;
                continue;
            }
            
            String[] data = line.split(",");
            if (data.length == 4) { // Pastikan ada 4 kolom (ID, Nama, Nomor, Kategori)
                String nama = data[1];
                String nomor = data[2];
                String kategori = data[3];
                
                dbHelper.tambahKontak(nama, nomor, kategori);
            }
        }
        
        JOptionPane.showMessageDialog(this, "Data berhasil diimpor dari kontak.csv!");
        loadKontakData(); // Refresh data di JTable setelah impor
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengimpor data!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jNama = new javax.swing.JTextField();
        jNomor = new javax.swing.JTextField();
        jCari = new javax.swing.JTextField();
        jcbList = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListKategori = new javax.swing.JList<>();
        btnCari = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnEkspor = new javax.swing.JButton();
        btnImpor = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKontak = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 153));

        jLabel1.setText("Nama");

        jLabel2.setText("No Telephone");

        jLabel3.setText("Kategori");

        jLabel4.setText("Pencarian Nama/ No Telephone");

        jcbList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Keluarga", "Teman", "Sahabat", "Kakak", "Adik" }));

        jListKategori.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Keluarga", "Teman", "Sahabat", "Kakak", "Adik" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListKategori.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListKategoriValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListKategori);

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEkspor.setText("Ekspor to CSV");
        btnEkspor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEksporActionPerformed(evt);
            }
        });

        btnImpor.setText("Impor");
        btnImpor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImporActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tblKontak.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Nomor", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblKontak);

        jLabel5.setFont(new java.awt.Font("Mongolian Baiti", 1, 36)); // NOI18N
        jLabel5.setText("Aplikasi Pengelolaan Kontak");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jNama)
                    .addComponent(jNomor)
                    .addComponent(jcbList, 0, 128, Short.MAX_VALUE)
                    .addComponent(jCari))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEkspor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImpor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel5)
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCari))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambah))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jcbList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEkspor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImpor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus)))))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadKontakData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Nomor", "Kategori"}, 0);
        dbHelper.getKontakList().forEach((kontak) -> {
            model.addRow(kontak);
        });
        tblKontak.setModel(model);
    }
    
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
    String keyword = jCari.getText();
    DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Nomor", "Kategori"}, 0);
    
    dbHelper.getKontakList().forEach((kontak) -> {
        if (kontak[1].contains(keyword) || kontak[2].contains(keyword)) {
            model.addRow(kontak);
          }
        });
        tblKontak.setModel(model);
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    String nama = jNama.getText();
    String nomor = jNomor.getText();
    String kategori = (String) jcbList.getSelectedItem();
    
    if (!nama.isEmpty() && !nomor.isEmpty() && kategori != null) {
        dbHelper.tambahKontak(nama, nomor, kategori);
        loadKontakData(); // Refresh data di JTable
        jNama.setText("");
        jNomor.setText("");
        jcbList.setSelectedIndex(0);
    } else {
        JOptionPane.showMessageDialog(this, "Kontak Berhasil Ditambahkan!");
    }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEksporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEksporActionPerformed
    btnEkspor.addActionListener(e -> eksporKeCSV());
    }//GEN-LAST:event_btnEksporActionPerformed

    private void btnImporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImporActionPerformed
    btnImpor.addActionListener(e -> imporDariCSV());
    }//GEN-LAST:event_btnImporActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
    int selectedRow = tblKontak.getSelectedRow();
    
    // Pastikan ada baris yang dipilih
    if (selectedRow != -1) {
        int id = Integer.parseInt((String) tblKontak.getValueAt(selectedRow, 0)); // Ambil ID dari kolom pertama
        String nama = jNama.getText();
        String nomor = jNomor.getText();
        String kategori = (String) jcbList.getSelectedItem();
        
        // Validasi data sebelum diperbarui
        if (!nama.isEmpty() && !nomor.isEmpty() && kategori != null) {
            dbHelper.updateKontak(id, nama, nomor, kategori); // Update kontak di database
            loadKontakData(); // Refresh data di JTable
            JOptionPane.showMessageDialog(this, "Kontak berhasil diperbarui!");

            // Kosongkan input setelah diperbarui
            jNama.setText("");
            jNomor.setText("");
            jcbList.setSelectedIndex(0);
        }
        } else {
        JOptionPane.showMessageDialog(this, "Pilih Kontak yang Ingin Diedit!");
    
    }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
    int selectedRow = tblKontak.getSelectedRow();
    if (selectedRow != -1) {
        int id = Integer.parseInt((String) tblKontak.getValueAt(selectedRow, 0));
        dbHelper.hapusKontak(id);
        loadKontakData(); // Refresh data di JTable
    } else {
        JOptionPane.showMessageDialog(this, "Kontak Berhasil Dihapus!");
    }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void jListKategoriValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListKategoriValueChanged
    jListKategori.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = jListKategori.getSelectedValue();
                jcbList.setSelectedItem(selectedCategory); // Sinkronkan dengan JComboBox
            }
        });
    }//GEN-LAST:event_jListKategoriValueChanged

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PengelolaanKontakFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEkspor;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImpor;
    private javax.swing.JButton btnTambah;
    private javax.swing.JTextField jCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jListKategori;
    private javax.swing.JTextField jNama;
    private javax.swing.JTextField jNomor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jcbList;
    private javax.swing.JTable tblKontak;
    // End of variables declaration//GEN-END:variables
}
