(function() {
	'use strict';

	//Declare app level module which depends on views, and components
	angular.module('ljprojectbuilderApp', [
	    'pascalprecht.translate',
	  
	    //###BEGIN### include generated files
		'ljprojectbuilderApp.project',
		'ljprojectbuilderApp.domain',
		'ljprojectbuilderApp.generator',
		//###END### include generated files
		'ngRoute'
	]);
	
	angular.module('ljprojectbuilderApp').config(['$translateProvider', function($translateProvider) {
		$translateProvider
		.useStaticFilesLoader({
			prefix: 'localization/translations-',
			suffix: '.json'
		}).preferredLanguage('de-DE')
			.useSanitizeValueStrategy('escaped') // Security for escaping variables
			.usePostCompiling(true); // Post compiling angular filters
	}]);
	
	angular.module('ljprojectbuilderApp').controller('appController', appController);
	
	/**
	 * Controller for global behavior when changing the view (routeChange).
	 * @param $scope
	 * @returns
	 */
	function appController($scope) {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
		});
	}
	
	angular.module('ljprojectbuilderApp').factory('restConnectorFactory', restConnectorFactory);
})();