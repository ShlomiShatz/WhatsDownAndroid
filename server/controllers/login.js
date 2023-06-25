import * as userService from '../services/user.js';
import * as loginService from '../services/login.js';

const processLogin = async (req, res) => {
    if(!req.body.username){
        res.status(400).send({"errors": {"Username":['The Username field is required.']}})
    }
    if(!req.body.password){
        res.status(400).send({"errors": {"userpass":["The userpass field is required."]}})
    }
    
    const user = await userService.getPasswordtUserByUserName(req.body.username);
    if (!user) {
        res.status(404).send('Incorrect username and/or password')
    }
    else if (req.body.password === user.password) {
        const token = loginService.createToken(req.body.username);
        res.status(200).json(token);
    }
    else{
        res.status(404).send('Incorrect username and/or password')
    }
    
}
const isLoggedIn = (req, res, next) => {
    if (req.headers.authorization.split(" ").length === 2) {
        const token = req.headers.authorization.split(" ")[1].replace(/"/g, '');
        try {
            loginService.verifyToken(token);
            return next()
        } catch (err) {
            return res.status(401).send("Invalid Token");
        }
    }
    else
        return res.status(403).send('Token required');
}

const addFirebase = async (req, res) => {
    if(req.body.firebaseToken || req.body.firebaseToken === "") {
        userService.addFirebaseToken(req.params.username, req.body.firebaseToken);
        res.status(200).send();
    }
}

export { processLogin, isLoggedIn, addFirebase };