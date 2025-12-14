import java.time.LocalDate;

public class Hotel {
    private String hotelId;
    private String name;
    private String location;
    private int totalRooms;
    private int bookedRooms;
    private double pricePerNight;

    public Hotel(String hotelId, String name, String location,
                 int totalRooms, double pricePerNight) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.totalRooms = totalRooms;
        this.bookedRooms = 0;
        this.pricePerNight = pricePerNight;
    }

    // Getter & Setter
    public String getHotelId() { return hotelId; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getTotalRooms() { return totalRooms; }
    public int getAvailableRooms() { return totalRooms - bookedRooms; }
    public double getPricePerNight() { return pricePerNight; }

    public boolean book(int quantity) {
        if (quantity <= getAvailableRooms()) {
            bookedRooms += quantity;
            return true;
        }
        return false;
    }

    public boolean isAvailable() {
        return getAvailableRooms() > 0;
    }

    @Override
    public String toString() {
        return String.format("🏨 %s | %s | %s | Rooms: %d/%d | Rp%,.0f/night",
                hotelId, name, location,
                getAvailableRooms(), totalRooms, pricePerNight);
    }
}