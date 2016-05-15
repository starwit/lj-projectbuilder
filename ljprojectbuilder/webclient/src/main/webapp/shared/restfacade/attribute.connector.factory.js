function attributeConnectorFactory ($http, $location, restConnectorFactory) {
	var factory = {};
	
	factory.getAttributeAll = function($scope) {
		$http.get('api/attribute/query/all')
		.then(function (response) {
			content = response.data;
			$scope.attributeAll = content.result;		
		});
	};
		
	factory.loadAttribute = function($scope, id) {
		$http.get('api/attribute/query/' + id)
		.then(function (response) {
			content = response.data;
			$scope.attribute = content.result;		
		});
	};
		
	factory.createAttribute = function($scope, successPath, errorPath) {
		$http.put('api/attribute/', $scope.attribute)
		.then(function(response) {
			restConnectorFactory.handleResponse($scope, response, successPath,  errorPath);
		});
	};
		
	factory.updateAttribute = function($scope, successPath, errorPath) {
		$http.post('api/attribute/', $scope.attribute)
		.then(function(response) {
			restConnectorFactory.handleResponse($scope, response, successPath, errorPath);
		});
	};
		
	factory.deleteAttribute = function($scope, id) {
		$http.delete('api/attribute/' + id)
		.then(function(response) {
			content = response.data;
			$scope.protocol = content.result;
			factory.getAttributeAll($scope);
		});
	};
	
	factory.getAllowedFields = function($scope) {
		$http.get('api/attribute/query/allowedvalues')
		.then(function(response) {
			content = response.data;
			restConnectorFactory.getAllowedFields($scope, content);
		});
	};
		
	return factory;
}