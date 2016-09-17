projectConnectorFactory = ['$http', '$location', 'restConnectorFactory', function projectConnectorFactory ($http, $location, restConnectorFactory) {
    var factory = {
    		getProjectAll: getProjectAll,
    		loadProject: loadProject,
    		createProject: createProject,
    		updateProject: updateProject,
    		deleteProject : deleteProject
     };
    return factory;
	
	function getProjectAll() {
		return $http.get('api/project/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function loadProject(id) {
		return $http.get('api/project/query/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function createProject(project) {
		return $http.put('api/project/', project)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function updateProject(project) {
		return $http.post('api/project/', project)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function deleteProject(id) {
		return $http.delete('api/project/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
}];