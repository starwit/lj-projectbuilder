<#list (domainnames) as name>
<script src="serviceconnector/${name?lower_case}-connector-factory.js" type="application/javascript"></script>
<script src="views/${name?lower_case}/${name?lower_case}-ctrl.js" type="application/javascript" ></script>
<script src="views/${name?lower_case}/${name?lower_case}-config.js" type="application/javascript" ></script>

</#list>