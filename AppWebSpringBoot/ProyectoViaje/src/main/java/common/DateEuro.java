package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DateEuro implements AttributeConverter<LocalDate, String> {

    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.format(FORMATTER);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dbData, FORMATTER);
    }
}