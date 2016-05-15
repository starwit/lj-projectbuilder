'use strict';

//Declare app level module which depends on views, and components
var ljprojectbuilderApp = angular.module('ljprojectbuilderApp', [
    'pascalprecht.translate',
  
    //###BEGIN### include generated files
	'ljprojectbuilderApp.project',
	'ljprojectbuilderApp.attribute',
	'ljprojectbuilderApp.domain',
	//###END### include generated files
	'ngRoute'
]);

ljprojectbuilderApp.config(['$translateProvider', function($translateProvider) {
	$translateProvider
	.useStaticFilesLoader({
		prefix: 'localization/translations-',
		suffix: '.json'
	}).preferredLanguage('de-DE')
		.useSanitizeValueStrategy('escaped') // Security for escaping variables
		.usePostCompiling(true); // Post compiling angular filters
}]);
	
ljprojectbuilderApp.factory('restConnectorFactory', restConnectorFactory);

