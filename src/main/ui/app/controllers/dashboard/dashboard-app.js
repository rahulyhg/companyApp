angular.module('dashboardApp').controller('DashboardCntrl',
     ['$scope', 'CompanyService', '$timeout', '$location', 'UserService',
        function($scope, companyService, $timeout, $location, userService) {
          $scope.vm = {
            errorMessages : [],
            infoMessages : []
          };

          $scope.companies = [];
          $scope.selectedCompanyId = null;
          $scope.selectedCompany = {};
          $scope.companyToUpdate = {};
          $scope.existingUsers = [];
          $scope.userToUpdate = {};
          $scope.disabledUserId = null;
          $scope.addOwner = null;

          var listAllCompanies = function() {
             var promise = companyService.all();
             promise.then(onListAllCompanies);
          };

          var onListAllCompanies = function(response) {
              $scope.companies = response.data;
          };

          $scope.getCompany = function() {
              var promise = companyService.get($scope.selectedCompanyId);
              promise.then(onGetCompany, onGetCompanyFail);
          };

          var onGetCompany = function(response) {
              $scope.selectedCompany = response.data;
          };

          var onGetCompanyFail = function(response) {
              console.log(response);
              alert('failed');
          };

          $scope.saveUpdateCompany = function() {
            var promise = null;
            if ($scope.companyToUpdate.id == null || $scope.companyToUpdate.id == undefined) {
              promise = companyService.create($scope.companyToUpdate.name,
                      $scope.companyToUpdate.address, $scope.companyToUpdate.city,
                      $scope.companyToUpdate.country, $scope.companyToUpdate.email,
                      $scope.companyToUpdate.phone);
            } else {
              promise = companyService.update($scope.companyToUpdate.id, $scope.companyToUpdate.name,
                      $scope.companyToUpdate.address, $scope.companyToUpdate.city,
                      $scope.companyToUpdate.country, $scope.companyToUpdate.email,
                      $scope.companyToUpdate.phone);
            }
            promise.then(onSaveUpdateCompany);
          };

          var onSaveUpdateCompany = function(response) {
            alert("Company saved.")
            listAllCompanies();
            $scope.companyToUpdate = {};
          };

          var listAllUsers = function() {
            var promise = userService.all();
            promise.then(function(response) {
              $scope.existingUsers = response.data;
            });
          };

          $scope.saveUpdateUser = function() {
            var promise = null;
            if ($scope.companyToUpdate.id == null || $scope.companyToUpdate.id == undefined) {
              promise = userService.signup($scope.userToUpdate.email,
                      $scope.userToUpdate.password, $scope.userToUpdate.name);
            } else {
              promise = userService.update($scope.userToUpdate.id, $scope.userToUpdate.email,
                      $scope.userToUpdate.password, $scope.userToUpdate.name);
            }
            promise.then(function(response) {
              alert("User saved.")
              listAllUsers();
              $scope.userToUpdate = {};
            });
          };

          $scope.deleteCompany = function(id) {
            var promise = companyService.remove(id);
            promise.then(function(response) {
              alert('Company removed');
              listAllCompanies();
            });
          };

          $scope.deleteUser = function(id) {
            var promise = userService.remove(id);
            promise.then(function(response) {
              alert('User removed');
              listAllUsers();
            });
          };

          $scope.removeOwner = function(ownerId, companyId) {
            var promise = companyService.removeOwner(ownerId, companyId);
            promise.then(function(response) {
                alert('Owner Removed.');
                listAllCompanies();
              });
          };

          $scope.enableUser = function() {
            var promise = userService.enableUser($scope.disabledUserId);
            promise.then(function(response) {
              alert('User Enabled.');
              listAllUsers();
              $scope.disabledUserId = null;
            });
          };

          $scope.addOwner = function() {
              var promise = companyService.addOwner($scope.addOwner.ownerId, $scope.addOwner.companyId);
              promise.then(function(response) {
                alert('Owner Added.');
                listAllCompanies();
                $scope.addOwner = {};
              });
          }
          listAllCompanies();
          listAllUsers();
        } ]);