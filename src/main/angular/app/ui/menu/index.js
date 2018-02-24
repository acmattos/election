import angular      from 'angular';
import htmlTemplate from './menu.template.html';

/**
 * 'election.ui.menu' module definition.
 * Defines a HTML template.
 */
export default angular.module('election.ui.menu', [])
.component('menu', {template: htmlTemplate})
.name;