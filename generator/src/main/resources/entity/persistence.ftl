<#list (app.selectedDomains) as domain> 
		<class>de.${app.packagePrefix?lower_case}.${app.title?lower_case}.entity.${domain.name}Entity</class>
</#list>