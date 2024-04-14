public class InvalidSymbolException extends Exception{

    public InvalidSymbolException() {
        super("There is no such symbol");
    }
    public InvalidSymbolException(String message) {
        super(message);
    }
}
