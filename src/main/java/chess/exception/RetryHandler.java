package chess.exception;

public class RetryHandler {

    public static void retryOnException(final Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            retryOnException(runnable);
        }
    }
}
