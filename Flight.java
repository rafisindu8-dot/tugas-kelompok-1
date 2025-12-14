import java.time.LocalDate;

public class Flight {
    // 🔒 Enkapsulasi: semua field private
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate date;
    private int totalSeats;
    private int bookedSeats;
    private double price;

    public Flight(String flightNumber, String origin, String destination,
                  LocalDate date, int totalSeats, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.totalSeats = totalSeats;
        this.bookedSeats = 0;
        this.price = price;
    }

    // Getter & Setter
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDate getDate() { return date; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return totalSeats - bookedSeats; }
    public double getPrice() { return price; }

    public boolean book(int quantity) {
        if (quantity <= getAvailableSeats()) {
            bookedSeats += quantity;
            return true;
        }
        return false;
    }

    public boolean isAvailable() {
        return getAvailableSeats() > 0;
    }

    @Override
    public String toString() {
        return String.format("✈️  %s | %s → %s | %s | Seats: %d/%d | Rp%,.0f",
                flightNumber, origin, destination, date,
                getAvailableSeats(), totalSeats, price);
    }
}