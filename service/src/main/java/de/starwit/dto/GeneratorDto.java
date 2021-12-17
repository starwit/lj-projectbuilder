package de.starwit.dto;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Contains all configuration for app setup and generator.
 * @author Anett Huebner
 *
 */
@Schema
@XmlRootElement
public class GeneratorDto extends LoadAppTemplateDto {
	
	private Long appId;

	/********************* GETTER, SETTER **************************/
	
	@Schema(defaultValue = "1")
	@NotBlank
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	@Override
	@JsonIgnore
	public Long getAppTemplateId() {
		return super.getAppTemplateId();
	}
}
