/**
 *  Defines all dependencies (services / factories and used modules) for the mo
 * (function() { - syntax: Immediately Invoked Function Expression to avoid global variables: https://github.com/johnpapa/angular-styleguide/blob/master/a1/README.md#style-y010
 */
(function() {
	'use strict';
	
	angular.module('${project.title?lower_case}App.${domain.name?lower_case}', ['ngRoute','pascalprecht.translate']);
	angular.module('${project.title?lower_case}App.${domain.name?lower_case}').factory('${domain.name?lower_case}ConnectorFactory', ${domain.name?lower_case}ConnectorFactory);
})();
