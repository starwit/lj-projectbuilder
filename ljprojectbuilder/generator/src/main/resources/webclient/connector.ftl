${domain?lower_case}ConnectorFactory = ['$http', '$location', 'restConnectorFactory', function ${domain?lower_case}ConnectorFactory ($http, $location, restConnectorFactory) {
    var factory = {
    		get${domain}All: get${domain}All,
    		load${domain}: load${domain},
    		create${domain}: create${domain},
    		update${domain}: update${domain},
    		delete${domain} : delete${domain}
     };
    return factory;
	
	function get${domain}All() {
		return $http.get('api/${domain?lower_case}/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function load${domain}(id) {
		return $http.get('api/${domain?lower_case}/query/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function create${domain}(${domain?lower_case}) {
		return $http.put('api/${domain?lower_case}/', ${domain?lower_case})
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function update${domain}(${domain?lower_case}) {
		return $http.post('api/project/', ${domain?lower_case})
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function delete${domain}(id) {
		return $http.delete('api/${domain?lower_case}/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
}];