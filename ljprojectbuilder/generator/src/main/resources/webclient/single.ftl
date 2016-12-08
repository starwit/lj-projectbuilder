<#compress>
<div id="headerBox" ng-include="'menu.html'"></div>

<div id="bodyBox">
	<div class="contain">
		<form name="ctrl.form" ng-init="sent=true;"> 
		<#list (attributes) as attribute> 
		<fieldset>
			<#if attribute.dataType == "String"> 
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="text" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" 
				<#if attribute.pattern?? && attribute.pattern?length &gt; 0 > ng-pattern="/${attribute.pattern}/"</#if>	
				<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
				<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>
			/>
			</#if>
			
			<#if attribute.dataType == "Integer"> 
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="number" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" numberinput='' ng-pattern="/^[0-9]+$/"	
				<#if attribute.min??> min="${attribute.min}"</#if>
				<#if attribute.max??> max="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>
			/>
			<label ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</label>
			</#if>
			
			<#if attribute.dataType == "BigDecimal"> 
			<span class="error" ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="text" placeholder="_,_" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" numberinput='' ng-pattern="/^\d{1,4}(\.\d{0,4})?$/"	
				<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
				<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>			
			/>
			<label ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</label>
			</#if>
			
			<#if attribute.dataType == "Double"> 
			<span class="error" ng-show="ctrl.form..${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</span><div class="clear"></div>
			<label for="${attribute.name?lower_case}">{{'${domain?lower_case}.${attribute.name}' | translate}}:</label>
			<input name="${attribute.name?lower_case}" id="${attribute.name?lower_case}" type="text" placeholder="_,_" ng-model="ctrl.${domain?lower_case}.${attribute.name?uncap_first}" numberinput='' ng-pattern="/^\d{1,4}(\.\d{0,4})?$/"
				<#if attribute.min??> ng-minlength="${attribute.min}"</#if>
				<#if attribute.max??> ng-maxlength="${attribute.max}"</#if>
				<#if !attribute.nullable> required</#if>				
			/>
			<label ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.number' | translate}}</label>
			</#if>
			
			<#if attribute.min??><label  ng-show="ctrl.form.${attribute.name?lower_case}.$error.minlength">{{'error.minlength' | translate}}</label></#if>
			<#if attribute.max??><label  ng-show="ctrl.form.${attribute.name?lower_case}.$error.maxlength">{{'error.maxlength' | translate}}</label></#if>
			<#if !attribute.nullable><label  ng-show="ctrl.form.${attribute.name?lower_case}.$error.required">{{'error.required' | translate}}</label></#if>
			<#if attribute.pattern??><label ng-show="ctrl.form.${attribute.name?lower_case}.$error.pattern">{{'error.pattern' | translate}}</label></#if>
			<div class="clear"></div>
		</fieldset>	
		</#list>
		<div class="submitArea">
			<button class="buttonRounded" ng-click="ctrl.goto${domain}.all();"><img src="res/img/icons/back.png" border="0" /><span class="hidden">{{'button.back' | translate}}</span></button>
			<button class="buttonRounded right" ng-click="ctrl.doMaintain()"><img src="res/img/icons/save.png" border="0" /><span class="hidden">{{'button.save' | translate}}</span></button>
		</div>
	</form>	
	</div>
	</div>
</#compress>