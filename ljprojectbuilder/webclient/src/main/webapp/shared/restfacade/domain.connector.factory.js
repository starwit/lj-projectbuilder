domainConnectorFactory = ['$http', '$location', 'restConnectorFactory', function  ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.getDomainsByProject = function(projectId) {
		return $http.get('api/domain/query/domainsbyproject/' + projectId)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	factory.loadDomain = function(domainId) {
		return $http.get('api/domain/query/' + domainId)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
	
	factory.createDomain= function(domain) {
		return $http.put('api/domain/', domain)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	factory.updateDomain = function(domain) {
		return $http.post('api/domain/', domain)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	factory.deleteDomain = function(id) {
		return $http.delete('api/domain/' + id)
		.then(
				restConnectorFactory.handleResponseSuccess,
				restConnectorFactory.handleResponseError
		);
	};
	
	factory.getTypes = function() {
		return $http.get('api/domain/query/types')
		.then(function(response) {
			return content = response.data;
		});
	};
		
	return factory;
}]