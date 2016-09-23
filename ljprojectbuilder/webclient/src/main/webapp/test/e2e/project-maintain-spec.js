describe('project maintain test', function() {

    it('should add a new project', function() {
        browser.get('http://localhost:8080/ljprojectbuilder/#/viewcomponents/project-maintain/create/');

        element(by.model('ctrl.project.description')).sendKeys('testProject');
        element(by.model('ctrl.project.templateLocation')).sendKeys('https://github.com/witchpou/lirejarp');
        element(by.model('ctrl.project.templateTitle')).sendKeys('lirejarp');
        element(by.model('ctrl.project.templatePackagePrefix')).sendKeys('de.starwit');
        element(by.model('ctrl.project.targetPath')).sendKeys('/testProject');
        element(by.model('ctrl.project.title')).sendKeys('testProject');
        element(by.model('ctrl.project.packagePrefix')).sendKeys('de.test');

        element(by.id('save')).click();

    });

});
