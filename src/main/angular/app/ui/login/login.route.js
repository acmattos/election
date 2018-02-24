import templateHtml from './login.template.html';

export default function routes(stateProvider) {
    stateProvider
        .state('/', {
            url: '/',
            template: templateHtml,
            controller: 'loginController',
            controllerAs: 'vm',
            resolve: {
                isLogout: function () {
                    return false;
                }
            }
        })
        .state('logout', {
            url: '/logout',
            template: templateHtml,
            controller: 'loginController',
            controllerAs: 'vm',
            resolve: {
                isLogout: function () {
                    return true;
                }
            }
        })
    ;
}
routes.$inject = ['$stateProvider'];