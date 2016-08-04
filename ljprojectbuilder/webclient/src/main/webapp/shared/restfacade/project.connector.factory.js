function projectConnectorFactory ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.getProjectAll = function($scope) {
		$http.get('api/project/query/all')
		.then(function (response) {
			content = response.data;
			$scope.projectAll = content.result;		
		});
	};
		
	factory.loadProject = function($scope, id) {
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
		
	factory.deleteProject = function($scope, id) {
		$http.delete('api/project/' + id)
		.then(function(response) {
			content = response.data;
			$scope.protocol = content.result;
			factory.getProjectAll($scope);
		});
	};
	
	factory.getAllowedFields = function($scope) {
		$http.get('api/project/query/allowedvalues')
		.then(function(response) {
			content = response.data;
			restConnectorFactory.getAllowedFields($scope, content);
		});
	};
	return factory;
}