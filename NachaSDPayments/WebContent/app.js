/* This is the heart of our Angular application: the main module. This module doesn't do too
 * much at the moment, but it is handling routing information for our app. $routeProvider
 * allows us to attach HTML files to specific URL routes and Angular controllers. Once we add
 * more than one HTML file to our app, we'll want to add a new controller just for that
 * page.
 */
var app = angular.module('NachaSDPayments', ['ngRoute']);
app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
	$routeProvider
		.when("/", {templateUrl: "login.html", controller: "LoginCtrl"})
		.when("/home", {templateUrl: "transactions.html", controller: "HomeCtrl"})
		.when("/home/new_transaction", {templateUrl: "newtrans.html", controller: "TransFormCtrl"})
		.otherwise({ redirectTo: "/"});
}]);