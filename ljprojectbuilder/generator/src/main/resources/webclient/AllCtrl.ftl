/**
 * This controller facilitates the ${domain?lower_case}.all.html - view to display all ${domain?lower_case}s. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('${appName}.${domain?lower_case}').controller('${domain?lower_case}AllCtrl', ${domain?lower_case}AllCtrl);
	projectAllCtrl.$inject = ['${domain?lower_case}ConnectorFactory', 'goto${domain}'];
	function ${domain?lower_case}AllCtrl(${domain?lower_case}ConnectorFactory, goto${domain}) {
		
		var  ctrl = this;
		ctrl.refresh = refresh;
		ctrl.delete${domain} = delete${domain};
		ctrl.goto${domain} = goto${domain};
		init();
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.${domain?lower_case}All = [];
			ctrl.${domain?lower_case} = {};
			${domain?lower_case}ConnectorFactory.get${domain}All().then(set${domain}All, null);
		}
		
		function refresh() {
			${domain?lower_case}ConnectorFactory.get${domain}All().then(set${domain}All, function() {});
		};
		
		function deleteProject(id) {
			${domain?lower_case}ConnectorFactory.delete${domain}(id).then(deleteSuccess, function() {})
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function set${domain}All(response) {
			ctrl.${domain?lower_case}All = response;		
		}
		
		/**
		 * Success message after deleting.
		 */
		function deleteSuccess(response) {
			refresh();
			goto${domain}.all();
		};
	};
})();