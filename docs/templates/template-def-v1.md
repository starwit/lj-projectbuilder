---
layout: page
title: Version 1
permalink: /template-version-1/
parent: Templates
---

# Template Definition Version 1 <sup>depricated</sup>

The following objects can be used in template files.

### **project**

General information about the project are stored.


| Name        | Description     |      Example  |
| ------------- |-------------| -------------|
| projecthome   | home directory | `${projecthome}` |
| project.targetPath | directory where the template is checked out  | `${projecthome}/${project.targetPath}/generator-templates/entity/entity.ftl` |
| project.title | name of the project | `${projecthome}/${project.targetPath}/${project.title}/persistence/` |
| project.template | project template used - not used in templates |  |
| project.packagePrefix | you can choose a package name which will be included in the Java-package path | `${projecthome}/${project.targetPath}/${project.title}/persistence/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/entity/`
| project.description | description of the project which is not used in templates warning: not possible anymore | |
| project.domains | domain objects.   | |

### **domain**

The domain object is normally used in the template-files (*.ftl).

#### Example
```
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
```

#### Properties

| Name        | Description     |      Example  |
| ------------|-------------| -------------|
| domain.name | name of the domain | public class ${domain.name}Entity extends AbstractEntity { |
| domain.description | description of the domain | normally not used |
| domain.getAttributes() | used to get all attributes of a domain | <#list (domain.getAttributes()) as attribute> |

### **attribute**

As shown in the example above, attribute is used in comination with a domain.

#### Example

```
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
```

#### Properties

| Name        | Description     |      Example  |
| ------------|-------------| -------------|
| attribute.name | name of the attribute | @Column(name="${attribute.name?upper_case}" |
| attribute.description | description of the attribute | normally not used |
| attribute.pattern | Regex for validation of input | @Pattern(regexp = "${attribute.pattern}")|
| attribute.dataType | | <#if attribute.dataType == "BigDecimal"> |
| attribute.min | String: minimum length of the String. Number: smallest number | |
| attribute.max | String: maximum length of the String. Number: highest number | |
| attribute.nullable | Is false if attribut value is required | <#if !attribute.nullable>, nullable = false</#if> |