<#list project.selectedDomains as domain>
	'${project.title?lower_case}App.${domain.name?lower_case}',
</#list>