<#list domains as domain>
	'${appName}App.${domain.name?lower_case}',
</#list>