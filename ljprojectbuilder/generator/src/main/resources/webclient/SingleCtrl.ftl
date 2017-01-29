/**
 * This controller maintains a '${domain?lower_case} object and belongs to the view '${domain?lower_case}.single.html.
 */
(function() {
	'use strict';
	angular.module('${appName}App.${domain?lower_case}').controller('${domain?lower_case}SingleCtrl', ${domain?lower_case}SingleCtrl);
	
	${domain?lower_case}SingleCtrl.$inject = ['$scope', '$routeParams', '${domain?lower_case}ConnectorFactory', 'goto${domain}'];
	function ${domain?lower_case}SingleCtrl($scope, $routeParams, ${domain?lower_case}ConnectorFactory, goto${domain}) {
		var ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.goto${domain} = goto${domain};
		init();

		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintain() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto();
			} else {
				goto${domain}.all();
			}
		}
		
		function doMaintainThenGoto() {
			var saveFunction = isUpdate() ? ${domain?lower_case}ConnectorFactory.update${domain} : ${domain?lower_case}ConnectorFactory.create${domain};
			saveFunction(ctrl.${domain?lower_case}).then(saveSuccessCallback(), function(){});
		}

		function isUpdate() {
			return ctrl.${domain?lower_case} != null && ctrl.${domain?lower_case}.id != null;
		}
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.${domain?lower_case} = {};
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined && $routeParams.id !== ctrl.${domain?lower_case}.id) {
					ctrl.${domain?lower_case}.id = $routeParams.id;
					${domain?lower_case}ConnectorFactory.load${domain}(ctrl.${domain?lower_case}.id).then(set${domain}, function(){});
				}
				if ($routeParams.id == null) {
					ctrl.${domain?lower_case} = {};
				}
			});
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function set${domain}(response) {
			ctrl.${domain?lower_case} = response;
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessCallback() {
			return function (response) {
				set${domain}(response);
				goto${domain}.all();
			}
		}
	
	}
})();