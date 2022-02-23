package cubs.training.storage;

import com.google.common.collect.Sets;
import cubs.training.data.Person;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PersonStorage {

    private final ConcurrentMap<String, Person> personsById = new ConcurrentHashMap<>();

    public void save(final Person person) {
        personsById.put(person.getId(), person);
    }

    public int size() {
        return personsById.values().size();
    }

    public Set<Person> drain() {
        final LinkedHashSet<Person> people = Sets.newLinkedHashSet(personsById.values());
        personsById.clear();
        return people;
    }
}
