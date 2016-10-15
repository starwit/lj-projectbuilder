projectSetupConnectorFactory = ['$http', '$location', 'restConnectorFactory',
	function projectSetupConnectorFactory($http, $location, restConnectorFactory) {
	    var factory = {
	    		projectSetup: projectSetup
	     };
	    return factory;
	    
		function projectSetup(dto) {
			return $http.post('api/projectsetup/downloadproject', dto)
				.then(
						restConnectorFactory.handleResponseSuccess,
						restConnectorFactory.handleResponseError
				);
		};
	}];