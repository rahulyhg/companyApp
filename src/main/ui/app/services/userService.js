angular.module('dashboardApp')
    .service('UserService', ['$http','$q', function($http, $q) {
        return {
            logout: function () {
                $http({
                    method: 'POST',
                    url: 'logout'
                })
                .then(function (response) {
                    if (response.status == 200) {
                      window.location.reload();
                    } else {
                      console.log("Logout failed!");
                    }
                });
            },
            login: function (username, password) {
               return  $http({
                    method: 'POST',
                    url: 'authenticate',
                    data: 'username=' + username + '&password=' + password,
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "X-Login-Ajax-call": 'true'
                    }
                });
            },
            signup: function(email, password, name) {
              return $http.post('user', {'username': name, 'password': password,
                'loginId': email, 'isActive': true});
            },
            all: function() {
              return $http.get('user/all');
            },
            get: function(id) {
              return $http.get('user/' + id);
            },
            update: function(id, email, password, name) {
              return $http.put('user', {'username': name, 'password': password,
                'loginId': email, 'isActive': true, 'id': id});
            },
            remove: function(id) {
              return $http.delete('user/' + id);
            },
            enableUser: function(id) {
              return $http.put('user/enable/' + id);
            }
        };
    }]);
