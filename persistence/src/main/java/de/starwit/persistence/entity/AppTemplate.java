package de.starwit.persistence.entity;

import de.starwit.persistence.converter.ListToStringConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement
@Entity
@Table(name = "APPTEMPLATE")
public class AppTemplate extends AbstractEntity<Long> {

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
    @Size(max = 100)
    @Column(name = "TEMPLATE_NAME", nullable = false, length = 100)
    private String templateName = "lirejarp";

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9#\\/\\._\\-]*\\.json)$")
    @Size(max = 100)
    @Column(name = "CONFIG_FILE", nullable = false, length = 100)
    private String configFile = "template-config.json";

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
    @Size(max = 100)
    @Column(name = "PACKAGE", nullable = false, length = 100)
    private String packagePlaceholder = "starwit";

    @Column(name = "IMAGE_URL")
    @Pattern(regexp = "^(https?:\\/\\/.*\\.(?:png|jpg|jpeg))$")
    private String imageUrl;

    @NotBlank
    @Pattern(regexp = "^(?:git|ssh|https?|git@[-w.]+):(//)?(.*?)(.git)(/?|#[-dw._]+?)$")
    @Size(max = 100)
    @Column(name = "LOCATION", nullable = false, length = 100)
    private String location;

    @Size(max = 255)
    @Pattern(regexp = "^([a-zA-Z0-9#/_\\-]*)$")
    @Column(name = "BRANCH", length = 255)
    private String branch = "master";

    @Column(name = "CREDENTIALS_REQUIRED")
    private boolean credentialsRequired = false;

    @OrderBy("category, fileName asc")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "appTemplate")
    private Set<TemplateFile> templateFiles;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "GROUPS")
    @Convert(converter = ListToStringConverter.class)
    private List<String> groups;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getPackagePlaceholder() {
        return packagePlaceholder;
    }

    public void setPackagePlaceholder(String packagePlaceholder) {
        this.packagePlaceholder = packagePlaceholder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public boolean isCredentialsRequired() {
        return credentialsRequired;
    }

    public void setCredentialsRequired(boolean credentialsRequired) {
        this.credentialsRequired = credentialsRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TemplateFile> getTemplateFiles() {
        return templateFiles;
    }

    public void setTemplateFiles(Set<TemplateFile> templateFiles) {
        this.templateFiles = templateFiles;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
