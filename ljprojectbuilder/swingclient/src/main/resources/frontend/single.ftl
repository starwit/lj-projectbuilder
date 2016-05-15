<div class="subPartTitle"><h1>{{title | translate}}</h1></div>
<div class="subPart addressSingle">
	<form class="editForm" name="${domain?lower_case}Form">   
			<div class="errorGlobal">
				<b>{{message}}</b>
				<ul>
					<li ng-repeat="error in validationErrors">{{error.fieldname}}: {{error.message}}</li>
				</ul>
			</div>
		<div class="editArea">
		<#list (attributes) as attribute> 
		<fieldset>
			<#if attribute.pattern??><span class="error" ng-show="${domain?lower_case}Form.${attribute.columnName?lower_case}.$error.pattern">{{'error.pattern' | translate}}</span><div class="clear"></div></#if>	
			<#if !attribute.nullable><span class="error" ng-show="${domain?lower_case}Form.${attribute.columnName?lower_case}.$error.required">{{'error.required' | translate}}</span><div class="clear"></div></#if>	
			<#if attribute.min??><span class="error" ng-show="${domain?lower_case}Form.${attribute.columnName?lower_case}.$error.minlength">{{'error.minlength' | translate}}</span><div class="clear"></div></#if>
			<#if attribute.max??><span class="error" ng-show="${domain?lower_case}Form.${attribute.columnName?lower_case}.$error.maxlength">{{'error.maxlength' | translate}}</span><div class="clear"></div></#if>
			
			<#if attribute.dataType == "String"> 
			<label for="${attribute.columnName?lower_case}">{{'${domain?lower_case}.${attribute.columnName}' | translate}}:</label>
			<input name="${attribute.columnName?lower_case}" id="${attribute.columnName?lower_case}" type="text" ng-model="${domain?lower_case}.${attribute.columnName?uncap_first}" 
			<#if attribute.pattern??> ng-pattern="${attribute.pattern}"</#if>	
			<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
			<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
			<#if !attribute.nullable> required</#if>
			/>
			<div class="clear"></div>
			</#if>
			<#if attribute.dataType == "Integer"> 
			<span class="error" ng-show="${domain?lower_case}Form.${attribute.columnName?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.columnName?lower_case}">{{'${domain?lower_case}.${attribute.columnName}' | translate}}:</label>
			<input name="${attribute.columnName?lower_case}" id="${attribute.columnName?lower_case}" type="text" ng-model="${domain?lower_case}.${attribute.columnName?uncap_first}" numberinput='' ng-pattern="/^[0-9]+$/"	
			<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
			<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
			<#if !attribute.nullable> required</#if>
			/>
			<div class="clear"></div>
			</#if>
			<#if attribute.dataType == "BigDecimal"> 
			<span class="error" ng-show="${domain?lower_case}Form.${attribute.columnName?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.columnName?lower_case}">{{'${domain?lower_case}.${attribute.columnName}' | translate}}:</label>
			<input name="${attribute.columnName?lower_case}" id="${attribute.columnName?lower_case}" type="text" placeholder="_,_" ng-model="${domain?lower_case}.${attribute.columnName?uncap_first}" numberinput='' ng-pattern="/^\d{1,4}(\.\d{0,4})?$/"	
			<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
			<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
			<#if !attribute.nullable> required</#if>			
			/>
			<div class="clear"></div>
			</#if>
			<#if attribute.dataType == "Double"> 
			<span class="error" ng-show="${domain?lower_case}Form.${attribute.columnName?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.columnName?lower_case}">{{'${domain?lower_case}.${attribute.columnName}' | translate}}:</label>
			<input name="${attribute.columnName?lower_case}" id="${attribute.columnName?lower_case}" type="text" placeholder="_,_" ng-model="${domain?lower_case}.${attribute.columnName?uncap_first}" numberinput='' ng-pattern="/^\d{1,4}(\.\d{0,4})?$/"
			<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
			<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
			<#if !attribute.nullable> required</#if>				
			/>
			</#if>
		</fieldset>	
		</#list>
		</div>
		<div class="submitArea">
			<button class="buttonRounded green" ng-click="doMaintain()"><img src="res/img/symbols/save-wh.png" border="0" /><span class="hidden">Speichern</span></button>
			<button class="buttonRounded grey" ng-click="doBack()"><img src="res/img/symbols/refresh-wh.png" border="0" /><span class="hidden">Formular zur&uuml;cksetzen</span></button>
	</form>	
</div>