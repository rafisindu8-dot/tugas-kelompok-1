import java.time.LocalDateTime;

// Hanya kelas abstrak — TIDAK sealed, TIDAK implements apa-apa
public abstract class Reservation {
    protected int confirmationNumber;
    protected LocalDateTime bookingTime;
    protected boolean cancelled;

    // Konstruktor tetap ada
    public Reservation(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
        this.bookingTime = LocalDateTime.now();
        this.cancelled = false;
    }

    // Method abstrak yang HARUS di-override
    public abstract void display();

    // Getter
    public int getConfirmationNumber() { return confirmationNumber; }
    public boolean isCancelled() { return cancelled; }

    // Cancel = void (konsisten di semua subclass)
    public void cancel() {
        this.cancelled = true;
    }
}