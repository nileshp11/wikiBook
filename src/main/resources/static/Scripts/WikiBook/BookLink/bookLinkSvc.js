'use strict';
angular.module('wikiBookApp')
    .factory('bookLinkSvc', ['$http', function ($http) {
        return {
            getBookLinkByTitle: function (linkTitle) {
                return $http.get('api/links/' + linkTitle);
            },
            postBookLink: function (bookLinkItem) {
                return $http.post('api/links/', bookLinkItem);
            },
            putBookLink: function (bookLinkItem) {
                return $http.put('api/links/', bookLinkItem);
            },
            getAllBookLinks: function () {
                return $http.get('api/links');
            },
            deletBookLinkByTitle: function (linkTitle) {
                return $http({
                    method: 'DELETE',
                    url: 'api/links/' + linkTitle
                });
            }
        }
    }]);