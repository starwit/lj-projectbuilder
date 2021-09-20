appSetupConnectorFactory = ['$http', '$location', 'restConnectorFactory',
	function appSetupConnectorFactory($http, $location, restConnectorFactory) {
	    var factory = {
	    		appSetup: appSetup,
	    		checkIfRepoServerWorks: checkIfRepoServerWorks,
	    		createTargetRepo: createTargetRepo
	     };
	    return factory;
	    
		function appSetup(dto) {
			return $http.post('api/appsetup/downloadapp', dto)
				.then(
						restConnectorFactory.handleResponseSuccess,
						restConnectorFactory.handleResponseError
				);
		};
		
		function checkIfRepoServerWorks(dto) {
			return $http.post('api/appsetup/currentrepos', dto)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);			
		};
		
		function createTargetRepo(dto) {
			return $http.post('api/appsetup/createtargetrepo', dto)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);			
		};
	}];