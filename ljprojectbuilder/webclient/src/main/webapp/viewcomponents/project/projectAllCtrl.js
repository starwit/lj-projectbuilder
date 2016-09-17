(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectAllCtrl', projectAllCtrl);
	
	function projectAllCtrl(projectConnectorFactory, gotoProject) {
		var  ctrl = this;
		init();
		
		function init() {
			ctrl.projectAll = [];
			projectConnectorFactory.getProjectAll().then(setProjectAll, null);
			
			ctrl.gotoProject = gotoProject;
			ctrl.deleteProject = function(id) {projectConnectorFactory.deleteProject(id).then(projectConnectorFactory.getProjectAll(), null)};
		}
		
		function setProjectAll(response) {
			ctrl.projectAll = response;		
		}
	};
	
})();