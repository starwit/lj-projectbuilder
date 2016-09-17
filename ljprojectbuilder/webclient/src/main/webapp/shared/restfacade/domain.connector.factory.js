domainConnectorFactory = ['$http', '$location', 'restConnectorFactory', 	function domainConnectorFactory($http, $location, restConnectorFactory) {
    var factory = {
    		getDomainsByProject: getDomainsByProject,
    		loadDomain: loadDomain,
    		createDomain: createDomain,
    		updateDomain: updateDomain,
    		deleteDomain: deleteDomain,
    		getTypes: getTypes
     };
    return factory;
	
	function getDomainsByProject(projectId) {
		return $http.get('api/domain/query/domainsbyproject/' + projectId)
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
		
	function updateDomain(domain) {
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