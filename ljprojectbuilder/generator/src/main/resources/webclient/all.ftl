<div id="headerBox" ng-include="'menu.html'"></div>

<div id="subheaderBox" class="aCenter">
	<div class="bottom">
		<span class="button add"><a ng-click="ctrl.goto${domain.name}.create();">* {{'button.add' | translate}}</a></span>
		<span class="button"><a ng-click="ctrl.idSelected != null && ctrl.goto${domain.name}.update(ctrl.idSelected);" ng-disabled="ctrl.idSelected == null">+ {{'button.update' | translate}}</a></span>
		<span class="button"><a ng-click="ctrl.idSelected != null && ctrl.delete${domain.name}(ctrl.idSelected);" ng-disabled="ctrl.idSelected == null">- {{'button.delete' | translate}}</a></span>
		<div class="clearer"></div>
	</div>
</div>

<div id="bodyBox">
	<div class="contain">
		<table>
			<colgroup>
				<#list domain.attributes> 
				<#items as attribute>
	    		<col class="">
	    		</#items>
	    		</#list>
	    	</colgroup>
		<thead>
			<#list domain.attributes> 
			<tr>
				<#items as attribute>
				<th>{{'${domain.name?lower_case}.${attribute.name}' | translate}}</th>
				</#items>
			</tr>	
			</#list>
		</thead>
		<tbody>
			<tr ng-repeat="${domain.name?lower_case} in ctrl.${domain.name?lower_case}All" ng-click="ctrl.setSelected(${domain.name?lower_case}.id)" 
				ng-class="{selected: ${domain.name?lower_case}.id === ctrl.idSelected }" 
				ng-dblclick="ctrl.setSelected(${domain.name?lower_case}.id);ctrl.goto${domain.name}.update(ctrl.idSelected);">

				<#list domain.attributes> 
				<#items as attribute>
				<td>{{${domain.name?lower_case}.${attribute.name} <#if attribute.dataType == "Date"> | formatLocalDate </#if> <#if attribute.dataType == "Timestamp"> | formatLocalTime </#if> }}</td>
				</#items>
				</#list>
			</tr>
		</tbody>
		<tfoot></tfoot>
		</table>
	</div>	
</div>