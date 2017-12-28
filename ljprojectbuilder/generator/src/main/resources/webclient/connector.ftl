${domain.name?lower_case}ConnectorFactory = ['$http', '$location', 'restConnectorFactory', function ${domain.name?lower_case}ConnectorFactory ($http, $location, restConnectorFactory) {
    var factory = {
    		get${domain.name}All: get${domain.name}All,
    		load${domain.name}: load${domain.name},
    		create${domain.name}: create${domain.name},
    		update${domain.name}: update${domain.name},
    		delete${domain.name} : delete${domain.name}
     };
    return factory;
	
	function get${domain.name}All() {
		return $http.get('api/${domain.name?lower_case}/query/all')
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function load${domain.name}(id) {
		return $http.get('api/${domain.name?lower_case}/query/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function create${domain.name}(${domain.name?lower_case}) {
		return $http.put('api/${domain.name?lower_case}/', ${domain.name?lower_case})
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function update${domain.name}(${domain.name?lower_case}) {
		return $http.post('api/${domain.name?lower_case}/', ${domain.name?lower_case})
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
		
	function delete${domain.name}(id) {
		return $http.delete('api/${domain.name?lower_case}/' + id)
		.then(
			restConnectorFactory.handleResponseSuccess,
			restConnectorFactory.handleResponseError
		);
	};
}];