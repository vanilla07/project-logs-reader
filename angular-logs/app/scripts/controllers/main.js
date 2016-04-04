'use strict';

/**
 * @ngdoc function
 * @name logsReaderApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the logsReaderApp
 */
angular.module('logsReaderApp')
  .controller('MainCtrl', ['$scope', '$state', '$log', 'resourceService', 'dialogService', 
  	function ($scope, $state, $log, resourceService, dialogService) {
    
  	$scope.clearTable = function () {
  		resourceService.flushList().delete(
            function () {
                $scope.message = 'All logs have been removed'; 
			    dialogService.showDialog($scope.message, $state.go('app'), 'Success');
            },
            function (response) {
                $log.log('Error: '+response.status + ' ' + response.statusText);
                $scope.message = 'Error while removing the logs'; 
			    dialogService.showDialog($scope.message, $state.go('app'), 'Error');
            }
        );
  	};

  	$scope.generateTable = function () {
  		resourceService.generateList().save(
            function () {
                $scope.message = 'Logs successfully loaded'; 
			    dialogService.showDialog($scope.message, $state.go('app.logs'), 'Success');
            },
            function (response) {
                $log.log('Error: '+response.status + ' ' + response.statusText);
                $scope.message = 'Error while loading the logs'; 
			    dialogService.showDialog($scope.message, $state.go('app'), 'Error');
            }
        );
  	};
}]);
