import java.time.LocalDate;

// HAPUS "implements Bookable"
public class FlightReservation extends Reservation {
    private Flight flight;
    private String passengerName;
    private int seats;
    private LocalDate travelDate;

    // ✅ TAMBAHKAN KONSTRUKTOR INI
    public FlightReservation(int confirmationNumber, Flight flight,
                             String passengerName, int seats) {
        super(confirmationNumber);
        this.flight = flight;
        this.passengerName = passengerName;
        this.seats = seats;
        this.travelDate = flight.getDate();
    }

    // ✅ WAJIB override display() dari Reservation
    @Override
    public void display() {
        System.out.println("✈️ FLIGHT RESERVATION");
        System.out.println("Confirmation #: " + confirmationNumber);
        System.out.println("Passenger: " + passengerName);
        System.out.println("Flight: " + flight.getFlightNumber());
        System.out.println("Route: " + flight.getOrigin() + " → " + flight.getDestination());
        System.out.println("Date: " + travelDate);
        System.out.println("Seats: " + seats);
        System.out.printf("Total: Rp%,.0f%n", seats * flight.getPrice());
        System.out.println("Status: " + (cancelled ? "❌ Cancelled" : "✅ Confirmed"));
        System.out.println("-".repeat(40));
    }

    // Getter untuk akses dari luar
    public Flight getFlight() { return flight; }
}