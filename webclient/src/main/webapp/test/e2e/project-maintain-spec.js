describe('The project maintain site', function() {

    var testProject = {
        "description"           : "testProject",
        "templateLocation"      : "https://github.com/witchpou/lirejarp",
        "templateTitle"         : "lirejarp",
        "templatePackagePrefix" : "de.starwit",
        "targetPath"            : "/testProject",
        "title"                 : "testProject",
        "packagePrefix"         : "de.test"
    };
    var projects = element.all(by.repeater('project in ctrl.projectAll'));

    // delete every project before starting the test suite
    beforeAll(function() {
        browser.get('http://localhost:8080/ljprojectbuilder/#/viewcomponents/project-all/');
        projects.each(function(project, index) {
            project.element(by.id('delete')).click();
        });
    });

    it('should add a new project and display it correctly', function() {
        browser.get('http://localhost:8080/ljprojectbuilder/#/viewcomponents/project-maintain/create/');
        element(by.model('ctrl.project.description')).sendKeys(testProject.description);
        element(by.model('ctrl.project.templateLocation')).sendKeys(testProject.templateLocation);
        element(by.model('ctrl.project.templateTitle')).sendKeys(testProject.templateTitle);
        element(by.model('ctrl.project.templatePackagePrefix')).sendKeys(testProject.templatePackagePrefix);
        element(by.model('ctrl.project.targetPath')).sendKeys(testProject.targetPath);
        element(by.model('ctrl.project.title')).sendKeys(testProject.title);
        element(by.model('ctrl.project.packagePrefix')).sendKeys(testProject.packagePrefix);
        element(by.id('save')).click();

        browser.get('http://localhost:8080/ljprojectbuilder/#/viewcomponents/project-all/');

        expect(projects.count()).toEqual(1);
        expect(projects.first().element(by.tagName('h3')).getText()).toEqual(testProject.title);
    });

});
