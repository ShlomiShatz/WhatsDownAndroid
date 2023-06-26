import mongoose from 'mongoose';
const Schema = mongoose.Schema;

const User = new Schema({
    username: {
        type: String,
        nullable: true
    },
    password: {
        type: String,
        nullable: true
    },
    displayName: {
        type: String,
        nullable: true
    },
    profilePic: {
        type: String,
        nullable: true
    }, 
    firebaseToken: {
        type: String,
        nullable: true
    }
});

export default mongoose.model('User', User);