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
    <!--<script src="{pageContext.request.contextPath}/angular_app.js"></script> -->
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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/pa165">Mushroom hunter portal</a>
        </div>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#/yourvisits">Your Visits</a></li>
            <li><a href="#/yourmushroomcounts">Your Catches</a></li>
            <li><a href="#/catches">Catches</a></li>
            <li><a href="#/mushroompreview">Mushrooms</a></li>
            <li><a href="#/forestpreview">Forests</a></li>
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
                <a class="btn btn-default"  data-toggle="collapse" href="#/login" aria-expanded="false" aria-controls="navbar">Login</a>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
