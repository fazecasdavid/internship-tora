package cubs.training.validation;

import cubs.training.data.Person;

import java.util.regex.Pattern;

public class PersonValidator implements Validator<Person> {

    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Za-z][A-Za-z]+|-");
    private static final Pattern ID_PATTERN = Pattern.compile("[1-8][0-9]{12}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\b\\D[a-zA-Z._]*@\\w+\\.com\\b");

    private static boolean hasValidNames(final Person person) {
        return isValidName(person.getFirstName()) && isValidName(person.getMiddleName()) && isValidName(person.getLastName());
    }

    private static boolean isValidName(final String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    private static boolean hasValidId(final Person person) {
        return ID_PATTERN.matcher(person.getId()).matches();
    }

    private static boolean hasValidEmail(final Person person) {
        return EMAIL_PATTERN.matcher(person.getEmail()).matches();
    }

    @Override
    public boolean isValid(Person person) {
        return hasValidNames(person) && hasValidId(person) && hasValidEmail(person);
    }
}