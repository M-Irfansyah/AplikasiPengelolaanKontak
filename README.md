# Aplikasi Pengelolaan Kontak
Latihan 3 - M. Irfansyah (2210010176)

## Deskripsi
Aplikasi Pengelolaan Kontak adalah sebuah aplikasi desktop sederhana yang dibuat menggunakan **Java Swing** dan **SQLite**. Aplikasi ini digunakan untuk mengelola data kontak dengan fitur **CRUD** (Create, Read, Update, Delete), serta dilengkapi dengan kemampuan untuk **ekspor** dan **impor** data dalam format **CSV**.

## Fitur Utama
1. **Tambah Kontak**: Menambahkan data kontak baru dengan informasi seperti nama, nomor telepon, dan kategori.
2. **Edit Kontak**: Mengubah informasi kontak yang sudah tersimpan.
3. **Hapus Kontak**: Menghapus kontak dari database.
4. **Tampilkan Daftar Kontak**: Menampilkan semua kontak di tabel.
5. **Pencarian Kontak**: Mencari kontak berdasarkan nama atau nomor telepon.
6. **Kategori Kontak**: Mengelompokkan kontak berdasarkan kategori (Keluarga, Teman, Kerja).
7. **Ekspor CSV**: Menyimpan daftar kontak ke file CSV.
8. **Impor CSV**: Menambahkan daftar kontak dari file CSV.

## Teknologi yang Digunakan
- **Java Swing** untuk GUI.
- **SQLite** sebagai basis data lokal.
- **JDBC** untuk menghubungkan Java dengan SQLite.

## Penjelasan Kode

1. Konstruktor Utama untuk Frame GUI
Menguji dan menginisialisasi komponen GUI serta memuat data kontak dari database saat aplikasi dibuka.
```
public PengelolaanKontakFrame() {
    initComponents(); // Menginisialisasi komponen GUI
    dbHelper = new db_Kontak(); // Inisialisasi dbHelper sebagai penghubung ke database
    loadKontakData(); // Memuat data kontak dari database saat frame dibuka pertama kali
}
```
2. Menambahkan Event Listener untuk Tombol-Tombol


```
btnTambah.addActionListener(this::btnTambahActionPerformed);
btnEdit.addActionListener(this::btnEditActionPerformed);
btnHapus.addActionListener(this::btnHapusActionPerformed);
btnCari.addActionListener(this::btnCariActionPerformed);
btnEkspor.addActionListener(e -> eksporKeCSV()); // Ekspor kontak ke file CSV
btnImpor.addActionListener(e -> imporDariCSV()); // Impor kontak dari file CSV
```

3. Menambahkan Listener untuk Pilihan di Tabel


```
tblKontak.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting() && tblKontak.getSelectedRow() != -1) {
        int selectedRow = tblKontak.getSelectedRow();
        // Mengisi form input berdasarkan baris yang dipilih
        jNama.setText((String) tblKontak.getValueAt(selectedRow, 1)); // Kolom Nama
        jNomor.setText((String) tblKontak.getValueAt(selectedRow, 2)); // Kolom Nomor
        jcbList.setSelectedItem((String) tblKontak.getValueAt(selectedRow, 3)); // Kolom Kategori
    }
});
```
4. Ekspor Data Kontak ke File CSV


```
private void eksporKeCSV() {
    try (FileWriter writer = new FileWriter("kontak.csv")) {
        // Menulis header kolom
        writer.append("ID,Nama,Nomor,Kategori\n");

        // Menulis data kontak ke file CSV
        for (String[] kontak : dbHelper.getKontakList()) {
            writer.append(String.join(",", kontak));
            writer.append("\n");
        }

        JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke kontak.csv!");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengekspor data!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

5. Impor Data Kontak dari File CSV


```
private void imporDariCSV() {
    try (BufferedReader reader = new BufferedReader(new FileReader("kontak.csv"))) {
        String line;
        boolean header = true; // Penanda untuk melewati baris header

        while ((line = reader.readLine()) != null) {
            if (header) { // Lewati header
                header = false;
                continue;
            }

            // Memecah data baris CSV ke kolom
            String[] data = line.split(",");
            if (data.length == 4) { // Pastikan format data sesuai
                String nama = data[1];
                String nomor = data[2];
                String kategori = data[3];

                dbHelper.tambahKontak(nama, nomor, kategori); // Tambah kontak ke database
            }
        }

        JOptionPane.showMessageDialog(this, "Data berhasil diimpor dari kontak.csv!");
        loadKontakData(); // Refresh data di JTable
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengimpor data!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

6. Memuat Data Kontak dari Database ke JTable

```
 loadKontakData() {
    DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Nomor", "Kategori"}, 0);
    dbHelper.getKontakList().forEach((kontak) -> {
        model.addRow(kontak); // Tambahkan setiap baris data ke model tabel
    });
    tblKontak.setModel(model); // Set model ke JTable
}
```

7. Mencari Kontak Berdasarkan Nama atau Nomor Telepon


```
private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {
    String keyword = jCari.getText(); // Kata kunci pencarian
    DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Nomor", "Kategori"}, 0);

    dbHelper.getKontakList().forEach((kontak) -> {
        // Menambahkan kontak ke model jika cocok dengan kata kunci
        if (kontak[1].contains(keyword) || kontak[2].contains(keyword)) {
            model.addRow(kontak);
        }
    });
    tblKontak.setModel(model); // Tampilkan hasil pencarian di JTable
}
```

8. Tambah Kontak Baru


```
private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
    String nama = jNama.getText();
    String nomor = jNomor.getText();
    String kategori = (String) jcbList.getSelectedItem();

    if (!nama.isEmpty() && !nomor.isEmpty() && kategori != null) { // Validasi input
        dbHelper.tambahKontak(nama, nomor, kategori); // Tambah ke database
        loadKontakData(); // Refresh data
        jNama.setText(""); // Kosongkan form input
        jNomor.setText("");
        jcbList.setSelectedIndex(0);
    } else {
        JOptionPane.showMessageDialog(this, "Kontak berhasil ditambahkan!");
    }
}
```
9. Edit Kontak yang Dipilih


```
private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
    int selectedRow = tblKontak.getSelectedRow(); // Baris yang dipilih di JTable

    if (selectedRow != -1) {
        int id = Integer.parseInt((String) tblKontak.getValueAt(selectedRow, 0)); // Ambil ID kontak
        String nama = jNama.getText();
        String nomor = jNomor.getText();
        String kategori = (String) jcbList.getSelectedItem();

        if (!nama.isEmpty() && !nomor.isEmpty() && kategori != null) { // Validasi input
            dbHelper.updateKontak(id, nama, nomor, kategori); // Update kontak di database
            loadKontakData(); // Refresh data
            JOptionPane.showMessageDialog(this, "Kontak berhasil diperbarui!");
            jNama.setText(""); // Kosongkan form input
            jNomor.setText("");
            jcbList.setSelectedIndex(0);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Pilih kontak yang ingin diedit!");
    }
}
```
10. Hapus Kontak yang Dipilih


```
private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
    int selectedRow = tblKontak.getSelectedRow(); // Baris yang dipilih di JTable
    if (selectedRow != -1) {
        int id = Integer.parseInt((String) tblKontak.getValueAt(selectedRow, 0)); // Ambil ID kontak
        dbHelper.hapusKontak(id); // Hapus dari database
        loadKontakData(); // Refresh data
    } else {
        JOptionPane.showMessageDialog(this, "Pilih kontak yang ingin dihapus!");
    }
}
```

11. Kelas db_Kontak


```
public class db_Kontak {
    private Connection conn; // Objek untuk koneksi ke database

    - Konstruktor untuk membuat koneksi ke database
    public db_Kontak() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:kontak.db");
            createTable(); // Membuat tabel jika belum ada
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
```
- Membuat tabel "kontak" jika belum ada
```
    private void createTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS kontak ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nama TEXT NOT NULL, "
                + "nomor TEXT NOT NULL, "
                + "kategori TEXT NOT NULL)";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreateTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
```
- Menambahkan kontak baru
```
    public void tambahKontak(String nama, String nomor, String kategori) {
        String sqlInsert = "INSERT INTO kontak (nama, nomor, kategori) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, nomor);
            pstmt.setString(3, kategori);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
```
- Menghapus kontak berdasarkan ID
```
    public void hapusKontak(int id) {
        String sqlDelete = "DELETE FROM kontak WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
```

 - Memperbarui kontak berdasarkan ID
```
    public void updateKontak(int id, String nama, String nomor, String kategori) {
        String sqlUpdate = "UPDATE kontak SET nama = ?, nomor = ?, kategori = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, nomor);
            pstmt.setString(3, kategori);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
```

- Mengambil semua data kontak dari database
```
    public List<String[]> getKontakList() {
        List<String[]> kontakList = new ArrayList<>();
        String sqlSelect = "SELECT * FROM kontak";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {
            while (rs.next()) {
                kontakList.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("nama"),
                        rs.getString("nomor"),
                        rs.getString("kategori")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kontakList;
    }
}
```

# Tampilan Pada Saat Aplikasi Di Jalankan
![Pengelolaan Kontak](https://github.com/user-attachments/assets/178021cd-36bc-400e-8111-a7ae766b0968)


## Indikator Penilaian:

| No  | Komponen         |  Persentase  |
| :-: | --------------   |   :-----:    |
|  1  | Komponen GUI     |    20    |
|  2  | Logika Program   |    30    |
|  3  |  Events          |    15    |
|  4  | Kesesuaian UI    |    15    |
|  5  | Memenuhi Variasi |    20    |
|     | TOTAL        | 100 |

