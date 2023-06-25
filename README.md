# What's Down Chatting App - Android Added
This is *What's Down*, a new, beautiful chatting app used to communicate between friends and family, for ages 9 to 99. In this part we added the option to use the app in your own Android smartphone!  
## Download and install
In order to run the app, you will need to download the project code into a folder on your computer. Make sure ReactJS and MongoDB is installed on your device. The folder of node_modules does not come as a part of the project - you need to install it yourself:
- Open a new folder  
- Download the files from the repository to the folder and extract it  
- Go to the extracted folder and open a terminal (cmd/bash) from that folder  
- Next, in the terminal go in the *server* folder (which contains the server) using `cd server` and run the command `npm i` to install the necessary modules  
## Running the server
After downloading and installing the necessary files and modules, open a terminal and go in the folder *server*. In order to run the server, run the command `npm start`.
## Running the app - Computer (smartphone below)
Now, in order to run the app, open a web browser and type the following URL: `http://localhost:5000/`. This will show you the first page of the app, the Login page.  
### Login Page
To login, enter your username and password. If both are correct, you will be redirected to the chatting page.  
If either of those are wrong, you will be notified. If you are not registered, click on the link on the bottom to register.
### Registration
To register, enter all the details:
- Username: Contains English letters and numbers only. No symbols, spaces, etc. A username must be unique, if a username is already taken you will be notified.  
- Password: Must be at least 8 characters long, and contain at least one of these each specifications: *lower case letter, higher case letter, number and at least one of these symbols: .!@#$*  
- Password Verification: Must be the same as the password entered previously.  
- Display name: The name you will have in the chat.
- Profile image: You are required to provide a valid profile picture, which is of one of these file types: *jpeg/jpg/gif/png*.
Note that if you will not fill all of the necessary details you will not be able to register.  
**After successful registeration, you will be redirected to the login page without any notice, and you will be able to login using the details you registered with.**  
### Chatting Page
The chatting area devides into two parts:
- The left is the side of contacts - you can see your contacts and the last message in that chat, enter their individual chat by clicking on it and add more contacts (by pressing the button and entering their **username**). You cannot add yourself as a contact and you cannot add the same person twice.
- The right side shows the chat itself, you can write messages to your contact and see what they write back. There is also a **delete** button that will delete the contact and all the messages between.  
On the right top side there is a Log Out button, it will redirect you to the login page.  
## Running the app - Smartphone
After running the server, install the app on your Android smartphone using Android Studio or a simiar software. After running it, the openning screen will show up. To start the app, click the **Start** button and you will be moved to the login page. Note that you will be asked for post-notification permissions. For the full app experience - please allow it.  
### Login Page
To login, enter your username and password and press the login button. If both are correct, you will be redirected to the chatting page.  
If either of those are wrong, you will be notified. If you are not registered, click on the link on the bottom to register.  
If you want to change the settings, click on the *gear* button on the top corner of the screen.  
### Setting page
In the setting page, you can change two things:
- The app theme (dark\light)
- The server port
After changing any of those, click on the *Save Settings* button to save your changes. A proper message will apear afterwards. In order to go back, click the *Back* button.
### Registration
To register, enter all the details:
- Username: Contains English letters and numbers only. No symbols, spaces, etc. A username must be unique, if a username is already taken you will be notified.  
- Password: Must be at least 8 characters long, and contain at least one of these each specifications: *lower case letter, higher case letter, number and at least one of these symbols: .!@#$*  
- Password Verification: Must be the same as the password entered previously.  
- Display name: The name you will have in the chat.
- Profile image: You are required to provide a valid profile picture, which is of one of these file types: *jpeg/jpg/gif/png*.
Note that if you will not fill all of the necessary details you will not be able to register.  
**After successful registeration, you will be notified properly and you will be redirected to the login page. You will be able to login using the details you registered with.**  
### Chatting Page
The chatting area devides into two parts - the contacts part and the chatting part.
#### Chatting page - contacts
In this part, which will apear upon successfull login, you will see your list of contacts, if you have any. Each contact will apear by the side of its profile picture and last message sent between you both. In order to go into the chat, press on the relevant contact. **Note: A long press will delete the contact!**   
Notice the 3 dots on the top corner. Clicking on it will reveal a small menu with a few options:
- Adding a contact - clicking this will open the Add contact page. In this, you can add the contact in the relevant text field. You can add a user using its username. Simply enter the username and click *Add contact*. If the user exists, a proper message will appear. Click *Back* to go back.
- Go to the setting page - same one that was presented above.
- Log out of the app - clicking this will move you to the login page. Make sure to log out properly every time you want to connect from another user!
#### Chatting page - chat
After clicking on the contact, you will be redirected to the chatting area. Here, you can see the conversation between you and the contact. In order to write a message - enter it in the textfield and click on the *send* button. If the contact writes you a message - you will see it in real-time. In order to go back to the contact list, click the *arrow* button on top.  
### Important Note
The server uses web sockets and Firebase to communicate, every message sent will appear in both of the chats, adding a person will make the chat appear on both sides, and **deletting a chat will erase the chat in BOTH sides**.
## Technologies Used
This project contains front-end, back-end and mobile implementations, using:
- Javascript, HTML, CSS
- Bootstrap
- React
- Socket.io
- MongoDB (Mongoose)
- NodeJS (Express)
- Java
- Firebase
- Android Rooms 
 
The server was designed by MVC architecturea.  
