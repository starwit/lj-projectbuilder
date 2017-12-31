projecttemplateConnectorFactory = ['$http', '$location', 'restConnectorFactory', function projecttemplateConnectorFactory ($http, $location, restConnectorFactory) {
    var factory = {
    		getProjectTemplateAll: getProjectTemplateAll,
    		loadProjectTemplate: loadProjectTemplate,
    		createProjectTemplate: createProjectTemplate,
    		updateProjectTemplate: updateProjectTemplate,
    		deleteProjectTemplate : deleteProjectTemplate
     };
    return factory;
	
	function getProjectTemplateAll() {
		return $http.get('api/projecttemplate/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function loadProjectTemplate(id) {
		return $http.get('api/projecttemplate/query/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	function createProjectTemplate(projecttemplate) {
		return $http.put('api/projecttemplate/', projecttemplate)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function updateProjectTemplate(projecttemplate) {
		return $http.post('api/projecttemplate/', projecttemplate)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function deleteProjectTemplate(id) {
		return $http.delete('api/projecttemplate/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
}];