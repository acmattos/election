import angular    from 'angular';
import controller from './vote.controller';
import route      from './vote.route';

/**
 * 'election.ui.vote' module definition.
 * Defines a controller, a router and a HTML template.
 */
export default angular.module('election.ui.vote',[])
.controller('voteController', controller)
.config(route)
.name; 