package data.access.validators;

/**
 * Interface containing the method definition for the validator.
 */
public interface Validator<T> {

    public void validate(T t);
}
