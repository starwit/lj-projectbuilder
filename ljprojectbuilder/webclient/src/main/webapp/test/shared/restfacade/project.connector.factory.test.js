'use strict';

describe('projectConnectorFactory', function() {

    var restConnectorFactory;
    var projectConnectorFactory;
    var scope;

    beforeEach(module('ljprojectbuilderApp', function ($translateProvider) {
        /*
            provide empty json for translation since asynchronous
            loading of translation file fails in unit tests
            https://angular-translate.github.io/docs/#/guide/22_unit-testing-with-angular-translate
        */
        $translateProvider.translations('de-DE', {});
    }));

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

        // TODO: test restService
        var resultPromise = projectConnectorFactory.createProject(testProject);

    });

});
