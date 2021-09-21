appConnectorFactory = ['$http', '$location', 'restConnectorFactory', function appConnectorFactory ($http, $location, restConnectorFactory) {
    var factory = {
    		getAppAll: getAppAll,
    		loadApp: loadApp,
    		getTemplateAll: getTemplateAll,
    		createApp: createApp,
    		updateApp: updateApp,
    		deleteApp : deleteApp,
    		getBranchnames : getBranchnames
     };
    return factory;
	
	function getAppAll() {
		return $http.get('api/app/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function loadApp(id) {
		return $http.get('api/app/query/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	function getBranchnames(remoteLocation) {
		return $http.post('api/app/branchnames/', remoteLocation)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	function getTemplateAll() {
		return $http.get('api/apptemplate/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function createApp(app) {
		return $http.put('api/app/', app)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function updateApp(app) {
		return $http.post('api/app/', app)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function deleteApp(id) {
		return $http.delete('api/app/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
}];