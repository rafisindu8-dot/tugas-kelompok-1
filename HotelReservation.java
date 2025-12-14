import java.time.LocalDate;

// HAPUS "implements Bookable"
public class HotelReservation extends Reservation {
    private Hotel hotel;
    private String guestName;
    private int rooms;
    private LocalDate checkIn;
    private LocalDate checkOut;

    // ✅ KONSTRUKTOR
    public HotelReservation(int confirmationNumber, Hotel hotel,
                            String guestName, int rooms,
                            LocalDate checkIn, LocalDate checkOut) {
        super(confirmationNumber);
        this.hotel = hotel;
        this.guestName = guestName;
        this.rooms = rooms;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // ✅ Override display()
    @Override
    public void display() {
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        System.out.println("🏨 HOTEL RESERVATION");
        System.out.println("Confirmation #: " + confirmationNumber);
        System.out.println("Guest: " + guestName);
        System.out.println("Hotel: " + hotel.getName() + " (" + hotel.getHotelId() + ")");
        System.out.println("Location: " + hotel.getLocation());
        System.out.println("Check-in: " + checkIn + " | Check-out: " + checkOut + " (" + nights + " nights)");
        System.out.println("Rooms: " + rooms);
        System.out.printf("Total: Rp%,.0f%n", rooms * nights * hotel.getPricePerNight());
        System.out.println("Status: " + (cancelled ? "❌ Cancelled" : "✅ Confirmed"));
        System.out.println("-".repeat(40));
    }

    public Hotel getHotel() { return hotel; }
}