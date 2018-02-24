import templateHtml from './results.template.html';

/**
 * Routes definition for 'election.ui.results' module.
 * @param {*} stateProvider State Provider.
 */
export default function routes(stateProvider) {
   stateProvider
   .state('results', {
      url: '/results',
      template: templateHtml,
      controller: 'resultsController',
      controllerAs: 'vm',
      authorize: true
   });
}
routes.$inject = ['$stateProvider'];