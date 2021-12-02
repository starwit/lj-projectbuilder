<#list app.selectedDomains as domain>
<#list (domain.attributes) as attribute> 
	"${domain.name?lower_case}.${attribute.name}": "${attribute.name}",
</#list>
	"${domain.name?lower_case}.all.title": "show ${domain.name}",
	"${domain.name?lower_case}.create.title": "create ${domain.name}",
	"${domain.name?lower_case}.update.title": "edit ${domain.name}",
</#list>
