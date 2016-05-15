<#list (attributes) as attribute> 
	"${domain?lower_case}.${attribute.columnName}": "${attribute.columnName}",
</#list>
	"${domain?lower_case}.all.title": "${domain} anzeigen",
	"${domain?lower_case}.create.title": "${domain} erfassen",
	"${domain?lower_case}.update.title": "${domain} bearbeiten",