describe('The app maintain site', function() {

    var testApp = {
        "description"           : "testApp",
        "templateLocation"      : "https://github.com/witchpou/lirejarp",
        "templateTitle"         : "lirejarp",
        "templatePackagePrefix" : "de.starwit",
        "targetPath"            : "/testApp",
        "title"                 : "testApp",
        "packagePrefix"         : "de.test"
    };
    var apps = element.all(by.repeater('app in ctrl.appAll'));

    // delete every app before starting the test suite
    beforeAll(function() {
        browser.get('http://localhost:8080/ljappbuilder/#/viewcomponents/app-all/');
        apps.each(function(app, index) {
            app.element(by.id('delete')).click();
        });
    });

    it('should add a new app and display it correctly', function() {
        browser.get('http://localhost:8080/ljappbuilder/#/viewcomponents/app-maintain/create/');
        element(by.model('ctrl.app.description')).sendKeys(testApp.description);
        element(by.model('ctrl.app.templateLocation')).sendKeys(testApp.templateLocation);
        element(by.model('ctrl.app.templateTitle')).sendKeys(testApp.templateTitle);
        element(by.model('ctrl.app.templatePackagePrefix')).sendKeys(testApp.templatePackagePrefix);
        element(by.model('ctrl.app.targetPath')).sendKeys(testApp.targetPath);
        element(by.model('ctrl.app.title')).sendKeys(testApp.title);
        element(by.model('ctrl.app.packagePrefix')).sendKeys(testApp.packagePrefix);
        element(by.id('save')).click();

        browser.get('http://localhost:8080/ljappbuilder/#/viewcomponents/app-all/');

        expect(apps.count()).toEqual(1);
        expect(apps.first().element(by.tagName('h3')).getText()).toEqual(testApp.title);
    });

});
