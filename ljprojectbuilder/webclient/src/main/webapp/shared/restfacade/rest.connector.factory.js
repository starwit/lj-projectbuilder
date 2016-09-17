(function() {
	'use strict';
	
	restConnectorFactory = ['$http', '$location', '$q', function  ($http, $location, $q) {
	    var factory = {
	    		handleResponseSuccess: handleResponseSuccess,
	    		handleResponseError: handleResponseError,
	    		getAllowedFields: getAllowedFields
	     };
	    return factory;
		
		function handleResponseSuccess(response) {
			if (response.data.metadata.responseCode == 'OK' || response.data.metadata.responseCode == 'EMPTY') {
				return response.data.result;
			} else {
				return $q.reject(response.data.metadata.message);
			}
		}
		
		function handleResponseError(response) {
			return $q.reject(response.data.metadata.message);
		}
		
		function getAllowedFields($scope, allowedFields) {
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
	}];
})();