(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectSingleCtrl', projectSingleCtrl);
	
	function projectSingleCtrl($scope, $routeParams, projectConnectorFactory, gotoProject) {
		var ctrl = this;
		init();
		ctrl.project = {};
		ctrl.gotoProject = gotoProject;

		ctrl.doMaintain = function () {
			ctrl.dialog = {};
			if (ctrl.project != null && ctrl.project.id != null) {
				projectConnectorFactory.updateProject(ctrl.project).then(
						saveSuccess, 
						saveError
				);
			} else {
				projectConnectorFactory.createProject(ctrl.project).then(
						saveSuccess, 
						saveError
				);
			}
		};
		
		function init() {
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined && ctrl.project.id == null) {
					projectConnectorFactory.loadProject($routeParams.id).then(setProject, null);
				}
			});
		};
		
		function setProject(response) {
			ctrl.project = response;
		};
		
		function showDialog(dialogtitle, dialogtext, dialogid) {
			ctrl.dialog.title = dialogtitle;
			ctrl.dialog.text = dialogtext;
			document.getElementById(dialogid).style.display = 'block';
		};
		
		function saveSuccess(response) {
			setProject(response);
			showDialog("project.dialog.success.title", "project.save.success", 'successdialog');
		};
		
		function saveError(response) {
			showDialog("project.dialog.error.title", "project.save.error", 'errordialog');
		};
	};
})();