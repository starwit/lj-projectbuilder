(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.generator', ['ngRoute','pascalprecht.translate']);
	angular.module('ljprojectbuilderApp.generator').factory('projectConnectorFactory', projectConnectorFactory);
	angular.module('ljprojectbuilderApp.generator').factory('projectSetupConnectorFactory', projectSetupConnectorFactory);
	angular.module('ljprojectbuilderApp.generator').factory('domainConnectorFactory', domainConnectorFactory);
	angular.module('ljprojectbuilderApp.generator').factory('generatorConnectorFactory', generatorConnectorFactory);

})();
