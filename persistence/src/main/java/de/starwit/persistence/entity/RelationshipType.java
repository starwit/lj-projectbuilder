package de.starwit.persistence.entity;

import java.util.HashMap;
import java.util.Map;

public enum RelationshipType {
    OneToOne("one-to-one"),
    OneToMany("one-to-many"),
    ManyToOne("many-to-one"),
    ManyToMany("many-to-many");

    private String jsonFormat;

    private static final Map<String, RelationshipType>  lookup = new HashMap<>();

    static
    {
        for(RelationshipType relType : RelationshipType.values())
        {
            lookup.put(relType.jsonFormat, relType);
        }
    }

    private RelationshipType(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    /**
     * get Enum by string value (of a json-file)
     * @param jsonFormat
     * @return RelationshipType from json string value
     */
    static public RelationshipType getEnum(String jsonFormat) {
        return lookup.get(jsonFormat);
    }

    public String getJsonFormat() {
        return jsonFormat;
    }
}
