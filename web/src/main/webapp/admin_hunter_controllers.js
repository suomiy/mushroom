/**
 * Created by kisty on 14.12.16.
 */


function loadAdminHunters($http, $scope) {
    $http.get('/pa165/rest/hunter/findall').then(function (response) {
        $scope.hunters = response.data;
        console.log('All hunters loaded');
    });
}

function findHunterByEmail($email, $scope, $http) {
    var hunter;

    $http.get('/pa165/rest/hunter/findbyemail?email=' + $email).then(function (response) {
        hunter = response.data;
        $scope.hunters = [hunter];
        console.log('Hunter with email' + $email + 'loaded');
    });
}

portalControllers.controller('AdminHuntersCtrl',
    function ($scope, $rootScope, $http ) {

        loadAdminHunters($http, $scope);

        $scope.deleteHunter = function (hunter) {
            console.log("deleting hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http.delete('/pa165/rest/hunter/' + hunter.id ).then(
                function success(response) {
                    console.log('deleted hunter ' + hunter.id + ' (' + hunter.email + ')  on server');
                    $rootScope.successAlert = 'Deleted hunter "' + hunter.email + '"';
                    loadAdminHunters($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting hunter");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            $rootScope.errorAlert = 'Cannot delete hunter '+response.data.message;
                            break;
                    }
                }
            );
        };

        $scope.findAllHunters = function () {
            loadAdminHunters($http,$scope);
        };

        $scope.findHunterByEmail = function (email) {
            findHunterByEmail(email, $scope, $http);
        };

    });

function findHunterById($hunterId, $scope, $http) {
    $http.get('/pa165/rest/hunter/' + $hunterId).then(function (response) {
        $scope.hunter = response.data;
        console.log('loaded hunter ' + $scope.hunter.id + ' (' + $scope.hunter.email + ')');
    })

};

portalControllers.controller('AdminHunterUpdateCtrl',
    function ($scope , $routeParams, $http, $rootScope, $location) {
        
        var hunterId = $routeParams.hunterId;
        $scope.ranks = ranks;
        $scope.types = roles;

        findHunterById(hunterId, $scope, $http);

        $scope.updateHunter = function (hunter) {
            console.log("updating hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http({
                method: 'POST',
                url: '/pa165/rest/hunter/update',
                data: hunter
            }).then(function success(response) {
                    console.log('updated hunter ' + hunter.id + ' (' + hunter.email + ')  on server');
                    $rootScope.successAlert = 'Updated hunter "' + hunter.email + '"';
                    $location.path("/admin/hunters");
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