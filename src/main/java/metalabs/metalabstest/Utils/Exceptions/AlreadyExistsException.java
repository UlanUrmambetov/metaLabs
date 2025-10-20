package metalabs.metalabstest.Utils.Exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException() {
        super("Already taken");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
