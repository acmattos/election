import angular    from 'angular';
import controller from './results.controller';
import route      from './results.route';

/**
 * 'election.ui.results' module definition.
 * Defines a controller, a router and a HTML template.
 */
export default angular.module('election.ui.results',[])
.controller('resultsController', controller)
.config(route)
.name; 