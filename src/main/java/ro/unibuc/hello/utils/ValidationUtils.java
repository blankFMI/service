package ro.unibuc.hello.utils;

/**
 * Minimal validation helper intended as a **template**.
 * <p>
 * ➡  Add your own domain‑specific checks, compose rules with the
 *     {@link ValidationRule} functional interface, or delete anything you
 *     don't need.  All sample methods are <strong>NO‑OP or stub</strong> so the
 *     project compiles even if you remove them later.
 */
public final class ValidationUtils {

    private ValidationUtils() {}

    /* ----------------------------------------------------------------------
     *  Functional Rule interface (keep -> you can chain lambdas easily)
     * ---------------------------------------------------------------------- */
    @FunctionalInterface
    public interface ValidationRule<T> {
        /**
         * @return A formatted error string ("%s …") or {@code null} when valid.
         */
        String validate(T value);

        default ValidationRule<T> and(ValidationRule<T> other) {
            return value -> {
                String err = this.validate(value);
                return err != null ? err : other.validate(value);
            };
        }
    }

    /* ----------------------------------------------------------------------
     *  Ready‑made examples (use / delete as you like)
     * ---------------------------------------------------------------------- */

    public static void exists(String fieldName, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    public static <T> ValidationRule<T> notNull() {
        return v -> v == null ? "%s is required" : null;
    }

    public static ValidationRule<String> minLength(int min) {
        return v -> v != null && v.length() < min ? "%s must be at least " + min + " chars" : null;
    }

    // --- add more rules here ------------------------------------------------

}
