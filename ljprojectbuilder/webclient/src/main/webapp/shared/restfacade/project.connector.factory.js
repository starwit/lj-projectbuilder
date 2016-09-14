projectConnectorFactory = ['$http', '$location', 'restConnectorFactory', function projectConnectorFactory ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.getProjectAll = function() {
		return $http.get('api/project/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	factory.loadProject = function(id) {
		return $http.get('api/project/query/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	factory.createProject = function(project) {
		return $http.put('api/project/', project)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	factory.updateProject = function(project) {
		return $http.post('api/project/', project)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	factory.deleteProject = function(id) {
		return $http.delete('api/project/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	return factory;
}]