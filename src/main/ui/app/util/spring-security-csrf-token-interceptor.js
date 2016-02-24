
/*
 * spring-security-csrf-token-interceptor
 *
 * Sets up an interceptor for all HTTP requests that adds the CSRF Token Header that Spring Security requires.
 */
(function () {
    angular.module('spring-security-csrf-token-interceptor', [])
        .config(function ($httpProvider) {
            var getTokenData = function () {
                var defaultCsrfTokenHeader = 'X-CSRF-TOKEN';
                var csrfTokenHeaderName = 'X-CSRF-HEADER';
                var xhr = new XMLHttpRequest();
                xhr.open('head', '/company-dashboard', false);
                xhr.send();
                var csrfTokenHeader = xhr.getResponseHeader(csrfTokenHeaderName);
                csrfTokenHeader = csrfTokenHeader ? csrfTokenHeader : defaultCsrfTokenHeader;
                return { headerName: csrfTokenHeader, token: xhr.getResponseHeader(csrfTokenHeader) };
            };
            var csrfTokenData = getTokenData();
            var numRetries = 0;
            var MAX_RETRIES = 5;
            $httpProvider.interceptors.push(function ($q, $injector) {
                return {
                    request: function (config) {
                        config.headers[csrfTokenData.headerName] = csrfTokenData.token;
                        return config || $q.when(config);
                    },
                    responseError: function (response) {
                        if (response.status == 403 && numRetries < MAX_RETRIES) {
                            csrfTokenData = getTokenData();
                            var $http = $injector.get('$http');
                            ++numRetries;
                            return $http(response.config);
                        }
                        return response;
                    },
                    response: function(response) {
                        // reset number of retries on a successful response
                        numRetries = 0;
                        return response;
                    }
                };
            });
        });
})();
