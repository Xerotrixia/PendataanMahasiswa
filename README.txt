========================================
APLIKASI PENDATAAN MAHASISWA (v2.0 - dengan Login)
Universitas Bina Sarana Informatika
========================================

DESKRIPSI:
Aplikasi desktop untuk pendataan mahasiswa dengan fitur CRUD (Create, Read, Update, Delete),
sekarang dengan sistem LOGIN dua peran (User & Admin) dan data tersimpan permanen
di database SQLite (bukan lagi hilang saat aplikasi ditutup).

FITUR BARU DI VERSI INI:
✓ Halaman Login dengan validasi username & password (password di-hash SHA-256)
✓ Dua peran: USER dan ADMIN, masing-masing diarahkan ke halaman berbeda
✓ Data mahasiswa tersimpan permanen di file database "pendataan.db" (SQLite)
✓ Tombol Logout kembali ke halaman Login

FITUR USER (MainForm):
✓ Tambah, ubah, hapus, dan lihat data mahasiswa (NIM, Nama Lengkap, Program Studi)
✓ Data langsung tersimpan ke database

FITUR ADMIN (AdminForm) - 4 Tab:
✓ Tab "Data Mahasiswa"  : CRUD penuh + tombol Export ke CSV (bisa dibuka di Excel)
✓ Tab "Kelola User"     : tambah, ubah/reset password, hapus akun user maupun admin
✓ Tab "Log Aktivitas"   : riwayat siapa melakukan apa dan kapan (tambah/ubah/hapus/login/logout)
✓ Tab "Statistik"       : total mahasiswa & jumlah mahasiswa per Program Studi

AKUN DEFAULT (bisa diubah lewat tab Kelola User setelah login sebagai admin):
  - Admin  -> username: admin   | password: admin123
  - User   -> username: user    | password: user123

========================================
LANGKAH PENTING SEBELUM MENJALANKAN APLIKASI
========================================

Aplikasi ini butuh driver JDBC untuk SQLite (file .jar) yang TIDAK disertakan
dalam paket ini (karena ukurannya perlu diunduh terpisah). Ikuti langkah berikut:

1. UNDUH DRIVER SQLITE JDBC:
   - Buka: https://github.com/xerial/sqlite-jdbc/releases
   - Unduh file bernama kira-kira "sqlite-jdbc-3.46.0.0.jar" (versi terbaru juga boleh)
   - Simpan file .jar tersebut ke dalam folder "lib" di dalam folder project ini
     (folder "lib" sudah disediakan, saat ini masih kosong)

2. DAFTARKAN LIBRARY DI NETBEANS (WAJIB, supaya bisa di-Run maupun di-Build jadi .jar):
   - Buka project ini di NetBeans (lihat langkah di bawah)
   - Klik kanan project "PendataanMahasiswa" -> "Properties"
   - Pilih kategori "Libraries" di sebelah kiri
   - Klik "Add JAR/Folder..." -> arahkan ke file jar yang sudah kamu simpan di folder "lib"
   - Klik OK
   - (Project sudah dikonfigurasi menunjuk ke lib/sqlite-jdbc-3.46.0.0.jar secara default;
     kalau nama file jar yang kamu unduh berbeda, cukup sesuaikan lewat dialog Libraries ini)

Kalau langkah ini dilewati, aplikasi akan menampilkan pesan error
"Driver SQLite tidak ditemukan" saat pertama kali dijalankan.

========================================
CARA EKSTRAK DAN MEMBUKA DI NETBEANS
========================================

1. EKSTRAK FILE ZIP:
   - Klik kanan pada file "PendataanMahasiswa.zip" -> "Extract All..." (Windows)
     atau: unzip PendataanMahasiswa.zip

2. BUKA PROJECT DI NETBEANS:
   - Buka NetBeans IDE -> File -> Open Project
   - Arahkan ke folder "PendataanMahasiswa" (hasil ekstrak) -> Open Project

3. TAMBAHKAN DRIVER SQLITE (lihat bagian "LANGKAH PENTING" di atas)

4. JALANKAN APLIKASI:
   - Klik kanan project -> "Run" (atau F6)
   - Halaman Login akan muncul lebih dulu
   - Database "pendataan.db" akan otomatis dibuat di folder project saat pertama kali dijalankan

5. LIHAT/EDIT DESAIN GUI:
   - LoginForm.java dan MainForm.java punya file .form pasangannya, jadi tab "Design"
     bisa dipakai seperti biasa (drag-and-drop di GUI Builder)
   - AdminForm.java DIBUAT LANGSUNG LEWAT KODE (tanpa file .form) karena strukturnya
     memakai tab (JTabbedPane) dan beberapa tabel dinamis yang lebih mudah diatur lewat kode.
     Tab "Design" tidak tersedia untuk file ini, tapi tetap bisa diedit normal lewat tab "Source".

========================================
STRUKTUR FOLDER PROJECT
========================================
PendataanMahasiswa/
├── src/
│   └── aplikasi/
│       ├── LoginForm.java / LoginForm.form   (Halaman login)
│       ├── MainForm.java / MainForm.form     (Halaman User)
│       ├── AdminForm.java                    (Halaman Admin, 4 tab)
│       ├── DatabaseHelper.java               (Koneksi & setup database SQLite)
│       └── PasswordUtil.java                 (Hashing password SHA-256)
├── lib/
│   └── (letakkan sqlite-jdbc-x.x.x.jar di sini)
├── nbproject/
│   ├── project.properties     (sudah dikonfigurasi menunjuk ke lib/sqlite-jdbc-3.46.0.0.jar)
│   └── project.xml
├── build.xml
├── manifest.mf                 (Main-Class sekarang aplikasi.LoginForm)
├── pendataan.db                (otomatis dibuat saat pertama kali Run, jangan dihapus manual)
└── README.txt                  (File ini)

========================================
TROUBLESHOOTING
========================================

A. "Driver SQLite tidak ditemukan" saat Run:
   - Pastikan file .jar sqlite-jdbc sudah ada di folder "lib"
   - Pastikan sudah didaftarkan lewat Project Properties -> Libraries (lihat langkah di atas)
   - Clean & Build ulang project

B. Lupa password admin:
   - Hapus file "pendataan.db" di folder project (data mahasiswa akan ikut terhapus!)
   - Jalankan ulang aplikasi, akun default admin/admin123 akan otomatis dibuat lagi
   - Cara lebih aman: minta admin lain untuk reset password lewat tab "Kelola User"

C. Tab "Design" tidak muncul untuk AdminForm.java:
   - Ini normal/disengaja, karena AdminForm dibuat lewat kode (lihat penjelasan di atas)
   - Edit lewat tab "Source" seperti file .java biasa

D. Error kompilasi terkait "org.sqlite":
   - Artinya driver SQLite belum terdaftar sebagai library, ikuti "LANGKAH PENTING" di atas

========================================
Version: 2.0 (dengan Login & Database)
Last Updated: 2026
========================================
