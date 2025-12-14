import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

// Mengimplementasikan: Searchable, koleksi, validasi, eksepsi, pattern matching
public class TravelApp implements Searchable<Object> {
    private final List<Flight> availableFlights = new ArrayList<>();
    private final List<Hotel> availableHotels = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public TravelApp() {
        initSampleData();
    }

    // 📦 Inisialisasi data contoh
    private void initSampleData() {
        // Penerbangan contoh
        availableFlights.addAll(Arrays.asList(
                new Flight("GA123", "Jakarta", "Bali", LocalDate.of(2025, 12, 20), 100, 1_500_000),
                new Flight("SJ456", "Surabaya", "Jakarta", LocalDate.of(2025, 12, 21), 80, 950_000),
                new Flight("ID789", "Bali", "Yogyakarta", LocalDate.of(2025, 12, 22), 60, 1_200_000)
        ));

        // Hotel contoh
        availableHotels.addAll(Arrays.asList(
                new Hotel("HTL001", "Grand Bali Resort", "Bali", 50, 2_000_000),
                new Hotel("HTL002", "Jakarta Central Hotel", "Jakarta", 30, 1_500_000),
                new Hotel("HTL003", "Yogyakarta Heritage", "Yogyakarta", 25, 1_000_000)
        ));
    }

    // 🔍 Pencarian Penerbangan
    public List<Flight> searchFlights(String origin, String destination, LocalDate date) {
        return availableFlights.stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin) &&
                        f.getDestination().equalsIgnoreCase(destination) &&
                        f.getDate().equals(date) &&
                        f.isAvailable())
                .collect(Collectors.toList());
    }

    // 🔍 Pencarian Hotel
    public List<Hotel> searchHotels(String location, LocalDate checkIn, LocalDate checkOut) {
        return availableHotels.stream()
                .filter(h -> h.getLocation().equalsIgnoreCase(location) && h.isAvailable())
                .collect(Collectors.toList());
    }

    // ✅ Pemesanan Penerbangan
    public FlightReservation bookFlight(Flight flight, String passengerName, int seats) {
        if (!ReservationUtility.isValidName(passengerName)) {
            throw new IllegalArgumentException("Nama penumpang tidak valid!");
        }
        if (seats <= 0 || seats > flight.getAvailableSeats()) {
            throw new IllegalArgumentException("Jumlah kursi tidak valid!");
        }

        if (flight.book(seats)) {
            int confNum = ReservationUtility.generateConfirmationNumber();
            FlightReservation res = new FlightReservation(confNum, flight, passengerName, seats);
            reservations.add(res);
            return res;
        }
        return null;
    }

    // ✅ Pemesanan Hotel
    public HotelReservation bookHotel(Hotel hotel, String guestName, int rooms,
                                      LocalDate checkIn, LocalDate checkOut) {
        if (!ReservationUtility.isValidName(guestName)) {
            throw new IllegalArgumentException("Nama tamu tidak valid!");
        }
        if (rooms <= 0 || rooms > hotel.getAvailableRooms()) {
            throw new IllegalArgumentException("Jumlah kamar tidak valid!");
        }

        if (hotel.book(rooms)) {
            int confNum = ReservationUtility.generateConfirmationNumber();
            HotelReservation res = new HotelReservation(confNum, hotel, guestName, rooms, checkIn, checkOut);
            reservations.add(res);
            return res;
        }
        return null;
    }

    // ❌ Pembatalan Reservasi — ✅ Pattern Matching (Java 16+)
    public boolean cancelReservation(int confirmationNumber) {
        Optional<Reservation> found = reservations.stream()
                .filter(r -> !r.isCancelled() && r.getConfirmationNumber() == confirmationNumber)
                .findFirst();

        if (found.isPresent()) {
            Reservation res = found.get();
            res.cancel();

            // 🔍 Pattern Matching dengan instanceof (Java 16+)
            // Di method cancelReservation():
if (res instanceof FlightReservation fr) {
    System.out.println("✈️ Penerbangan dibatalkan: " + fr.getFlight().getFlightNumber());
} else if (res instanceof HotelReservation hr) {
    System.out.println("🏨 Hotel dibatalkan: " + hr.getHotel().getName());
}
            return true;
        }
        return false;
    }

    // 👁️ Lihat Semua Pemesanan
    public void displayAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("📭 Tidak ada pemesanan.");
            return;
        }
        System.out.println("\n📋 SEMUA PEMESANAN:");
        System.out.println("=".repeat(50));
        reservations.stream()
                .filter(r -> !r.isCancelled())
                .forEach(Reservation::display);
    }

    // Implementasi dari Searchable — untuk fleksibilitas (opsional)
    @Override
    public List<Object> search(String origin, String destination, LocalDate date) {
        List<Object> result = new ArrayList<>();
        result.addAll(searchFlights(origin, destination, date));
        return result;
    }

    @Override
    public List<Object> search(String location, LocalDate checkIn, LocalDate checkOut) {
        List<Object> result = new ArrayList<>();
        result.addAll(searchHotels(location, checkIn, checkOut));
        return result;
    }
}