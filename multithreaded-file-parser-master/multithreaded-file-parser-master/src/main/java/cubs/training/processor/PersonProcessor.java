package cubs.training.processor;

import cubs.training.data.Person;
import cubs.training.storage.PersonStorage;
import cubs.training.validation.PersonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonProcessor implements ValidDataProcessor<Person> {

    private static final Logger LOGGER = LogManager.getLogger(PersonProcessor.class);
    private static final PersonValidator VALIDATOR = new PersonValidator();
    private final PersonStorage personStorage;

    public PersonProcessor(PersonStorage personStorage) {
        this.personStorage = personStorage;
    }

    @Override
    public boolean isValid(Person person) {
        return VALIDATOR.isValid(person);
    }

    @Override
    public void process(Person person) {
        personStorage.save(person);
        LOGGER.info("Saved person " + person.getId() + " Total number of persons " + personStorage.size());
    }
}