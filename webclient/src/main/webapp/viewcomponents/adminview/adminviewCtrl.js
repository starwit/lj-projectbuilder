/**
 * This controller facilitates the projecttemplate.all.html - view to display all projecttemplates. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.adminview').controller('adminviewCtrl', adminviewCtrl);
	adminviewCtrl.$inject = ['$http'];

	function adminviewCtrl($http) {
		var  ctrl = this;

		ctrl.showKnownUser = showKnownUser;
		init();
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			console.log('init');
		}
		
		function showKnownUser() {
			$http.get('api/admin/knownUsers').then(
				function(response) {
					console.log(response);
			});
		}
	};
})();