package data.access.validators;

import model.Client;

/**
 * Implement the validation method for age.
 * If the age is not within the range of 14 to 100, throw an exception.
 */
public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 14;
    private static final int MAX_AGE = 100;

    public void validate(Client t) {

        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected!");
        }
    }
}
