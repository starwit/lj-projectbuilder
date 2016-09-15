(function() {
	'use strict';
	
	angular.module('ljprojectbuilderApp.project', ['ngRoute','pascalprecht.translate']);
	angular.module('ljprojectbuilderApp.project').factory('projectConnectorFactory', projectConnectorFactory)
	angular.module('ljprojectbuilderApp.project').factory('projectSetupConnectorFactory', projectSetupConnectorFactory);
})();
