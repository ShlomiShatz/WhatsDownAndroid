import express from 'express';
// const loginController = require('../controllers/login.js')
import * as loginController from '../controllers/login.js';

var routerLogin = express.Router();

routerLogin.route('/').post(loginController.processLogin);
routerLogin.route('/:username').post(loginController.isLoggedIn, loginController.addFirebase);

export default routerLogin;