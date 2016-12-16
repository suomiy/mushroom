/**
 * Created by kisty on 16.12.16.
 */


portalControllers.controller('YourProfileCtrl', function ($rootScope,$scope, $http) {
    findHunterById($rootScope.user.id,$scope, $http);
});

portalControllers.controller('YourProfileUpdateCtrl',
    function ($scope, $routeParams, $http, $rootScope, $location) {

        findHunterById($rootScope.user.id,$scope, $http);
        $scope.showRanksAndRoles = false;

        $scope.updateHunter = function (hunter) {
            console.log("updating hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http({
                method: 'POST',
                url: '/rest/hunter/update',
                data: hunter
            }).then(function success(response) {
                    console.log('updated hunter ' + hunter.id + ' (' + hunter.email + ')  on server');
                    $rootScope.successAlert = 'Your profile was updated';
                    $location.path("/yourprofile");
                },
                function error(response) {
                    console.log("error when updating hunter");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot update hunter - ' + value;
                            });
                            break;
                    }
                }
            )
        }

});
