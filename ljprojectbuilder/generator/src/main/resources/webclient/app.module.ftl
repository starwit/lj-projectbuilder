<#list (domainnames) as name>
	'${appName}App.${name?lower_case}',
</#list>

/** 
 * Navigation and routing for module ljprojectbuilderApp.
 */
angular.module('{appName}App').config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/viewcomponents/${name?lower_case}-all/'});
}]);