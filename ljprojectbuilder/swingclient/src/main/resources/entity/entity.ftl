package de.starwit.${appName?lower_case}.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
<#list (imports) as import> 
${import}
</#list>

@XmlRootElement
@Entity
@Table(name="${domainUpper}")
public class ${domain}Entity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	//domain attributes
	
	<#list (attributes) as attribute> 
		<#if attribute.pattern??>
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
	private Date ${attribute.columnName};
	<#else>
	private ${attribute.dataType} ${attribute.columnName};
	</#if>
	
	</#list>
	
	<#list (attributes) as attribute> 

	<#if attribute.dataType == "String"> 
	@Column(name="${attribute.columnName?upper_case}"<#if !attribute.nullable>, nullable = false</#if><#if attribute.max??>, length=${attribute.max}</#if>)
	public ${attribute.dataType} get${attribute.columnName?cap_first}() {
		return ${attribute.columnName};
	}
	</#if>
	<#if attribute.dataType == "Integer"> 
	@Column(name="${attribute.columnName?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.columnName?cap_first}() {
		return ${attribute.columnName};
	}
	</#if>
	<#if attribute.dataType == "BigDecimal"> 
	@Column(name="${attribute.columnName?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.columnName?cap_first}() {
		return ${attribute.columnName};
	}
	</#if>
	<#if attribute.dataType == "Double"> 
	@Column(name="${attribute.columnName?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.columnName?cap_first}() {
		return ${attribute.columnName};
	}
	</#if>
	<#if attribute.dataType == "Enum"> 
	@Enumerated(EnumType.STRING)
	@Column(name="${attribute.columnName?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public ${attribute.dataType} get${attribute.columnName?cap_first}() {
		return ${attribute.columnName};
	}
	</#if>
	<#if attribute.dataType == "Date" || attribute.dataType == "Time" || attribute.dataType == "Timestamp"> 
	@Temporal(TemporalType.${attribute.dataType?upper_case})
	@Column(name="${attribute.columnName?upper_case}"<#if !attribute.nullable>, nullable = false</#if>)
	public Date get${attribute.columnName?cap_first}() {
		return ${attribute.columnName};
	}
	</#if>
	
	<#if attribute.dataType == "Date" || attribute.dataType == "Time" || attribute.dataType == "Timestamp">
	public void set${attribute.columnName?cap_first}(Date ${attribute.columnName}) {
		this.${attribute.columnName} = ${attribute.columnName};
	}
	<#else>
		public void set${attribute.columnName?cap_first}(${attribute.dataType} ${attribute.columnName}) {
		this.${attribute.columnName} = ${attribute.columnName};
	}
	</#if>
	</#list>
}