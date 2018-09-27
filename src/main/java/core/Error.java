package core;

public class Error extends RuntimeException {

    public Error(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
