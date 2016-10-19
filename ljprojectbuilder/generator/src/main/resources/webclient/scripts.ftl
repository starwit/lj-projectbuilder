<#list (domainnames) as name>
<script src="shared/restfacade/${name?lower_case}.connector.factory.js" type="application/javascript"></script>
<script src="viewcomponents/${name?lower_case}/${name?lower_case}.module.js" type="application/javascript" ></script>
<script src="viewcomponents/${name?lower_case}/${name?lower_case}.routes.js" type="application/javascript" ></script>
<script src="viewcomponents/${name?lower_case}/${name?lower_case}SingleCtrl.js" type="application/javascript" ></script>
<script src="viewcomponents/${name?lower_case}/${name?lower_case}AllCtrl.js" type="application/javascript" ></script>
</#list>