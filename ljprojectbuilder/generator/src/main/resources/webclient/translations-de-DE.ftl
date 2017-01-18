<#list domains as domain>
<#list (domain.attributes) as attribute> 
	"${domain.name?lower_case}.${attribute.name}": "${attribute.name}",
</#list>
	"${domain.name?lower_case}.all.title": "${domain.name} anzeigen",
	"${domain.name?lower_case}.create.title": "${domain.name} erfassen",
	"${domain.name?lower_case}.update.title": "${domain.name} bearbeiten",
</#list>
