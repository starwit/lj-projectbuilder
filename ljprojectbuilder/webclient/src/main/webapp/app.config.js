'use strict';

// Declare app level module which depends on views, and components
var lirejarpApp = angular.module('lirejarpApp', [
    'pascalprecht.translate',
    //###BEGIN### include generated files
	//###END### include generated files
	'ngRoute'
]);

lirejarpApp.config(['$translateProvider', function($translateProvider) {
	$translateProvider
	.useStaticFilesLoader({
		prefix: 'localization/translations-',
		suffix: '.json'
	}).preferredLanguage('de-DE')
		.useSanitizeValueStrategy('escaped') // Security for escaping variables
		.usePostCompiling(true); // Post compiling angular filters
}]);
	
lirejarpApp.factory('restConnectorFactory', restConnectorFactory);
