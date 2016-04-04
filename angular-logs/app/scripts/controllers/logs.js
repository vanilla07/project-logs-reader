'use strict';

angular.module('logsReaderApp')
    .controller('LogsCtrl', [ '$scope', 'resourceService', '$log', function($scope, resourceService, $log) { 
    		
		$scope.requestType = [ 'GET', 'PUT', 'DELETE', 'POST'];

        var hideAssets = function() {
            $scope.loading = true;
            $scope.loadMode = 'indeterminate';
            resourceService.getLogs().query(
                function(response) {
                    $scope.logs = response;
                    $scope.loading = false;
                    $scope.loadMode = '';
                },
                function(response) {
                    $scope.loading = false;
                    $scope.loadMode = '';
                    $log.log('Error: '+response.status + ' ' + response.statusText);
                }
            );
        };

        var showAssets = function() {
            $scope.loading = true;
            $scope.loadMode = 'indeterminate';
            resourceService.getLogsWithAssets().query(
                function(response) {
                    $scope.logs = response;
                    $scope.loading = false;
                    $scope.loadMode = '';
                },
                function(response) {
                    $scope.loading = false;
                    $scope.loadMode = '';
                    $log.log('Error: '+response.status + ' ' + response.statusText);
                }
            );
        };

        $scope.toggleAssets = function(asset) {
            if (asset) {
                showAssets();
            } 
            else {
                hideAssets();
            }
        };

		$scope.tab = 'all';

		$scope.select = function(setTab) {
            $scope.tab = setTab;
            $scope.selectedType = setTab;
            if (setTab === 'all') {
            	$scope.selectedType = '';
            }
            
        };

        $scope.getStatusFilter = function(status) {
            if (status) {
                return '!200 OK';
            } 
        };

        $scope.isSelected = function (checkTab) {
            return ($scope.tab === checkTab);
        };

        $scope.setSelectedLog = function (log) {
        	$scope.selectedLog = log;
        };

		$scope.sort = 'id';
		$scope.reverseSort = false;
		$scope.sortBy = function (criteria){
			if ($scope.sort !== criteria) {
				$scope.sort = criteria;
				$scope.reverseSort = false;
			}
			else {
				$scope.reverseSort = !$scope.reverseSort;
			}
		};

        hideAssets();

	}])
;