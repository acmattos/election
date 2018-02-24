import angular         from 'angular';
import loginTemplate   from './login.template.html';
import loginController from './login.controller';
import loginRoute      from './login.route';

/**
 * 'election.ui.login' module definition.
 * Defines a controller, a router and a HTML template.
 */
export default angular.module('election.ui.login', [])
.controller('loginController', loginController)
.config(loginRoute)
.name;