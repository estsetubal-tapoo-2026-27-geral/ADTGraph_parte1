package tapoo.metro;

/** Reports violations of metro domain rules. */
public class InvalidMetroException extends RuntimeException {
    public InvalidMetroException(String message) { super(message); }
}
