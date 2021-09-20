<#list app.selectedDomains as domain>
	'${app.title?lower_case}App.${domain.name?lower_case}',
</#list>