'use strict';

angular.module('logsReaderApp')
  .controller('HeaderCtrl', [ '$scope', '$location', function($scope, $location) { 
    		
	$scope.isActive = function (viewLocation) { 
        return viewLocation === $location.path();
    };

  }]);
