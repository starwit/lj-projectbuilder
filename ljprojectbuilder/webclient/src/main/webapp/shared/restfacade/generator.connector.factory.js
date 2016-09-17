(function() {
	'use strict';
	
	generatorConnectorFactory = ['$http', '$location', 'restConnectorFactory', function  ($http, $location, restConnectorFactory) {
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
})();