/**
 * This controller maintains a '${domain.name?lower_case} object and belongs to the view '${domain.name?lower_case}.single.html.
 */
(function() {
	'use strict';
	angular.module('${project.title?lower_case}App.${domain.name?lower_case}').controller('${domain.name?lower_case}SingleCtrl', ${domain.name?lower_case}SingleCtrl);
	
	${domain.name?lower_case}SingleCtrl.$inject = ['$scope', '$routeParams', '${domain.name?lower_case}ConnectorFactory', 'goto${domain.name}'];
	function ${domain.name?lower_case}SingleCtrl($scope, $routeParams, ${domain.name?lower_case}ConnectorFactory, goto${domain.name}) {
		var ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.goto${domain.name} = goto${domain.name};
		init();

		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintain() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto();
			} else {
				goto${domain.name}.all();
			}
		}
		
		function doMaintainThenGoto() {
			var saveFunction = isUpdate() ? ${domain.name?lower_case}ConnectorFactory.update${domain.name} : ${domain.name?lower_case}ConnectorFactory.create${domain.name};
			saveFunction(ctrl.${domain.name?lower_case}).then(saveSuccessCallback(), function(){});
		}

		function isUpdate() {
			return ctrl.${domain.name?lower_case} != null && ctrl.${domain.name?lower_case}.id != null;
		}
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.${domain.name?lower_case} = {};
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined && $routeParams.id !== ctrl.${domain.name?lower_case}.id) {
					ctrl.${domain.name?lower_case}.id = $routeParams.id;
					${domain.name?lower_case}ConnectorFactory.load${domain.name}(ctrl.${domain.name?lower_case}.id).then(set${domain.name}, function(){});
				}
				if ($routeParams.id == null) {
					ctrl.${domain.name?lower_case} = {};
				}
			});
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function set${domain.name}(response) {
			ctrl.${domain.name?lower_case} = response;
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessCallback() {
			return function (response) {
				set${domain.name}(response);
				goto${domain.name}.all();
			}
		}
	
	}
})();