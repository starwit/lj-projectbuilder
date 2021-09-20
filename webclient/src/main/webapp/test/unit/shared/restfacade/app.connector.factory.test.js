'use strict';

describe('appConnectorFactory', function() {

    var restConnectorFactory;
    var appConnectorFactory;
    var scope;
    var $httpBackend;

    // beforeEach(angular.mock.http.init);
    // afterEach(angular.mock.http.reset);

    beforeEach(module('ljappbuilderApp', function ($translateProvider) {
        /*
            provide empty json for translation since asynchronous
            loading of translation file fails in unit tests
            https://angular-translate.github.io/docs/#/guide/22_unit-testing-with-angular-translate
        */
        $translateProvider.translations('de-DE', {});
    }));

    beforeEach(module('ljappbuilderApp.app'));

    beforeEach(inject(function(_$injector_, $rootScope, _$httpBackend_) {
        var $injector = _$injector_;
        $httpBackend = _$httpBackend_;
        scope = $rootScope.$new();
        restConnectorFactory = $injector.get('restConnectorFactory');
        appConnectorFactory = $injector.get('appConnectorFactory');
    }));


    it('should create a app object successfully', function() {

        console.log($httpBackend.whenGET());

        $httpBackend.whenGET('api/app/query/all').passThrough();

        var testApp = {
            "title":"testApp",
            "templateLocation" : "/test",
            "templateTitle" : "lirejarp",
            "templatePackagePrefix" : "de.starwit",
            "packagePrefix" : "de.test",
            "targetPath" : "/testTarget"
        };

        // TODO: test restService
        var resultPromise = appConnectorFactory.createApp(testApp);

    });

});
