'use strict';
angular.module('wikiBookApp')
    .controller('bookLinkCtrl', ['$scope', '$location', 'bookLinkSvc', function ($scope, $location, bookLinkSvc) {
        $scope.error = '';
        $scope.loadingMessage = '';
        $scope.bookLinkList = null;
        $scope.editingInProgress = false;
        $scope.newBookLinkItemLinkTitle = '';
        $scope.newBookLinkItemWikiLink = '';
        $scope.newBookLinkItemYoutubeLink = '';
        $scope.newBookLinkItemExternalLink = '';
        $scope.newBookLinkItemSubject = '';
        $scope.newBookLinkItemContext = '';

        $scope.editInProgressLink = {
            title: '',
            wikiLink: '',
            youtubeLink: '',
            externalLink: '',
            subject:  '',
            context: ''
        };

        $scope.saveBookLinkItem = function () {
            bookLinkSvc.postBookLink($scope.editInProgressLink).success(function (results) {
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
            })
        };


    }]);