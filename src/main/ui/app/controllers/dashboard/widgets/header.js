angular.module('dashboardApp').controller(
    'HeaderCntrl',
    [ '$scope', 'UserService', '$state',
        function($scope, userService, $state) {

          $scope.logout = function() {
            userService.logout();
          }
        }]);
