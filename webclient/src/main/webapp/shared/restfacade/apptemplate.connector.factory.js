apptemplateConnectorFactory = ['$http', '$location', 'restConnectorFactory', function apptemplateConnectorFactory ($http, $location, restConnectorFactory) {
    var factory = {
    		getCategoryAll: getCategoryAll,
    		getAppTemplateAll: getAppTemplateAll,
    		loadAppTemplate: loadAppTemplate,
    		createAppTemplate: createAppTemplate,
    		updateAppTemplate: updateAppTemplate,
    		deleteAppTemplate : deleteAppTemplate
     };
    return factory;
    
	function getCategoryAll() {
		return $http.get('api/category/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	function getAppTemplateAll() {
		return $http.get('api/apptemplate/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function loadAppTemplate(id) {
		return $http.get('api/apptemplate/query/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	function createAppTemplate(apptemplate) {
		return $http.put('api/apptemplate/', apptemplate)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function updateAppTemplate(apptemplate) {
		return $http.post('api/apptemplate/', apptemplate)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function deleteAppTemplate(id) {
		return $http.delete('api/apptemplate/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
}];