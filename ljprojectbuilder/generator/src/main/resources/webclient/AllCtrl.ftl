/**
 * This controller facilitates the ${domain.name?lower_case}.all.html - view to display all ${domain.name?lower_case}s. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('${project.title?lower_case}App.${domain.name?lower_case}').controller('${domain.name?lower_case}AllCtrl', ${domain.name?lower_case}AllCtrl);
	${domain.name?lower_case}AllCtrl.$inject = ['${domain.name?lower_case}ConnectorFactory', 'goto${domain.name}'];
	function ${domain.name?lower_case}AllCtrl(${domain.name?lower_case}ConnectorFactory, goto${domain.name}) {
		
		var  ctrl = this;
		ctrl.refresh = refresh;
		ctrl.delete${domain.name} = delete${domain.name};
		ctrl.goto${domain.name} = goto${domain.name};
		ctrl.setSelected = setSelected;
		init();
		
		function setSelected(idSelected) { 
			ctrl.idSelected = idSelected; 
		}
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.${domain.name?lower_case}All = [];
			ctrl.${domain.name?lower_case} = {};
			ctrl.idSelected = null;
			${domain.name?lower_case}ConnectorFactory.get${domain.name}All().then(set${domain.name}All, null);
		}
		
		function refresh() {
			${domain.name?lower_case}ConnectorFactory.get${domain.name}All().then(set${domain.name}All, function() {});
		};
		
		function delete${domain.name}(id) {
			${domain.name?lower_case}ConnectorFactory.delete${domain.name}(id).then(deleteSuccess, function() {})
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function set${domain.name}All(response) {
			ctrl.${domain.name?lower_case}All = response;		
		}
		
		/**
		 * Success message after deleting.
		 */
		function deleteSuccess(response) {
			refresh();
			goto${domain.name}.all();
		};
	};
})();