'use strict';

angular.module('logsReaderApp')
    .constant('baseURL','http://0.0.0.0')
    .service('resourceService',[ '$resource', 'baseURL', function( $resource, baseURL) {

        this.getLogs = function(){
          return $resource(baseURL+':8080/logs/:logId', null);
        };

        this.getLogsWithAssets = function(){
          return $resource(baseURL+':8080/logs/logs-assets/true', null);
        };

        this.generateList = function(){
          return $resource(baseURL+':8080/generate', null);
        };

        this.flushList = function(){
          return $resource(baseURL+':8080/generate/clear', null);
        };
        
    }])
    .service('dialogService',[ '$mdDialog', function($mdDialog) {
      
        this.showDialog = function(message, state, title){
          $mdDialog.show(
            $mdDialog.alert()
              .parent(angular.element(document.body))
              .clickOutsideToClose(true)
              .title(title)
              .textContent(message)
              .ariaLabel('Alert Message')
              .ok('OK')
              .targetEvent(state)
          );
        };

    }])
;
