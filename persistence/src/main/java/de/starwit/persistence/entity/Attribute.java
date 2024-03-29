package de.starwit.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@XmlRootElement
@Entity
@Table(name = "ATTRIBUTE")
public class Attribute extends AbstractEntity<Long> {

    // domain attributes

    @NotBlank
    @Pattern(regexp = "^[a-z][a-zA-Z0-9]*$")
    @Length(max = 100)
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Length(max = 240)
    @Column(name = "DESCRIPTION", length = 240)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private DataType dataType;

    @Column(name = "PATTERN")
    private String pattern;

    @Column(name = "MAXIMUM")
    private Integer max;

    @Column(name = "MINIMUM")
    private Integer min;

    @Column(name = "NULLABLE")
    private boolean nullable = true;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ENUM_DEF_ID")
    private EnumDef enumDef;

    // TODO
    @Transient
    private boolean unique = false;

    // TODO
    @Transient
    private String patterJava;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType type) {
        this.dataType = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPatterJava() {
        return patterJava;
    }

    public void setPatterJava(String patterJava) {
        this.patterJava = patterJava;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isRequired() {
        return !nullable;
    }

    public void setRequired(boolean required) {
        this.nullable = !required;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public EnumDef getEnumDef() {
        return enumDef;
    }

    public void setEnumDef(EnumDef enumDef) {
        this.enumDef = enumDef;
    }

}