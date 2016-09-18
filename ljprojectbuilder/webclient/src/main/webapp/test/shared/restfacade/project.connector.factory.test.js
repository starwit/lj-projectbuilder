'use strict';

describe('projectConnectorFactory', function() {

    var restConnectorFactory;
    var projectConnectorFactory;
    var scope;

    beforeEach(module('ljprojectbuilderApp'));

    beforeEach(module('ljprojectbuilderApp.project'));

    beforeEach(inject(function(_$injector_, $rootScope) {
        var $injector = _$injector_;
        scope = $rootScope.$new();
        restConnectorFactory = $injector.get('restConnectorFactory');
        projectConnectorFactory = $injector.get('projectConnectorFactory');
    }));

    it('should create a project object successfully', function() {
        var testProject = {
            "title":"testProject",
            "templateLocation" : "/test",
            "templateTitle" : "lirejarp",
            "templatePackagePrefix" : "de.starwit",
            "packagePrefix" : "de.test",
            "targetPath" : "/testTarget"
        };

        scope.$digest();
        var resultPromise = projectConnectorFactory.createProject(testProject);

        resultPromise.then(function(result) {
            console.log(result);
        });

        scope.$digest();
    });

});
