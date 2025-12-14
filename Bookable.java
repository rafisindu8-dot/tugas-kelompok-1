// Mengimplementasikan konsep: Antarmuka, Enkapsulasi, Polimorfisme
public interface Bookable {
    boolean book(int quantity);
    boolean cancel();
    boolean isAvailable();
    int getConfirmationNumber();
}