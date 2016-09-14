var projectControllers = {};
projectControllers.loadProjectController = function($rootScope, $scope, $location, projectConnectorFactory, $translate, gotoProject) {

	$scope.projectAll = [];
	$scope.refresh = function() { projectConnectorFactory.getProjectAll().then(	setProjectAll, null); };
	$scope.gotoUpdateProject = function(id) { gotoProject.update($location, id); };
	$scope.gotoCreateProject = function () { gotoProject.create($location); };
	$scope.deleteProject = function(id) {projectConnectorFactory.deleteProject(id).then(projectConnectorFactory.getProjectAll(), null)};
	$scope.setSelected = function (idSelected) { $scope.idSelected = idSelected; };
	
	init();
	function init() {
		//change title on view change
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
		});
		$scope.refresh();
	}
	
	function setProjectAll(response) {
		$scope.projectAll = response;		
	}
	
	
	$scope.doBack = function () {
		gotoProject.back($location);
	};
};

projectControllers.maintainProjectController = function ($rootScope, $scope, $routeParams, $location, projectConnectorFactory, $translate, gotoProject) {
	
	$scope.project = {};
	$scope.gotoAll = function() { gotoProject.all($location); };
	$scope.gotoUpdateProject = function() { gotoUpdateProject.update($location, id); }
	$scope.gotoCreateProject = function () { gotoProject.create($location); };
	init();
	
	function init() {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title = next.title;
			$scope.mode = next.mode;
			if ($routeParams.id != undefined) {
				projectConnectorFactory.loadProject($routeParams.id)
					.then(	setProject, null);
			}
		});
	};
	
	function showDialog(dialogtitle, dialogtext, dialogid) {
		$rootScope.dialog.title = dialogtitle;
		$rootScope.dialog.text = dialogtext;
		document.getElementById(dialogid).style.display = 'block';
	};
	
	function showGenerationResultDialog(response) {
		if (response.responseCode == 'OK' || response.responseCode == 'EMPTY') {
			showDialog("project.dialog.success.title", response.message, 'successdialog');
		} else {
			showDialog("project.dialog.error.title", response.message, 'errordialog');
		}
	};
	
	function setProject(response) {
		$scope.project = response;
	};
	
	function saveSuccess(response) {
		$scope.project = response;
		showDialog("project.dialog.success.title", "project.save.success", 'successdialog');
	};
	
	function saveError(response) {
		$rootScope.dialog.title = "project.dialog.error.title";
		showDialog("project.dialog.error.title", "project.save.error", 'errordialog');
	};
	
	$scope.doMaintain = function () {
		$rootScope.dialog = {};
		if ($scope.mode == 'update') {
			projectConnectorFactory.updateProject($scope.project).then(
					saveSuccess, 
					saveError
			);
		} else {
			projectConnectorFactory.createProject($scope.project).then(
					saveSuccess, 
					saveError
			);
		}
	};
	
	$scope.doProjectSetup = function () {
		$rootScope.dialog = {};
		projectSetupConnectorFactory.projectSetup($scope.project)
		.then(showGenerationResultDialog);
	};
	
	$scope.doRename = function () {
		$rootScope.dialog = {};
		projectSetupConnectorFactory.rename($scope.project)
		.then(showGenerationResultDialog);
	};
	
	$scope.doRenamePackage = function () {
		$rootScope.dialog = {};
		projectSetupConnectorFactory.renamePackage($scope.project)
		.then(showGenerationResultDialog);
	};
	
	$scope.doBack = function () {
		gotoProject.all($location);
	};
};

projectControllers.projectGenerateController = function($scope, $routeParams, $location, domainConnectorFactory, projectConnectorFactory, gotoProject) {

	$scope.domainAll = [];
	$scope.refresh = function() { domainConnectorFactory.getDomainsByProject($routeParams.id); };

	
	init();
	function init() {
		//change title on view change
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
			if ($routeParams.id != undefined) {
				$scope.projectid = $routeParams.id;
				domainConnectorFactory.getDomainsByProject($routeParams.id)
					.then(
						function(response) { $scope.domainAll = response; }, null
					);
				
				projectConnectorFactory.loadProject($scope, $routeParams.id)
				.then(	
						function(response) { $scope.project = response; }
				);
			}
		});
		$scope.refresh();
	}
	
	$scope.doBack = function () {
		gotoProject.back($location);
	};
};