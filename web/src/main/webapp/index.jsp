<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mushroom hunter portal</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/resources/css/main.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular-resource.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular-route.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/app.js"></script>
    <script src="${pageContext.request.contextPath}/admin_hunter_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/admin_forest_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/forest_controllers.js"></script>
    <script src="${pageContext.request.contextPath}/your_profile_controllers.js"></script>
    <style>
        .navbar-nav.navbar-right .btn { position: relative; z-index: 2;padding: 4px 20px; margin: 10px auto; transition: transform 0.3s; }
    </style>
</head>
<body>
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
            <li><a href="#/yourprofile">Your Profile</a></li>
            <li><a href="#/yourvisits">Your Visits</a></li>
            <li><a href="#/yourcatches">Your Catches</a></li>
            <li><a href="#/catches">Catches</a></li>
            <li><a href="#/visits">Visits</a></li>
            <li><a href="#/mushrooms">Mushrooms</a></li>
            <li><a href="#/forests">Forests</a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin<b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#/admin/hunters">Hunters</a></li>
                    <li><a href="#/admin/visits">Visits</a></li>
                    <li><a href="#/admin/forests">Forests</a></li>
                    <li><a href="#/admin/mushroomcounts">Mushroom Counts</a></li>
                    <li><a href="#/admin/mushrooms">Mushrooms</a></li>
                </ul>
            </li>
            <li>
                <a class="btn btn-default" href="#/login" role="button">Login</a>
            </li>
        </ul>
    </div>

    <div class="container">

        <div ng-app="mushroomHunterApp">

            <div ng-show="warningAlert" class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideWarningAlert()"> <span aria-hidden="true">&times;</span></button>
                <strong>Warning!</strong> <span>{{warningAlert}}</span>
            </div>
            <div ng-show="errorAlert" class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideErrorAlert()"> <span aria-hidden="true">&times;</span></button>
                <strong>Error!</strong> <span>{{errorAlert}}</span>
            </div>
            <div ng-show="successAlert" class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideSuccessAlert()"> <span aria-hidden="true">&times;</span></button>
                <strong>Success !</strong> <span>{{successAlert}}</span>
            </div>

            <!-- the place where HTML templates are replaced by AngularJS routing -->
            <div ng-view></div>
        </div>

    </div>
</nav>
</body>
</html>
