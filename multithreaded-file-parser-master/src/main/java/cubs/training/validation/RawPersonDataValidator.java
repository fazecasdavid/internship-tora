package cubs.training.validation;

import org.apache.commons.lang3.StringUtils;

public class RawPersonDataValidator implements Validator<String> {

    private String fieldSeparator;

    public RawPersonDataValidator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    @Override
    public boolean isValid(String rawPersonData) {
        return StringUtils.isNotBlank(rawPersonData) && StringUtils.countMatches(rawPersonData, fieldSeparator) == 4;
    }
}
