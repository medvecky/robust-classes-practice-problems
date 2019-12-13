package model.exceptions;

public class NoIngredientException extends Exception {
    public NoIngredientException() {
    }

    public NoIngredientException(String message) {
        super(message);
    }
}
