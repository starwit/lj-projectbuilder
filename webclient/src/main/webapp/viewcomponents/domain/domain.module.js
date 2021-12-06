/**
 * Defines all dependencies (services / factories and used modules) for the module.
 */
(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.domain', ['ngRoute','pascalprecht.translate']);
	angular.module('ljprojectbuilderApp.domain').factory('domainConnectorFactory', domainConnectorFactory);
})();