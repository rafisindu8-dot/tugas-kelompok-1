import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

// 🔁 Mengimplementasikan: Main Driver, Menu Loop, Input/Output, Penanganan Eksepsi
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TravelApp app = new TravelApp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("✈️ 🏨 SELAMAT DATANG DI TRAVEL BOOKING APP");
        System.out.println("Platform pemesanan perjalanan terpercaya!");

        while (true) {
            try {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("🎯 MENU UTAMA");
                System.out.println("1. 🔍 Cari Penerbangan");
                System.out.println("2. 🔍 Cari Hotel");
                System.out.println("3. 📋 Lihat Semua Pemesanan");
                System.out.println("4. ❌ Batalkan Pemesanan");
                System.out.println("0. 🚪 Keluar");
                System.out.print("Pilih menu (0-4): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // konsumsi newline

                switch (choice) {
                    case 1 -> searchAndBookFlight(scanner, app, formatter);
                    case 2 -> searchAndBookHotel(scanner, app, formatter);
                    case 3 -> app.displayAllReservations();
                    case 4 -> cancelReservation(scanner, app);
                    case 0 -> {
                        System.out.println("👋 Terima kasih telah menggunakan layanan kami!");
                        return;
                    }
                    default -> System.out.println("⚠️ Pilihan tidak valid. Silakan coba lagi.");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Input harus berupa angka. Silakan ulangi.");
                scanner.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println("❗ Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    // 🔍 & 📝 Pencarian + Pemesanan Penerbangan
    private static void searchAndBookFlight(Scanner scanner, TravelApp app, DateTimeFormatter fmt) {
        System.out.println("\n✈️ PENCARIAN PENERBANGAN");
        System.out.print("Kota Asal         : ");
        String origin = scanner.nextLine().trim();
        System.out.print("Kota Tujuan       : ");
        String dest = scanner.nextLine().trim();
        System.out.print("Tanggal (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine().trim();

        try {
            LocalDate date = LocalDate.parse(dateStr, fmt);
            System.out.print("Jumlah Penumpang  : ");
            int passengers = scanner.nextInt();
            scanner.nextLine();

            List<Flight> results = app.searchFlights(origin, dest, date);

            if (results.isEmpty()) {
                System.out.println("📭 Tidak ada penerbangan tersedia.");
                return;
            }

            System.out.println("\n✅ PENERBANGAN DITEMUKAN:");
            for (int i = 0; i < results.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, results.get(i));
            }

            System.out.print("Pilih nomor penerbangan (1-" + results.size() + ") atau 0 untuk batal: ");
            int idx = scanner.nextInt() - 1;
            scanner.nextLine();

            if (idx < 0 || idx >= results.size()) {
                System.out.println("❌ Pilihan dibatalkan.");
                return;
            }

            Flight selected = results.get(idx);
            System.out.print("Nama Penumpang: ");
            String name = scanner.nextLine().trim();

            try {
                FlightReservation res = app.bookFlight(selected, name, passengers);
                if (res != null) {
                    System.out.println("\n🎉 ✅ PEMESANAN BERHASIL!");
                    res.display();
                } else {
                    System.out.println("❌ Gagal memesan — kursi tidak mencukupi.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }

        } catch (DateTimeParseException e) {
            System.out.println("❌ Format tanggal salah. Gunakan yyyy-MM-dd.");
        } catch (InputMismatchException e) {
            System.out.println("❌ Jumlah penumpang harus angka.");
            scanner.nextLine();
        }
    }

    // 🔍 & 📝 Pencarian + Pemesanan Hotel
    private static void searchAndBookHotel(Scanner scanner, TravelApp app, DateTimeFormatter fmt) {
        System.out.println("\n🏨 PENCARIAN HOTEL");
        System.out.print("Lokasi (Kota)     : ");
        String location = scanner.nextLine().trim();
        System.out.print("Check-in (yyyy-MM-dd): ");
        String inStr = scanner.nextLine().trim();
        System.out.print("Check-out (yyyy-MM-dd): ");
        String outStr = scanner.nextLine().trim();

        try {
            LocalDate checkIn = LocalDate.parse(inStr, fmt);
            LocalDate checkOut = LocalDate.parse(outStr, fmt);
            if (!checkOut.isAfter(checkIn)) {
                System.out.println("❌ Tanggal check-out harus setelah check-in.");
                return;
            }
            System.out.print("Jumlah Tamu       : ");
            int guests = scanner.nextInt();
            scanner.nextLine();

            List<Hotel> results = app.searchHotels(location, checkIn, checkOut);

            if (results.isEmpty()) {
                System.out.println("📭 Tidak ada hotel tersedia.");
                return;
            }

            System.out.println("\n✅ HOTEL DITEMUKAN:");
            for (int i = 0; i < results.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, results.get(i));
            }

            System.out.print("Pilih nomor hotel (1-" + results.size() + ") atau 0 untuk batal: ");
            int idx = scanner.nextInt() - 1;
            scanner.nextLine();

            if (idx < 0 || idx >= results.size()) {
                System.out.println("❌ Pilihan dibatalkan.");
                return;
            }

            Hotel selected = results.get(idx);
            System.out.print("Nama Tamu: ");
            String name = scanner.nextLine().trim();

            try {
                HotelReservation res = app.bookHotel(selected, name, guests, checkIn, checkOut);
                if (res != null) {
                    System.out.println("\n🎉 ✅ PEMESANAN BERHASIL!");
                    res.display();
                } else {
                    System.out.println("❌ Gagal memesan — kamar tidak mencukupi.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }

        } catch (DateTimeParseException e) {
            System.out.println("❌ Format tanggal salah. Gunakan yyyy-MM-dd.");
        } catch (InputMismatchException e) {
            System.out.println("❌ Jumlah tamu harus angka.");
            scanner.nextLine();
        }
    }

    // ❌ Pembatalan Reservasi
    private static void cancelReservation(Scanner scanner, TravelApp app) {
        System.out.println("\n❌ PEMBATALAN PEMESANAN");
        System.out.print("Masukkan nomor konfirmasi (6 digit): ");
        try {
            int confNum = scanner.nextInt();
            scanner.nextLine();

            if (String.valueOf(confNum).length() != 6) {
                System.out.println("❌ Nomor konfirmasi harus 6 digit.");
                return;
            }

            if (app.cancelReservation(confNum)) {
                System.out.println("✅ Pemesanan berhasil dibatalkan.");
            } else {
                System.out.println("❌ Nomor konfirmasi tidak ditemukan atau sudah dibatalkan.");
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Nomor konfirmasi harus berupa angka.");
            scanner.nextLine();
        }
    }
}