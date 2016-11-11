<#compress>
<div id="headerBox" ng-include="'menu.html'"></div>

<div id="bodyBox">
	<div class="contain">
		<form name="ctrl.form" ng-init="sent=true;"> 
		<#list (attributes) as attribute> 
		<fieldset class="odd">
			<#if attribute.pattern??><span class="error" ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.pattern' | translate}}</span><div class="clear"></div></#if>	
			<#if !attribute.nullable><span class="error" ng-show="ctrl.form.$error.required">{{'error.required' | translate}}</span><div class="clear"></div></#if>	
			<#if attribute.min??><span class="error" ng-show="ctrl.form.${attribute.name?lower_case}.$error.minlength">{{'error.minlength' | translate}}</span><div class="clear"></div></#if>
			<#if attribute.max??><span class="error" ng-show="ctrl.form.${attribute.name?lower_case}.$error.maxlength">{{'error.maxlength' | translate}}</span><div class="clear"></div></#if>
			
			<#if attribute.dataType == "String"> 
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="text" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" 
				<#if attribute.pattern??> ng-pattern="${attribute.pattern}"</#if>	
				<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
				<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>
			/>
			<div class="clear"></div>
			</#if>
			<#if attribute.dataType == "Integer"> 
			<span class="error" ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="text" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" numberinput='' ng-pattern="/^[0-9]+$/"	
				<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
				<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>
			/>
			<div class="clear"></div>
			</#if>
			<#if attribute.dataType == "BigDecimal"> 
			<span class="error" ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="text" placeholder="_,_" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" numberinput='' ng-pattern="/^\d{1,4}(\.\d{0,4})?$/"	
				<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
				<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>			
			/>
			<div class="clear"></div>
			</#if>
			<#if attribute.dataType == "Double"> 
			<span class="error" ng-show="ctrl.form..${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="text" placeholder="_,_" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" numberinput='' ng-pattern="/^\d{1,4}(\.\d{0,4})?$/"
				<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
				<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>				
			/>
			</#if>
		</fieldset>	
		</#list>
		<div class="submitArea">
			<button class="buttonRounded green" ng-click="ctrl.doMaintain()"><img src="res/img/symbols/save-wh.png" border="0" /><span class="hidden">Speichern</span></button>
			<button class="buttonRounded grey" ng-click="ctrl.goto${domain?lower_case}.all"><img src="res/img/symbols/refresh-wh.png" border="0" /><span class="hidden">Formular zur&uuml;cksetzen</span></button>
	</form>	
	</div>
	</div>
</#compress>