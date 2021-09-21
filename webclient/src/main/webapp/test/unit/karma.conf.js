// Karma configuration
// Generated on Sat Sep 17 2016 23:43:17 GMT+0200 (CEST)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '.',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
        'res/lib/angular.js',
        'res/lib/angular-mocks.js',
        'res/lib/angular-route.js',
        'res/lib/angular-resource.js',
        'res/lib/angular-translate.min.js',
        'res/lib/angular-translate-loader-static-files.min.js',

        'shared/directives/global.directives.js',
        'shared/restfacade/rest.connector.factory.js',
        'shared/restfacade/appsetup.connector.factory.js',
        'shared/dialogs/dialog.service.js',

        'shared/restfacade/app.connector.factory.js',
        'viewcomponents/app/app.module.js',
        'viewcomponents/app/app.routes.js',
        'viewcomponents/app/appAllCtrl.js',
        'viewcomponents/app/appSingleCtrl.js',

        'shared/restfacade/domain.connector.factory.js',
        'viewcomponents/domain/domain.module.js',
        'viewcomponents/domain/domain.routes.js',
        'viewcomponents/domain/domainAllCtrl.js',
        'viewcomponents/domain/domainSingleCtrl.js',

        'shared/restfacade/generator.connector.factory.js',
        'viewcomponents/generator/generator.module.js',
        'viewcomponents/generator/generator.routes.js',
        'viewcomponents/generator/generatorCtrl.js',

        'app.module.js',
        'app.routes.js',

        'test/**/*.test.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['PhantomJS'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
