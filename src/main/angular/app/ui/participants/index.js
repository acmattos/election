import angular    from 'angular';
import controller from './participants.controller';
import route      from './participants.route';

/**
 * 'election.ui.participants' module definition.
 * Defines a controller, a router and a HTML template.
 */
export default angular.module('election.ui.participants', [])
.controller('participantsController', controller)
.config(route)
.name;