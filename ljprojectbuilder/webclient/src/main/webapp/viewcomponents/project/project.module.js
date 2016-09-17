/*
 * Immediately Invoked Function Expression to avoid global variables: 
 * https://github.com/johnpapa/angular-styleguide/blob/master/a1/README.md#style-y010
 */
(function() {
	'use strict';
	
	angular.module('ljprojectbuilderApp.project', ['ngRoute','pascalprecht.translate']);
	angular.module('ljprojectbuilderApp.project').factory('projectConnectorFactory', projectConnectorFactory)
	angular.module('ljprojectbuilderApp.project').factory('projectSetupConnectorFactory', projectSetupConnectorFactory);
})();
