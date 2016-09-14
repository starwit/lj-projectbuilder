projectSetupConnectorFactory = ['$http', '$location', 'restConnectorFactory', function  ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.projectSetupAll = function(project) {
		return $http.post('api/projectsetup/setupall', project)
			.then(function(response) { return response.data.metadata; }	);
	};
	
	factory.projectSetup = function(project) {
		return $http.post('api/projectsetup/setup', project)
			.then(function(response) { return response.data.metadata; }	);
	};
	
	factory.rename = function(project) {
		return $http.post('api/projectsetup/rename', project)
			.then(function(response) { return response.data.metadata; }	);
	};
	
	factory.renamePackage = function(project) {
		return $http.post('api/projectsetup/renamepackage', project)
			.then(function(response) { return response.data.metadata; }	);
	};
	
	return factory;
}]