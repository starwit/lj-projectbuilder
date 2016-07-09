function projectConnectorFactory ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.generate = function(project) {
		return $http.post('api/project/generate', project)
			.then(function(response) { return response.data.metadata; }	);
	}
	
	factory.rename = function(project) {
		return $http.post('api/project/rename', project)
			.then(function(response) { return response.data.metadata; }	);
	}
	
	factory.renamePackage = function(project) {
		return $http.post('api/project/renamepackage', project)
			.then(function(response) { return response.data.metadata; }	);
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