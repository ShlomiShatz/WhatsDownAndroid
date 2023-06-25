// const User = require('../models/user');
import User from '../models/user.js'

const createUser = async (username, password, displayName, profilePic) => {
    if(await getUserPassnameByUsername(username) !== null){
        return null;
    }else{
        const user = new User({
            username: username,
            password: password,
            displayName: displayName,
            profilePic: profilePic
        });
        return await user.save();
    }
};

const getPasswordtUserByUserName = async (username) => {
    const user = await User.findOne({username: username});
    if(user == null){
        return null;
    }
    const userInfo = {
        username: username,
        password: user.password
    }
    return userInfo;
};

const getUserPassnameByUsername = async (username) => {
    return await User.findOne({username: username});
};

const getUserPassByUsername = async (username) => {
    const user = await getUserPassnameByUsername(username);
    if(user == null){
        return null;
    }
    const userWithoutPassword = {
        username: username,
        displayName: user.displayName,
        profilePic: user.profilePic
    }
    return userWithoutPassword;
};

const getDisplaynameByUsername = async (username) => {
    const user = await getUserPassnameByUsername(username);
    if(user == null){
        return null;
    }
    return user.displayName;
};

const getUserIdByName = async (username) => {
    const user = await getUserPassnameByUsername(username);
    return user._id
}

const getFirebaseTokenByUsername = async (username) => {
    const user = await User.findOne({username: username});
    if(user == null){
        return null;
    }
    let fireToken = user.firebaseToken;
    return fireToken;
}

const addFirebaseToken = async (usern, token) => {
    const update = {
        $set: {
            firebaseToken: token
        }
    };
    const filter = { username: usern };
    const result = await User.updateOne(filter, update).exec();
}

export {
    createUser,
    getUserIdByName, 
    getPasswordtUserByUserName, 
    getUserPassnameByUsername, 
    getUserPassByUsername,
    addFirebaseToken,
    getFirebaseTokenByUsername,
    getDisplaynameByUsername
};