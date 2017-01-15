<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mushroom hunter portal</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/resources/css/main.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-resource.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-route.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment-with-locales.js"></script>
    <script src="//cdn.rawgit.com/indrimuska/angular-moment-picker/master/dist/angular-moment-picker.min.js"></script>
    <link href="//cdn.rawgit.com/indrimuska/angular-moment-picker/master/dist/angular-moment-picker.min.css" rel="stylesheet">
    <script> var contextPath = "${pageContext.request.contextPath}"</script>
    <script src="${pageContext.request.contextPath}/app.js"></script>
    <script src="${pageContext.request.contextPath}/hunter_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/forest_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/your_profile_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/login_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/mushroom_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/register_controllers.js"></script>

    <script src="${pageContext.request.contextPath}/utils_service.js"></script>
    <script src="${pageContext.request.contextPath}/rest/utils/rest_utils_service.js"></script>
    <script src="${pageContext.request.contextPath}/rest/utils/rest_error_handlers_service.js"></script>

    <script src="${pageContext.request.contextPath}/rest/visit_rest_service.js"></script>
    <script src="${pageContext.request.contextPath}/rest/hunter_rest_service.js"></script>
    <script src="${pageContext.request.contextPath}/rest/forest_rest_service.js"></script>
    <script src="${pageContext.request.contextPath}/rest/mushroom_rest_service.js"></script>
    <script src="${pageContext.request.contextPath}/rest/mushroom_count_rest_service.js"></script>
    <script src="${pageContext.request.contextPath}/rest/combined_rest_service.js"></script>

    <script src="${pageContext.request.contextPath}/controllers/visit/update_visit_controller.js"></script>
    <script src="${pageContext.request.contextPath}/controllers/visit/create_visit_controller.js"></script>
    <script src="${pageContext.request.contextPath}/controllers/visit/visits_controller.js"></script>
    <script src="${pageContext.request.contextPath}/controllers/mushroom_count/update_mc_controller.js"></script>
    <script src="${pageContext.request.contextPath}/controllers/mushroom_count/create_mc_controller.js"></script>
    <style>
        .navbar-nav.navbar-right .btn {
            position: relative;
            z-index: 2;
            padding: 4px 20px;
            margin: 10px auto;
            transition: transform 0.3s;
        }
    </style>
</head>
<body>
<div ng-app="mushroomHunterApp">
    <nav class="navbar navbar-default navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">Mushroom hunter portal</a>
            </div>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li ng-hide="user.role == 'ANONYMOUS'"><a href="#/yourprofile">Your Profile</a></li>
                <li ng-hide="user.role == 'ANONYMOUS'">
                    <a href="#/yourvisits">Your Visits</a>
                </li>
                <li><a href="#/visits">Visits</a></li>
                <li><a href="#/mushrooms">Mushrooms</a></li>
                <li><a href="#/forests">Forests</a></li>
                <li ng-hide="user.role == 'ANONYMOUS'"><a href="#/hunters">Hunters</a></li>
                <li>
                    <a class="btn btn-default" href="#/login" ng-show="!user.isLogged()" role="button">Login</a>
                    <a class="btn btn-default" href="#/visits" ng-show="user.isLogged()" ng-click="user.logout()" role="button">Logout</a>
                </li>
                <li>
                    <a class="btn btn-default" href="#/signup" ng-show="!user.isLogged()" role="button">Sign Up</a>
                </li>
            </ul>
        </div>

        <div class="container">
            <div ng-show="warningAlert" class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideWarningAlert()"><span
                        aria-hidden="true">&times;</span></button>
                <strong>Warning!</strong> <span>{{warningAlert}}</span>
            </div>
            <div ng-show="errorAlert" class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideErrorAlert()"><span
                        aria-hidden="true">&times;</span></button>
                <strong>Error!</strong> <span>{{errorAlert}}</span>
            </div>
            <div ng-show="successAlert" class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideSuccessAlert()"><span
                        aria-hidden="true">&times;</span></button>
                <strong>Success !</strong> <span>{{successAlert}}</span>
            </div>

            <!-- the place where HTML templates are replaced by AngularJS routing -->
            <div ng-view></div>
        </div>
    </nav>
</div>
</body>
</html>
