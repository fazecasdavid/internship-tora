package cubs.training.processor;

import cubs.training.data.Person;
import cubs.training.validation.RawPersonDataValidator;
import org.apache.commons.lang3.StringUtils;

public class RawPersonDataProcessor implements ValidDataProcessor<String> {

    private static final String FIELD_SEPARATOR = "|";
    private static final RawPersonDataValidator VALIDATOR = new RawPersonDataValidator(FIELD_SEPARATOR);
    private final PersonProcessor personProcessor;

    public RawPersonDataProcessor(PersonProcessor personProcessor) {
        this.personProcessor = personProcessor;
    }

    private final Object lock = new Object();
    @Override
    public void process(String rawPersonData) {
        final Person person = createPerson(rawPersonData);
        personProcessor.processIfValid(person);
    }

    @Override
    public boolean isValid(String rawPersonData) {
        return VALIDATOR.isValid(rawPersonData);
    }

    private Person createPerson(String rawPersonData) {
        final String[] tokens = StringUtils.split(rawPersonData, FIELD_SEPARATOR);
        return new Person(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
    }
}
