import java.util.Random;

// 🔒 Final class: tidak boleh diwariskan
public final class ReservationUtility {

    private static final Random RANDOM = new Random();

    // Private constructor → tidak bisa diinstansiasi
    private ReservationUtility() {}

    // ✅ Ekspresi Lambda & Stream digunakan di sini
    public static int generateConfirmationNumber() {
        return RANDOM.ints(100000, 999999 + 1) // range 100000–999999
                .limit(1)
                .findFirst()
                .orElse(100000);
    }

    // Contoh penggunaan Lambda untuk validasi
    public static boolean isValidName(String name) {
        return name != null &&
               !name.trim().isEmpty() &&
               name.chars().allMatch(c -> Character.isLetter(c) || Character.isWhitespace(c));
    }

    public static boolean isFutureDate(java.time.LocalDate date) {
        return date != null && date.isAfter(java.time.LocalDate.now());
    }
}