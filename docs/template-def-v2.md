# Template Definition Version 2

The following objects can be used in template files. See also https://pb.starwit.de/ljprojectbuilder/swagger-ui.html

### **app**

In Swagger: AppDto

General information about the app are stored. Can also be used in templatePath or targetPath or template files.

#### **Example**

Java
```java
package de.${app.packageName?lower_case}.persistence.entity;

<#list (imports) as import>
${import}
</#list>
...
```
#### **Properties**

| Name        | Type | Description     |
| ------------- |-------------| -------------|
| targetPath | String | directory where the template is checked out  |
| baseName | String | name of the app |
| template | AppTemplate | app template used - not used in templates |  |
| packageName | String | you can choose a package name which will be included in the Java-package path |
| entities | Array of EntityDto | entities   |
| enums | Array of EnumDTo | enums   |

### **entity**

In Swagger: EntityDto

The domain object is normally used in the template-files (*.ftl).

Waring: There is a predefined list of imports named `imports` you can use for entity creation in Java.

#### **Example**

Java
```java
package de.${app.packageName?lower_case}.persistence.entity;

<#list (imports) as import>
${import}
</#list>

/**
 * ${entity.name} Entity class
 */
@XmlRootElement
@Entity
@Table(name = "${entity.name?upper_case}")
public class ${entity.name}Entity extends AbstractEntity<Long> {

<#if entity.fields??>
    // entity fields
  <#list (entity.fields) as field>
  <#if field.fieldValidateRulesPattern?? && field.fieldValidateRulesPattern?length &gt; 0 && field.fieldType == "String">
    @Pattern(regexp = "${field.fieldValidateRulesPattern}")
  </#if>
  <#if field.fieldType == "String">
            <#if field.required>
```

#### **Properties**

| Name        | Type | Description     |      Example  |
| ------------|-------------| -------------|---|
| name | String | name of the entity | `public class ${entity.name}Entity extends AbstractEntity {` |
| fields | FieldDto | fields of the entity | `<#list (entity.fields) as field>` |
| relationships | Relationship | relation to another entity | `<#list (entity.relationships) as relation>` |
| position | Position | position on the entity-relationship diagram. Should not be needed for templates |  |

### **field**

In Swagger: FieldDto

As shown in the example above, field is used in comination with a domain.

#### **Example**

Java
```Java
<#if entity.fields??>
    // entity fields
  <#list (entity.fields) as field>
  <#if field.fieldValidateRulesPattern?? && field.fieldValidateRulesPattern?length &gt; 0 && field.fieldType == "String">
    @Pattern(regexp = "${field.fieldValidateRulesPattern}")
  </#if>
  <#if field.fieldType == "String">
            <#if field.required>
    @NotBlank
            </#if>
            <#if field.fieldValidateRulesMinlength?? && field.fieldValidateRulesMaxlength??>
    @Size(min = ${field.fieldValidateRulesMinlength}, max = ${field.fieldValidateRulesMaxlength})
```

#### **Properties**

| Name        | Type | Description     |
| ------------|-------------| -------------|
| fieldName | String | name of the field |
| fieldType | enum [ String, Integer, Long, BigDecimal, Float, Double, Boolean, Enum ] | data type of field |
| fieldValidateRules | array of enum [ required, minlength, maxlength, pattern, unique, min, max ] | which validation rules are added |
| fieldValidateRulesMin | Integer | lowest allowed number |
| fieldValidateRulesMax | Integer | highest allowed number |
| fieldValidateRulesMinlength | Integer | lowest allowed number |
| fieldValidateRulesMaxlength | Integer | maximum length of the String |
| fieldValidateRulesMaxlength | Integer | maximum length of the String |
| fieldValidateRulesPattern | String | regex validation pattern |
| fieldValidateRulesPatternJava | String | regex validation pattern. Same value as fieldValidateRulesPattern. |
| required | boolean | value is required |

### **relationship**

In Swagger: Relationship

Relation between two entities. They are saved bidirectional. If you create one relationship for an entity in the entity-relationship diagram in project builder, two relationship objects will be created and added to the two according entities.

#### **Example**

Java
```Java
<#if entity.relationships??>
    // entity relations
  <#list (entity.relationships) as relation>
  <#if relation.relationshipType == "OneToMany">
    @OneToMany(mappedBy = "${relation.otherEntityRelationshipName}")
    private Set<${relation.otherEntityName}Entity> ${relation.relationshipName};

  <#elseif relation.relationshipType == "ManyToOne">
    @ManyToOne
    @JoinColumn(name = "${relation.otherEntityName?upper_case}_ID")
    private ${relation.otherEntityName}Entity ${relation.relationshipName};

  <#elseif relation.relationshipType == "OneToOne">
    ...
  </#if>
  </#list>
</#if>
```

#### **Properties**

| Name        | Type | Description     |
| ------------|-------------| -------------|
| otherEntityName | String | name of the entity on the other side |
otherEntityRelationshipName | String | name of the field in the entity on the other side |
| relationshipName | String | name of the relationship field in the parent entity |
| ownerSide | boolean | is owner of the relationship |
| relationshipType | one of [ one-to-one, many-to-one, many-to-many, one-to-one ] | type of the relation, default: one-to-many |

### **enumDef**

In Swagger: EnumDto

Enums are added to the generator as "enumDefs" and contains directly to app configuration. Additionally, fields can have the type `enum` and contain an `enumDef`.

#### **Example**

Java
```Java
        <#if field.fieldType == "Enum">
    @Enumerated(EnumType.STRING)
    @Column(name = "${field.fieldName?upper_case}"<#if field.required>, nullable = false</#if>)
        </#if>
        <#if field.fieldType == "Enum">
    private ${field.enumDef.name} ${field.fieldName};
```
JavaScript
```JavaScript
const entityFields = [
<#if entity.fields??>
<#list (entity.fields) as field>
    <#if field.fieldType == "Enum">
    {
        name: "${field.fieldName}",
        type: "${field.fieldType?lower_case}",
        regex: <#if field.fieldValidateRulesPattern??>/^${field.fieldValidateRulesPattern}$/<#else>null</#if>,
        notNull: <#if field.required>true<#else>false</#if>,
        enumName: "${field.enumDef.name?trim?uncap_first}",
        selectList: [<#list (field.enumDef.selectList) as enumItem>
            "${enumItem?trim}"<#sep>,</#sep></#list>
        ]
    },
...
```
#### **Properties**

| Name        | Type | Description     |
| ------------|-------------| -------------|
| name | String |
| value | String | comma separated list of enum values |
| selectList | array of Strings | selection possibilities for UI |
| position | Position | position on the entity-relationship diagram. Should not be needed for templates |