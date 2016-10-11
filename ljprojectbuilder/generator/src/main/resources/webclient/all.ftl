<div class="subPart personAll">
	<div class="subPartTitle"><h1>{{title | translate}}</h1></div>
	<div class="editMenu">
		<button class="buttonLarge grey floating" ng-click="goto${domain}.create();">erstellen</button>
		<button class="buttonLarge grey floating" ng-click="idSelected != null && goto${domain}.update(idSelected);" ng-disabled="idSelected == null">bearbeiten</button>
		<button class="buttonLarge grey floating" ng-click="idSelected != null && delete${domain}(idSelected);" ng-disabled="idSelected == null">l&ouml;schen</button>
	</div>
	<div class="resultTable">
		<table>
		<thead>
			<#list attributes> 
			<tr>
				<#items as attribute>
				<th>{{'${domain?lower_case}.${attribute.name}' | translate}}</th>
				</#items>
			</tr>	
			</#list>
		</thead>
		<tbody>
			<tr ng-repeat="${domain?lower_case} in ${domain?lower_case}All" ng-click="setSelected(${domain?lower_case}.id)" 
				ng-class="{selected: ${domain?lower_case}.id === idSelected }" 
				ng-dblclick="setSelected(${domain?lower_case}.id);goto${domain}.update(idSelected);">

				<#list attributes> 
				<#items as attribute>
				<td>{{${domain?lower_case}.${attribute.name}}}</td>
				</#items>
				</#list>
			</tr>
		</tbody>
		<tfoot></tfoot>
		</table>
		</div>	
	</div>
</div>