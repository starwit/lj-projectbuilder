projectSetupConnectorFactory = ['$http', '$location', 'restConnectorFactory',
	function projectSetupConnectorFactory($http, $location, restConnectorFactory) {
	    var factory = {
	    		projectSetup: projectSetup,
	    		checkIfRepoServerWorks: checkIfRepoServerWorks,
	    		createTargetRepo: createTargetRepo
	     };
	    return factory;
	    
		function projectSetup(dto) {
			return $http.post('api/projectsetup/downloadproject', dto)
				.then(
						restConnectorFactory.handleResponseSuccess,
						restConnectorFactory.handleResponseError
				);
		};
		
		function checkIfRepoServerWorks(dto) {
			return $http.post('api/projectsetup/currentrepos', dto)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);			
		};
		
		function createTargetRepo(dto) {
			return $http.post('api/projectsetup/createtargetrepo', dto)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);			
		};
	}];