package ro.unibuc.hello.utils;

import lombok.*;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * <h2>DatabaseUtils (template)</h2>
 * <p>
 * A stripped‑down helper class you can extend with whatever database‑specific
 * utilities you need later.  For now it only contains an in‑memory ID generator
 * that is safe to call but <strong>does nothing</strong> until you store
 * templates via {@link #setTemplate(String, String)}.
 * </p>
 */
public final class DatabaseUtils {

    /**
     * Example composite key you can re‑use (or delete) once your new domain
     * model solidifies.
     */
    @Getter @Setter @ToString @EqualsAndHashCode
    @NoArgsConstructor @AllArgsConstructor
    public static class CompositeKey implements Serializable {
        private String leftId;
        private String rightId;
        // static builder left for you to adapt
        public static CompositeKey of(String left, String right) {
            return new CompositeKey(left, right);
        }
    }

    private DatabaseUtils() { }

    // ---------------------------------------------------------------------
    //  In‑memory ID generator (safe placeholder until real IDs come in)
    // ---------------------------------------------------------------------
    private static final Map<String, Pair<String, Integer>> registry = new ConcurrentHashMap<>();

    /**
     * Register a template such as <code>user-%s</code>.  The integer placeholder
     * will be filled with an auto‑incrementing counter.
     */
    public static void setTemplate(String key, String template) {
        registry.put(key, Pair.of(template, 0));
    }

    /**
     * Get the <code>n</code>‑th ID for a template, without bumping the counter.
     */
    public static String getId(String key, int index) {
        Pair<String, Integer> p = registry.get(key);
        if (p == null) throw new IllegalStateException(key + " not registered");
        return String.format(p.getFirst(), index);
    }

    /**
     * Generate and return the next ID in sequence for <code>key</code>.
     */
    public static String generateId(String key) {
        Pair<String, Integer> p = registry.computeIfAbsent(key, k -> Pair.of(k + "-%s", 0));
        registry.put(key, Pair.of(p.getFirst(), p.getSecond() + 1));
        return String.format(p.getFirst(), p.getSecond());
    }
}
