<#list (domains) as domain>
<script src="shared/restfacade/${domain.name?lower_case}.connector.factory.js" type="application/javascript"></script>
<script src="viewcomponents/${domain.name?lower_case}/${domain.name?lower_case}.module.js" type="application/javascript" ></script>
<script src="viewcomponents/${domain.name?lower_case}/${domain.name?lower_case}.routes.js" type="application/javascript" ></script>
<script src="viewcomponents/${domain.name?lower_case}/${domain.name?lower_case}SingleCtrl.js" type="application/javascript" ></script>
<script src="viewcomponents/${domain.name?lower_case}/${domain.name?lower_case}AllCtrl.js" type="application/javascript" ></script>
</#list>