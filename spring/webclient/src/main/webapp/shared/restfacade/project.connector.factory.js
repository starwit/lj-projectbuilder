projectConnectorFactory = ['$http', '$location', 'restConnectorFactory', function projectConnectorFactory ($http, $location, restConnectorFactory) {
    var factory = {
    		getProjectAll: getProjectAll,
    		loadProject: loadProject,
    		getTemplateAll: getTemplateAll,
    		createProject: createProject,
    		updateProject: updateProject,
    		deleteProject : deleteProject,
    		getBranchnames : getBranchnames
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
	
	function getBranchnames(remoteLocation) {
		return $http.post('api/project/branchnames/', remoteLocation)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	function getTemplateAll() {
		return $http.get('api/projecttemplate/query/all')
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