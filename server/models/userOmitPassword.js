import mongoose from 'mongoose';
const Schema = mongoose.Schema;

const UserOmitPassword = new Schema({
    username: {
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
    }
});

export default mongoose.model('UserOmitPassword', UserOmitPassword);