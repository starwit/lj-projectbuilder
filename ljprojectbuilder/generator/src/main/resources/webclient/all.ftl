<div class="subPart personAll">
	<div class="subPartTitle"><h1>{{title | translate}}</h1></div>
	<div class="editMenu">
		<button class="buttonLarge grey floating" ng-click="ctrl.goto${domain}.create();">erstellen</button>
		<button class="buttonLarge grey floating" ng-click="ctrl.idSelected != null && ctrl.goto${domain}.update(ctrl.idSelected);" ng-disabled="ctrl.idSelected == null">bearbeiten</button>
		<button class="buttonLarge grey floating" ng-click="ctrl.idSelected != null && ctrl.delete${domain}(ctrl.idSelected);" ng-disabled="ctrl.idSelected == null">l&ouml;schen</button>
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
			<tr ng-repeat="${domain?lower_case} in ctrl.${domain?lower_case}All" ng-click="ctrl.setSelected(${domain?lower_case}.id)" 
				ng-class="{selected: ${domain?lower_case}.id === ctrl.idSelected }" 
				ng-dblclick="ctrl.setSelected(${domain?lower_case}.id);ctrl.goto${domain}.update(ctrl.idSelected);">

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