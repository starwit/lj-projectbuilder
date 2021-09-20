/**
 * Defines all dependencies (services / factories and used modules) for the module.
 */
(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.generator', ['ngRoute','pascalprecht.translate']);
	angular.module('ljprojectbuilderApp.generator').factory('appConnectorFactory', appConnectorFactory);
	angular.module('ljprojectbuilderApp.generator').factory('appSetupConnectorFactory', appSetupConnectorFactory);
	angular.module('ljprojectbuilderApp.generator').factory('domainConnectorFactory', domainConnectorFactory);
})();
