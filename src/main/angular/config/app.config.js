/**
 * Cofigures routing services for this application.
 */
function routing($urlRouterProvider, $locationProvider) {
   $locationProvider.html5Mode(false);
   $urlRouterProvider.otherwise('/');
}

/**
 * Cofigures routing services for this application.
 */
export default function config($urlRouterProvider, $locationProvider){
   routing($urlRouterProvider, $locationProvider);
}
config.$inject = [
   '$urlRouterProvider',
   '$locationProvider'
];