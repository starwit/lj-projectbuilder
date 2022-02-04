package de.starwit.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@XmlRootElement
public class SaveAppTemplateDto extends AppTemplateDto {

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^(?:git|ssh|https?|git@[-w.]+):(//)?([a-zA-Z0-9./_-]{2,100})(.git)$")
    private String location;

    @Size(max = 100)
    @Pattern(regexp = "^([a-zA-Z0-9/_-]{0,100})$")
    private String branch = "master";

    private String description;

    private List<String> groups;

    private List<String> userGroups;

    @Schema(hidden = true)
    @Pattern(regexp = "^([a-zA-Z_0-9|-])*$")
	@Size(max = 100)
    private String packagePlaceholder;

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

    public List<String> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<String> userGroups) {
        this.userGroups = userGroups;
    }
}
