package cc.lijad.fantasticblog.exception;

/**
 * 认证失败
 *
 * @author ljd
 * @create 2023/2/28 22:13
 */
public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
