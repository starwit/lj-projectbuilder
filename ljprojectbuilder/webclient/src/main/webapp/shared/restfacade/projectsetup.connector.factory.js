projectSetupConnectorFactory = ['$http', '$location', 'restConnectorFactory',
	function projectSetupConnectorFactory($http, $location, restConnectorFactory) {
	    var factory = {
				projectSetup: projectSetup,
				createRepository: createRepository,
				checkInCode: checkInCode,
				createBuildJob: createBuildJob,
				createRuntimeEnv: createRuntimeEnv,
				deployApplication: deployApplication
	     };
	    return factory;
	    
		function projectSetup(dto) {
			return $http.post('api/projectsetup/downloadproject', dto)
				.then(
						restConnectorFactory.handleResponseSuccess,
						restConnectorFactory.handleResponseError
				);
		};

		function createRepository() {
			return $http.put('api/projectsetup/ci/createrepo',)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);
		}

		function checkInCode() {
			return $http.put('api/projectsetup/ci/checkincode',)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);
		}

		function createBuildJob() {
			return $http.put('api/projectsetup/ci/createbuildjob',)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);
		}

		function createRuntimeEnv() {
			return $http.put('api/projectsetup/ci/createruntimeenv',)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);
		}

		function deployApplication() {
			return $http.put('api/projectsetup/ci/deployapp',)
			.then(
					restConnectorFactory.handleResponseSuccess,
					restConnectorFactory.handleResponseError
			);
		}
	}];