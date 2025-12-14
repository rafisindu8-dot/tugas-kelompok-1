import java.time.LocalDate;
import java.util.List;

// Untuk pemisahan tanggung jawab dan ekstensibilitas
public interface Searchable<T> {
    List<T> search(String origin, String destination, LocalDate date);
    List<T> search(String location, LocalDate checkIn, LocalDate checkOut);
}