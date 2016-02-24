angular.module('dashboardApp')
    .controller('BaseFormCtrl', ['$scope', '$http', function ($scope, $http) {

        var fieldWithFocus;
        $scope.vm = {
            submitted: false,
            errorMessages: []
        };

        $scope.focus = function (fieldName) {
            fieldWithFocus = fieldName;
        };

        $scope.blur = function (fieldName) {
            fieldWithFocus = undefined;
        };

        $scope.isMessagesVisible = function (fieldName) {
            return fieldWithFocus === fieldName || $scope.vm.submitted;
        };

        $scope.preparePostData = function () {
            var username = $scope.vm.username != undefined ? $scope.vm.username : '';
            var password = $scope.vm.password != undefined ? $scope.vm.password : '';
            return 'username=' + username + '&password=' + password;
        };

        $scope.login = function (username, password) {
            var postData = $scope.preparePostData();

            $http({
                method: 'POST',
                url: '/authenticate',
                data: postData,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-Login-Ajax-call": 'true'
                }
            })
            .then(function(response) {
                if (response.data == 'ok') {
                    window.location.replace('/resources/dashboard.html');
                }
                else {
                    $scope.vm.errorMessages = [];
                    $scope.vm.errorMessages.push({description: 'Access denied'});
                }
            });
        };
    }]);