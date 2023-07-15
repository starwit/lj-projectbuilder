package de.starwit.generator.generator;

import java.util.Set;
import java.util.TreeSet;

import de.starwit.persistence.entity.Attribute;
import de.starwit.persistence.entity.DataType;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.Relationship;
import de.starwit.persistence.entity.RelationshipType;

/**
 *
 * @author anett
 *
 */
public class EntityImports {
    /**
     * Imports for Java-Entities Bean Validation and DataTypes for EntityGeneration.
     *
     * @param domain - definition of domain.
     * @return Set of Java imports
     */
    public static Set<String> gatherEntityImports(Domain domain) {
        Set<String> imports = new TreeSet<>();

        addGeneralImports(imports);

        if (domain.getAttributes() != null) {
            for (Attribute attr : domain.getAttributes()) {
                addImportsForAttribute(imports, attr);
            }
        }

        if (domain.getRelationships() != null) {
            for (Relationship relation : domain.getRelationships()) {
                addImportsForRelations(imports, relation);
            }
        }
        return imports;
    }

    private static void addGeneralImports(Set<String> imports) {
        imports.add("import jakarta.persistence.Column;");
        imports.add("import jakarta.persistence.Entity;");
        imports.add("import jakarta.persistence.Table;");
        imports.add("import jakarta.xml.bind.annotation.XmlRootElement;");
    }

    private static void addImportsForAttribute(Set<String> imports, Attribute attr) {
        if (DataType.String.equals(attr.getDataType())) {
            if (attr.getMax() != null || attr.getMin() != null) {
                imports.add("import jakarta.validation.constraints.Size;");
            }
            if (!attr.isNullable()) {
                imports.add("import jakarta.validation.constraints.NotBlank;");
            }
        } else if (DataType.Date.equals(attr.getDataType()) || DataType.Time.equals(attr.getDataType())
                || DataType.Timestamp.equals(attr.getDataType())) {
            imports.add("import java.util.Date;");
            imports.add("import jakarta.persistence.Temporal;");
            imports.add("import jakarta.persistence.TemporalType;");
        } else if (DataType.Enum.equals(attr.getDataType())) {
            imports.add("import jakarta.persistence.EnumType;");
            imports.add("import jakarta.persistence.Enumerated;");
        } else if (DataType.BigDecimal.equals(attr.getDataType())) {
            imports.add("import java.math.BigDecimal;");
        }
        if (attr.getPattern() != null) {
            imports.add("import jakarta.validation.constraints.Pattern;");
        }

        if (!DataType.String.equals(attr.getDataType())) {
            if (!attr.isNullable()) {
                imports.add("import jakarta.validation.constraints.NotNull;");
            }
            if (attr.getMax() != null) {
                imports.add("import jakarta.validation.constraints.Max;");
            }
            if (attr.getMin() != null) {
                imports.add("import jakarta.validation.constraints.Min;");
            }
        }
    }

    private static void addImportsForRelations(Set<String> imports, Relationship relation) {
        imports.add("import jakarta.persistence." + relation.getRelationshipType() + ";");
        imports.add("import com.fasterxml.jackson.annotation.JsonFilter;");
        // imports.add("import jakarta.persistence.FetchType;");

        if (RelationshipType.ManyToMany.equals(relation.getRelationshipType()) ||
                RelationshipType.ManyToOne.equals(relation.getRelationshipType()) ||
                RelationshipType.OneToOne.equals(relation.getRelationshipType())) {
            imports.add("import jakarta.persistence.JoinColumn;");
        }
        if (RelationshipType.ManyToMany.equals(relation.getRelationshipType()) &&
                relation.isOwnerSide()) {
            imports.add("import jakarta.persistence.JoinTable;");
        }
        if (RelationshipType.OneToMany.equals(relation.getRelationshipType())
                || RelationshipType.ManyToMany.equals(relation.getRelationshipType())) {
            imports.add("import java.util.Set;");
        }
        if (RelationshipType.OneToOne.equals(relation.getRelationshipType()) &&
                relation.isOwnerSide()) {
            imports.add("import jakarta.persistence.CascadeType;");
        }
    }
}
