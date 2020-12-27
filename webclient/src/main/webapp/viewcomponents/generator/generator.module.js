/**
 * Defines all dependencies (services / factories and used modules) for the module.
 */
(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.generator', ['ngRoute','pascalprecht.translate']);
	angular.module('ljprojectbuilderApp.generator').factory('projectConnectorFactory', projectConnectorFactory);
	angular.module('ljprojectbuilderApp.generator').factory('projectSetupConnectorFactory', projectSetupConnectorFactory);
	angular.module('ljprojectbuilderApp.generator').factory('domainConnectorFactory', domainConnectorFactory);
})();
