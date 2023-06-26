import admin from 'firebase-admin'
import serviceAccount from '../serviceAccountKey.json' assert {type: 'json'};

const init = () => {
    admin.initializeApp({
      credential: admin.credential.cert(serviceAccount)
    });
}

const sendMessageToFirebase = (message) => {
    admin.messaging().send(message)
        .then((response) => {
            
        }).catch((error) => {
            console.log("Error sending message: ", error);
        });
}

export {
    init,
    sendMessageToFirebase
}