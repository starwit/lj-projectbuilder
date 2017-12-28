package de.${package}.${appName?lower_case}.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
<#list (imports) as import> 
${import}
</#list>

@XmlRootElement
@Entity
@Table(name="${domain.name?upper_case}")
public class ${domain.name}Entity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	//domain attributes
	<#list (domain.getAttributes()) as attribute> 
		<#if attribute.pattern?? && attribute.pattern?length &gt; 0 && attribute.dataType == "String">
	@Pattern(regexp = "${attribute.pattern}")
		</#if>	
		<#if attribute.dataType == "String">
			<#if !attribute.nullable>
	@NotBlank
			</#if>
			<#if attribute.min?? && attribute.max??>
	@Size(min = ${attribute.min}, max = ${attribute.max})
			</#if>
			<#if attribute.min?? && !attribute.max??>
	@Size(min = ${attribute.min})
			</#if>
			<#if !attribute.min?? && attribute.max??>
	@Size(max = ${attribute.max})
			</#if>
		<#else>
			<#if !attribute.nullable>
	@NotNull
			</#if>
			<#if attribute.min??>
	@Min(value = ${attribute.min})
			</#if>
			<#if attribute.max??>
	@Max(value = ${attribute.max})
			</#if>
		</#if>
	<#if attribute.dataType == "Date" || attribute.dataType == "Time" || attribute.dataType == "Timestamp">
	private Date ${attribute.name};
	<#else>
	private ${attribute.dataType} ${attribute.name};
	</#if>
	
	</#list>
	
	<#list (domain.getAttributes()) as attribute> 

	<#if attribute.dataType == "String"> 
	@Column(name="${attribute.name?upper_case}"<#if !attribute.nullable>, nullable = false</#if><#if attribute.max??>, length=${attribute.max}</#if>)
	public ${attribute.dataType} get${attribute.name?cap_first}() {
		return ${attribute.name};
	}
	</#if>
	<#if attribute.dataType == "Integer"> 
	@Column(name="${attribute.name?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.name?cap_first}() {
		return ${attribute.name};
	}
	</#if>
	<#if attribute.dataType == "BigDecimal"> 
	@Column(name="${attribute.name?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.name?cap_first}() {
		return ${attribute.name};
	}
	</#if>
	<#if attribute.dataType == "Double"> 
	@Column(name="${attribute.name?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.name?cap_first}() {
		return ${attribute.name};
	}
	</#if>
	<#if attribute.dataType == "Enum"> 
	@Enumerated(EnumType.STRING)
	@Column(name="${attribute.name?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.name?cap_first}() {
		return ${attribute.name};
	}
	</#if>
	<#if attribute.dataType == "Date" || attribute.dataType == "Time" || attribute.dataType == "Timestamp"> 
	@Temporal(TemporalType.${attribute.dataType?upper_case})
	@Column(name="${attribute.name?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public Date get${attribute.name?cap_first}() {
		return ${attribute.name};
	}
	</#if>
	
	<#if attribute.dataType == "Date" || attribute.dataType == "Time" || attribute.dataType == "Timestamp">
	public void set${attribute.name?cap_first}(Date ${attribute.name}) {
		this.${attribute.name} = ${attribute.name};
	}
	<#else>
	public void set${attribute.name?cap_first}(${attribute.dataType} ${attribute.name}) {
		this.${attribute.name} = ${attribute.name};
	}
	</#if>
	</#list>
}