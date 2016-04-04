'use strict';

/**
 * @ngdoc overview
 * @name logsReaderApp
 * @description
 * # logsReaderApp
 *
 * Main module of the application.
 */
angular
  .module('logsReaderApp', [
    'ngAnimate',
    'ngMaterial',
    'ngResource',
    'ui.router'
  ])
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        
      // route for the home page 
      .state('app', {
          url:'/',
          views: {
              'header': {
                  templateUrl : 'views/header.html',
                  controller  : 'HeaderCtrl'
              },
              'content': {
                  templateUrl : 'views/main.html',
                  controller  : 'MainCtrl'
              },
              'footer': {
                  templateUrl : 'views/footer.html',
              }
          }

      })
      // route for the logs listing
      .state('app.logs', {
          url:'logs',
          views: {
              'content@': {
                  templateUrl : 'views/logs.html',
                  controller  : 'LogsCtrl'                  
              }
          },
      })
      ;
    
    $urlRouterProvider.otherwise('/');
  }])
  .filter('unsafe', function($sce) { return $sce.trustAsHtml; })
  ;
