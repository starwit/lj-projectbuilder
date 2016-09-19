generatorConnectorFactory = ['$http', '$location', 'restConnectorFactory', function generatorConnectorFactory($http, $location, restConnectorFactory) {
    var factory = {
    		generate: generate
     };
    return factory;
	
	function generate(generatorinput) {
		return $http.post('api/generator/generate', generatorinput)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
}];