function ${domain?uncap_first}ConnectorFactory ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.get${domain}All = function($scope) {
		$http.get('api/${domain?lower_case}/query/all')
		.then(function (response) {
			content = response.data;
			$scope.${domain?lower_case}All = content.result;		
		});
	};
		
	factory.load${domain} = function($scope, id) {
		$http.get('api/${domain?lower_case}/query/' + id)
		.then(function (response) {
			content = response.data;
			$scope.${domain?lower_case} = content.result;		
		});
	};
		
	factory.create${domain} = function($scope, successPath, errorPath) {
		$http.put('api/${domain?lower_case}/', $scope.${domain?lower_case})
		.then(function(response) {
			restConnectorFactory.handleResponse($scope, response, successPath,  errorPath);
		});
	};
		
	factory.update${domain} = function($scope, successPath, errorPath) {
		$http.post('api/${domain?lower_case}/', $scope.${domain?lower_case})
		.then(function(response) {
			restConnectorFactory.handleResponse($scope, response, successPath, errorPath);
		});
	};
		
	factory.delete${domain} = function($scope, id) {
		$http.delete('api/${domain?lower_case}/' + id)
		.then(function(response) {
			content = response.data;
			$scope.protocol = content.result;
			factory.get${domain}All($scope);
		});
	};
	
	factory.getAllowedFields = function($scope) {
		$http.get('api/${domain?lower_case}/query/allowedvalues')
		.then(function(response) {
			content = response.data;
			restConnectorFactory.getAllowedFields($scope, content);
		});
	};
		
	return factory;
}