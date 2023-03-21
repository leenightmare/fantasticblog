package cc.lijad.fantasticblog.exception;

/**
 * @author ljd
 * @create 2023/2/26 14:30
 */
public class ArgumentException extends RuntimeException {
    public ArgumentException() {
        super();
    }

    public ArgumentException(String message) {
        super(message);
    }
}
