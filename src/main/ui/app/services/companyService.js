angular.module('dashboardApp')
    .service('CompanyService', ['$http',function($http) {
        return {
          all: function() {
            return $http.get('company/all');
          },
          create: function(name, address, city, country, email, phone) {
              return $http.post('company', {'name': name, 'address': address,
                'city': city, 'country': country, 'email': email,
                'phone': phone});
          },
          remove: function(id) {
              return $http.delete('company/' + id);
          },
          get:function(id) {
             return $http.get('company/' + id);
          },
          update:function(id, name, address, city, country, email, phone) {
            return $http.put('company', {'id': id, 'name': name,
              'address': address, 'city': city, 'country': country,
              'email': email, 'phone': phone});
          },
          removeOwner: function(ownerId, companyId) {
              return $http.delete('company/' + companyId + '/' + ownerId);
          },
          addOwner: function(ownerId, companyId) {
              return $http.put('company/' + companyId + '/' + ownerId);
          }
       }
    }]);