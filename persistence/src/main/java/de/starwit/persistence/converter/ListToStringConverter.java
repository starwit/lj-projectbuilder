package de.starwit.persistence.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.AttributeConverter;

public class ListToStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute == null ? null : String.join(",", attribute) + ",";
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        List<String> items = dbData == null ? Collections.emptyList() : Arrays.asList(dbData.split(","));
        items.sort(String.CASE_INSENSITIVE_ORDER);
        return items;
    }
}
