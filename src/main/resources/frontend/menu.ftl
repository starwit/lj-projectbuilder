<#list (domainnames) as name>
<li><a href="#/views/${name?lower_case}-all/" name="${name?lower_case}">{{'${name?lower_case}.all.title' | translate}}</a></li>
</#list>
