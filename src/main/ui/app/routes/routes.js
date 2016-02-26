angular.module('dashboardApp').config(function($routeProvider, $urlRouterProvider, $stateProvider) {
  $urlRouterProvider.otherwise('/app');
  $stateProvider
  .state( 'app', {
      url: '/app',
      views: {
        '': {
          templateUrl : 'resources/templates/company-dashboard.html'
        }
      }
  })
});

angular.module('dashboardApp').run(['$rootScope', '$location', '$state',
                                    function($rootScope, $location, $state) {

  $rootScope.$on('$locationChangeStart', function(event, newUrl, oldUrl) {
    console.log('Starting to leave %s to go to %s', oldUrl, newUrl);
    var indexOfDashboard = newUrl.indexOf('/dashboard');
    if (indexOfDashboard > -1 && newUrl[indexOfDashboard + 10] == undefined ) {  
      $state.go('app');
    }
  });
}]);