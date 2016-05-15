<#list (domainnames) as name>
<li><a href="#/viewcomponents/${name?lower_case}-all/" name="${name?lower_case}">{{'${name?lower_case}.all.title' | translate}}</a></li>
</#list>
