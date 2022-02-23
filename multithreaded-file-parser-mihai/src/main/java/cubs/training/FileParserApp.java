package cubs.training;

import cubs.training.data.Person;
import cubs.training.parser.FileParser;
import cubs.training.processor.PersonProcessor;
import cubs.training.processor.RawPersonDataProcessor;
import cubs.training.storage.PersonStorage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.util.Arrays.asList;

public class FileParserApp {


    private static final Logger LOGGER = LogManager.getLogger(FileParserApp.class);
    private static final Set<String> INPUT_FILE_NAMES = newLinkedHashSet(asList("input0.txt", "input1.txt", "input2.txt"));
    private static final ReentrantLock lock = new ReentrantLock();
    private static int noOfLocks = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);
        final PersonStorage personStorage = new PersonStorage();
        final PersonProcessor personProcessor = new PersonProcessor(personStorage);
        final RawPersonDataProcessor rawPersonDataProcessor = new RawPersonDataProcessor(personProcessor);
        final FileParser fileParser = new FileParser(rawPersonDataProcessor);

        final List<Thread> parserThreads = new ArrayList<>();
        for (String fileName : INPUT_FILE_NAMES) {
            parserThreads.add(new Thread(new FileParserTask(fileName, fileParser), fileName));
        }

        startParserThreads(parserThreads);
        waitForCompletion(parserThreads);
        LOGGER.info("Parsing finished");

        serializePeople(personStorage);
        LOGGER.info("Serialized people");
    }

    private static void serializePeople(PersonStorage personStorage) throws IOException {
        final Set<Person> people = personStorage.drain();
        final FileOutputStream fout = new FileOutputStream("output.bin");
        final ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fout));
        oos.writeObject(people);
    }

    private static void startParserThreads(List<Thread> parserThreads) {
        LOGGER.info("Starting parser threads");
        for (final Thread parserThread : parserThreads) {
            parserThread.setPriority(Thread.MAX_PRIORITY);
        }
        parserThreads.get(parserThreads.size() - 1).setPriority(Thread.MIN_PRIORITY);
        for (final Thread parserThread : parserThreads) {
            parserThread.start();
        }
    }

    private static void waitForCompletion(List<Thread> parserThreads) throws InterruptedException {
        for (final Thread parserThread : parserThreads) {
            parserThread.join();
        }
    }

    static class FileParserTask implements Runnable {

        private final String fileName;
        private final FileParser fileParser;

        FileParserTask(String fileName, FileParser fileParser) {
            this.fileName = fileName;
            this.fileParser = fileParser;
        }

        @Override
        public void run() {
            boolean processed = false;

            while(!processed) {
                while(true) {
                    try {
                        if (!lock.tryLock(1, TimeUnit.MILLISECONDS)) break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++noOfLocks;

                    //System.out.println("Acquired lock");

                    if(noOfLocks >= 2) {
                        //System.out.println("Processing");
                        processed = true;
                        try {
                            LOGGER.info("Thread " + Thread.currentThread() + " started parsing");
                            fileParser.readPersonsFromFile(fileName);
                            LOGGER.info("Thread " + Thread.currentThread() + " finished parsing");
                        } catch (Exception e) {
                            LOGGER.error("Failed to execute file parser task for " + fileName, e);
                        }
                    }

                    //System.out.println("Release lock");
                    --noOfLocks;
                    lock.unlock();
                }
            }
        }
    }
}
