'use strict';
angular.module('wikiBookApp', ['ngRoute'])
    .config(['$routeProvider',  function ($routeProvider) {
        $routeProvider.when('/Home', {
            controller: 'homeCtrl',
            templateUrl: 'Views/WikiBook/BookLink/Home.html',
        }).when('/bookLink', {
                controller: 'bookLinkCtrl',
                templateUrl: 'Views/WikiBook/BookLink/BookLinkList.html',
            }).otherwise({redirectTo: '/Home'});
    }]);