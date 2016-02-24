angular.module('dashboardApp').controller(
    'LoginSignupCntrl',
    [
        '$scope',
        '$http',
        'UserService',
        function($scope, $http, userService) {

          $scope.loginSignUpErrorMessages = [];

          $scope.onLogin = function() {
            var promise = userService
                .login(this.loginEmail, this.loginPassword);
            promise.then(onLoginComplete);
          };

          var onLoginComplete = function(response) {
            if (response.data == 'ok') {
              window.location.replace('dashboard');
            } else {
              $scope.loginSignUpErrorMessages = [];
              $scope.loginSignUpErrorMessages
                  .push("Invalid Username and password provided.");
            }
          }
        } ]);
