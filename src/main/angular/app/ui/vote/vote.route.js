import templateHtml from './vote.template.html';

/**
 * Routes definition for 'election.ui.vote' module.
 * @param {*} stateProvider State Provider.
 */
export default function routes(stateProvider) {
   stateProvider
   .state('vote', {
      url: '/vote',
      template: templateHtml,
      controller: 'voteController',
      controllerAs: 'vm',
      authorize: true
   });
}
routes.$inject = ['$stateProvider'];