/*
 * Aplikasi Pendataan Mahasiswa
 * Universitas Bina Sarana Informatika
 */
package aplikasi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Helper class untuk koneksi database SQLite dan inisialisasi tabel.
 * Database disimpan sebagai file "pendataan.db" di folder project.
 */
public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:pendataan.db";
    private static Connection connection;

    /**
     * Mendapatkan koneksi aktif ke database (dibuat sekali, dipakai ulang).
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Driver SQLite tidak ditemukan!\n"
                        + "Pastikan file sqlite-jdbc-x.x.x.jar sudah ditambahkan\n"
                        + "ke Libraries project ini (klik kanan project > Properties > Libraries).",
                        "Driver Tidak Ditemukan",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    /**
     * Membuat tabel-tabel yang dibutuhkan jika belum ada,
     * dan membuat akun default (admin & user) jika tabel users masih kosong.
     */
    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username TEXT UNIQUE NOT NULL,"
                    + "password TEXT NOT NULL,"
                    + "role TEXT NOT NULL,"
                    + "nama_lengkap TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS mahasiswa ("
                    + "nim TEXT PRIMARY KEY,"
                    + "nama TEXT NOT NULL,"
                    + "prodi TEXT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS activity_log ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username TEXT,"
                    + "aktivitas TEXT,"
                    + "waktu TEXT)");

            // Buat akun default kalau tabel users masih kosong
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS cnt FROM users")) {
                if (rs.next() && rs.getInt("cnt") == 0) {
                    String sql = "INSERT INTO users (username, password, role, nama_lengkap) VALUES (?,?,?,?)";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, "admin");
                        ps.setString(2, PasswordUtil.hash("admin123"));
                        ps.setString(3, "admin");
                        ps.setString(4, "Administrator");
                        ps.executeUpdate();
                    }
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, "user");
                        ps.setString(2, PasswordUtil.hash("user123"));
                        ps.setString(3, "user");
                        ps.setString(4, "User Biasa");
                        ps.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal menyiapkan database:\n" + e.getMessage(),
                    "Error Database",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mencatat aktivitas user/admin ke tabel activity_log.
     */
    public static void logActivity(String username, String aktivitas) {
        String sql = "INSERT INTO activity_log (username, aktivitas, waktu) VALUES (?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, aktivitas);
            ps.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
