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

projectControllers.maintainProjectController = function ($scope, $routeParams, $location, projectConnectorFactory, $translate, gotoProject) {
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
	
	$scope.doBack = function () {
		gotoProject.all($location);
	};
};