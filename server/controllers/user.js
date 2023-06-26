import * as userService from '../services/user.js';
import * as userOmitPasswordService from '../services/userOmitPassword.js'
import * as loginService from '../services/login.js';


const createUser = async (req, res) => {
    const { username, password, displayName, profilePic } = req.body;
    if (!username || username === "") {
        return res.status(400).json({"Username" : ['the Username field is required.']});
    }
    if (!password || password === "") {
        return res.status(400).json({"Password": ['the Password field is required.']});
    }
    if (!displayName || displayName === "") {
        return res.status(400).json({"DisplayName": ['the DisplayName field is required.']});
    }
    if (!profilePic || profilePic === "") {
        return res.status(400).json({"u": ['the u field is required.']});
    }
    const user = await userService.createUser(username, password, displayName, profilePic);
    if(!user){
        return res.status(409).json({error: ['Conflict']});
    }
    await userOmitPasswordService.createUser(username, displayName, profilePic);
    res.json(await userService.getUserPassByUsername(username));
};

const getUser = async (req, res) => {
    const username = loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);
    const user = await userService.getUserPassByUsername(req.params.username);
    if(user == null || user.username !== username){
        return res.status(401).json({error: ['user not found']});
    }
    res.json(user);
};

const getUserPassword = async (req, res) => {
    const user = await userService.getPasswordtUserByUserName(req.params.username);
    if(!user){
        return res.status(401).json({error: ['user not found']});
    }
    res.json(user);
};

export {createUser, getUser,  getUserPassword};