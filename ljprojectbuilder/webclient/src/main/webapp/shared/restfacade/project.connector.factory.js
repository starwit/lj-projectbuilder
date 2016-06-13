function projectConnectorFactory ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.generate = function(project) {
		return $http.post('api/project/generate', project)
			.then(
				restConnectorFactory.handleResponseSuccess ,
				restConnectorFactory.handleResponseError
			);
	}
	
	factory.getProjectAll = function($scope) {
		$http.get('api/project/query/all')
		.then(function (response) {
			content = response.data;
			$scope.projectAll = content.result;		
		});
	};
		
	factory.loadProject = function($scope, id) {
		$http.get('api/project/query/' + id)
		.then(function (response) {
			content = response.data;
			$scope.project = content.result;		
		});
	};
		
	factory.createProject = function($scope, successPath, errorPath) {
		$http.put('api/project/', $scope.project)
		.then(function(response) {
			restConnectorFactory.handleResponse($scope, response, successPath,  errorPath);
		});
	};
		
	factory.updateProject = function($scope, successPath, errorPath) {
		$http.post('api/project/', $scope.project)
		.then(function(response) {
			restConnectorFactory.handleResponse($scope, response, successPath, errorPath);
		});
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