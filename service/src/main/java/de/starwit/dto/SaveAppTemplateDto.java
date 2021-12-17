package de.starwit.dto;

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
    @Pattern(regexp = "^(?:git|ssh|https?|git@[-w.]+):(//)?(.*?)(.git)(/?|#[-dw._]+?)$")
    private String location;

    @Size(max = 100)
    @Pattern(regexp = "^([a-zA-Z0-9/_\\-]*)$")
    private String branch = "master";

    private boolean credentialsRequired = false;

    private String description;

    @Schema(hidden = true)
    @Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
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
}
