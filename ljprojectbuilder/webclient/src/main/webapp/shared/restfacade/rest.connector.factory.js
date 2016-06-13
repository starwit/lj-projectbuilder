function restConnectorFactory ($http, $location) {
	var factory = {};
	
	factory.handleResponse = function($scope, response, successPath, errorPath) {
		content = response.data;
		// promise fulfilled
        if (content.metadata.responseCode === 'OK') {
        	successPath($location);
        } else {
        	$scope.message = content.metadata.message;		
        	$scope.validationErrors = content.metadata.validationErrors;	
        	errorPath($location);
        }
	};
	
	factory.handleResponseSuccess = function(response) {
		if (response.data.metadata.responseCode == 'OK' || response.data.metadata.responseCode == 'EMPTY') {
			return response.data.result;
		} else {
			return $q.reject(response.data.metadata.message);
		}
	}
	
	factory.handleResponseError = function(response) {
		return $q.reject(response.data.metadata.message);
	}
	
	factory.getAllowedFields = function($scope, allowedFields) {
		$scope.allowedfields = {};
		var i = 0;
		var len = allowedFields.length;
		var text = "";
		for (; i < len; i++) {
			var key = allowedFields[i].fieldname;
			var obj = {};
			$scope.allowedfields[key] = allowedFields[i].values;
		}
	};
	return factory;
}