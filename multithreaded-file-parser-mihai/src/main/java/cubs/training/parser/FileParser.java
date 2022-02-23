package cubs.training.parser;

import cubs.training.FileParserApp;
import cubs.training.processor.RawPersonDataProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileParser {

    private static final Logger LOGGER = LogManager.getLogger(FileParserApp.class);
    private static final String PERSON_SEPARATOR = "#";
    private RawPersonDataProcessor rawPersonDataProcessor;

    public FileParser(final RawPersonDataProcessor rawPersonDataProcessor) {
        this.rawPersonDataProcessor = rawPersonDataProcessor;
    }

    public void readPersonsFromFile(String fileName) throws IOException, URISyntaxException {
        final URL inputFileURL = getClass().getClassLoader().getResource(fileName);
        if (inputFileURL == null) {
            LOGGER.error("Failed to parse " + fileName + " File not found");
            return;
        }
        final Path path = Paths.get(inputFileURL.toURI());
        try (Scanner scanner = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            scanner.useDelimiter(PERSON_SEPARATOR);
            while (scanner.hasNext()) {
                rawPersonDataProcessor.processIfValid(scanner.next());
            }
        }
    }
}
