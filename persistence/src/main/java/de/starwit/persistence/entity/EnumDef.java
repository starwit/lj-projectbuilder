package de.starwit.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@XmlRootElement
@Entity
@Table(name = "ENUM_DEF")
public class EnumDef extends AbstractEntity<Long> {

    @NotBlank
    @Size(max = 200)
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9]*$")
    @Column(name = "ENUM_NAME", nullable = false)
    private String name;

    @NotBlank
    @Size(max = 500)
    @Column(name = "ENUM_VALUE", nullable = false)
    private String value;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "APP_ID")
    private App app;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getSelectList() {
        return value.split(",");
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

}
