package de.starwit.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Schema
@XmlRootElement
public class SaveAppTemplateDto extends AppTemplateDto {

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9#\\/\\._\\-]*\\.json)$")
    @Size(max = 100)
    private String configFile = "template-config.json";

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^(?:git|ssh|https?|git@[-w.]+):(//)?([a-zA-Z0-9./_-]{2,100})(.git)$")
    private String location;

    @Size(max = 100)
    @Pattern(regexp = "^([a-zA-Z0-9#/_\\-]{0,255})$")
    private String branch = "master";

    private String description;

    private List<String> groups;

    @Schema(hidden = true)
    @Pattern(regexp = "^([a-zA-Z_0-9|-])*$")
    @Size(max = 100)
    private String packagePlaceholder;

    @Size(max = 100)
    private String imageUrl;

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackagePlaceholder() {
        return packagePlaceholder;
    }

    public void setPackagePlaceholder(String packagePlaceholder) {
        this.packagePlaceholder = packagePlaceholder;
    }

    @Schema(hidden = true)
    @Override
    public String getName() {
        return super.getName();
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
