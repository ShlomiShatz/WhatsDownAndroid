// const User = require('../models/user');
import UserOmitPassword from '../models/userOmitPassword.js'

const createUser = async (username, displayName, profilePic) => {
    if(await getUserByUsername(username) !== null){
        return null;
    }else{
        const user = new UserOmitPassword({
            username: username,
            displayName: displayName,
            profilePic: profilePic
        });
        return await user.save();
    }
};


const getUserByUsername = async (username) => {
    return await UserOmitPassword.findOne({username: username});
};

const getUserIdByName = async (username) => {
    return await getUserByUsername(username);
}


export {createUser,getUserByUsername, getUserIdByName};