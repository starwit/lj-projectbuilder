<#list project.selectedDomains as domain>
<li><a href="#/viewcomponents/${domain.name?lower_case}-all/" name="${domain.name?lower_case}">{{'${domain.name?lower_case}.all.title' | translate}}</a></li>
</#list>
