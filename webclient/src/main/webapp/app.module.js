(function() {
	'use strict';

	//Declare app level module which depends on views, and components
	angular.module('ljprojectbuilderApp', [
	    'pascalprecht.translate',
	    'ngFileSaver',
	  
	    //###BEGIN### include generated files
		'ljprojectbuilderApp.app',
		'ljprojectbuilderApp.domain',
		'ljprojectbuilderApp.generator',
		'ljprojectbuilderApp.apptemplate',		
		//###END### include generated files
		'ljprojectbuilderApp.default',
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
	
	angular.module('ljprojectbuilderApp').config(function ($httpProvider) {
	    $httpProvider.interceptors.push('responseObserver');
	});
	
	angular.module('ljprojectbuilderApp').factory('responseObserver', function responseObserver($q, $window) {
	    return {
	        'responseError': function(errorResponse) {
	            switch (errorResponse.status) {
	            case 403:
	                $window.location = './#/viewcomponents/notallowed';
	                break;
	            case 500:
	                $window.location = './#/viewcomponents/internalerror';
	                break;
	            }
	            return $q.reject(errorResponse);
	        }
	    };
	});
	
	angular.module('ljprojectbuilderApp').controller('appController', appController);
	
	/**
	 * Controller for global behavior when changing the view (routeChange).
	 * @param $scope
	 * @returns
	 */
	function appController($scope, $http) {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
		});
		console.log('checking profile image');
		console.log($scope.userImageURL);
		if($scope.userImageURL === undefined) {
			$http.get('api/user/image')
				.then(function(response){
					console.log(response);
					$scope.userImageURL=response.data;
				}, function(){
					console.log("couldn't load profile image");
				});
		}
	}
	
	angular.module('ljprojectbuilderApp').factory('restConnectorFactory', restConnectorFactory);
	angular.module('ljprojectbuilderApp').factory('dialogService', dialogService);
})();