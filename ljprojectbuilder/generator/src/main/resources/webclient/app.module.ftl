<#list (domainnames) as name>
	'${appName}App.${name?lower_case}',
</#list>
