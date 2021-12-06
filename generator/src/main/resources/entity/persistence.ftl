<#list (project.selectedDomains) as domain> 
		<class>de.${project.packagePrefix?lower_case}.${project.title?lower_case}.entity.${domain.name}Entity</class>
</#list>