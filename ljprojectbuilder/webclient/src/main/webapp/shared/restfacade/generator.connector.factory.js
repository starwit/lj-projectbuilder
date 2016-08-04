function generatorConnectorFactory ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.generate = function(generatorinput) {
		return $http.post('api/generator/generate', generatorinput)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};

	return factory;
}