import templateHtml from './participants.template.html';

/**
 * Routes definition for 'election.ui.participants' module.
 * @param {*} stateProvider State Provider.
 */
export default function routes(stateProvider) {
   stateProvider
   .state('participants', {
      url: '/participants',
      template: templateHtml,
      controller: 'participantsController',
      controllerAs: 'vm',
      authorize: true,
   });
}
routes.$inject = ['$stateProvider'];