var projectControllers = {};
projectControllers.loadProjectController = function($rootScope, $scope, $location, projectConnectorFactory, $translate, gotoProject) {

	$scope.projectAll = [];
	$scope.refresh = function() { projectConnectorFactory.getProjectAll($scope); };
	$scope.gotoUpdateProject = function(id) { gotoProject.update($location, id); };
	$scope.gotoCreateProject = function () { gotoProject.create($location); };
	$scope.deleteProject = function(id) {	projectConnectorFactory.deleteProject($scope, id);};
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
	
	$scope.doBack = function () {
		gotoProject.back($location);
	};
};

projectControllers.maintainProjectController = function ($rootScope, $scope, $routeParams, $location, projectConnectorFactory, $translate, gotoProject) {
	$scope.project = {};
	init();
	
	function init() {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title = next.title;
			$scope.mode = next.mode;
			if ($routeParams.id != undefined) {
				projectConnectorFactory.loadProject($scope, $routeParams.id);
			}
		});
	};
	
	$scope.doMaintain = function () {
		if ($scope.mode == 'update') {
			projectConnectorFactory.updateProject($scope, function($location) {gotoProject.all($location);}, function($location) {gotoProject.update($location, $scope.project.id);});
		} else {
			projectConnectorFactory.createProject($scope, function($location) {gotoProject.all($location);}, function($location) {gotoProject.create($location);});
		}
	};
	
	$scope.doGenerate = function () {
		$rootScope.dialog = {};
		projectConnectorFactory.generate($scope.project)
		.then(function(response) {
			$rootScope.dialog.text = response.message;
			if (response.responseCode == 'OK' || response.responseCode == 'EMPTY') {
				$rootScope.dialog.title = "project.dialog.success.title";
				document.getElementById('successdialog').style.display = 'block';
			} else {
				$rootScope.dialog.title = "project.dialog.error.title";
				document.getElementById('errordialog').style.display = 'block';
			}
		});
	};
	
	$scope.doRename = function () {
		$rootScope.dialog = {};
		projectConnectorFactory.rename($scope.project)
		.then(function(response) {
			$rootScope.dialog.text = response.message;
			if (response.responseCode == 'OK' || response.responseCode == 'EMPTY') {
				$rootScope.dialog.title = "project.dialog.success.title";
				document.getElementById('successdialog').style.display = 'block';
			} else {
				$rootScope.dialog.title = "project.dialog.error.title";
				document.getElementById('errordialog').style.display = 'block';
			}
		});
	};
	
	$scope.doBack = function () {
		gotoProject.all($location);
	};
};