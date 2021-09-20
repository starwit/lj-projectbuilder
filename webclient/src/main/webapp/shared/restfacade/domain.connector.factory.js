domainConnectorFactory = ['$http', '$location', 'restConnectorFactory', 	function domainConnectorFactory($http, $location, restConnectorFactory) {
    var factory = {
    		getDomainsByApp: getDomainsByApp,
    		loadDomain: loadDomain,
    		createDomain: createDomain,
    		updateDomain: updateDomain,
    		deleteDomain: deleteDomain,
    		getTypes: getTypes
     };
    return factory;
	
	function getDomainsByApp(appId) {
		return $http.get('api/domain/query/domainsbyapp/' + appId)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	 function loadDomain(domainId) {
		return $http.get('api/domain/query/' + domainId)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	function createDomain(domain) {
		return $http.put('api/domain/', domain)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function updateDomain(domain, appid) {
		return $http.post('api/domain/', domain)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function deleteDomain(id) {
		return $http.delete('api/domain/' + id)
		.then(
				restConnectorFactory.handleResponseSuccess,
				restConnectorFactory.handleResponseError
		);
	};
	
	function getTypes() {
		return $http.get('api/domain/query/types')
		.then(function(response) {
			return content = response.data;
		});
	};
}];