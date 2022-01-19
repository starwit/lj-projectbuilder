package de.starwit.persistence.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.AttributeConverter;

public class ListToStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute == null ? null : "," + String.join(",", attribute) + ",";
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        ArrayList<String> items = new ArrayList<>();
        if (dbData != null) {
            Collections.addAll(items, dbData.split(","));
        }
        items.removeIf(item -> item == null || "".equals(item));
        items.sort(String.CASE_INSENSITIVE_ORDER);
        return items;
    }
}
