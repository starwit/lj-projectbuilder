<#list (domains) as domain> 
		<class>de.${package}.${appName?lower_case}.entity.${domain.name}Entity</class>
</#list>