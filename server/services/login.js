import jwt from 'jsonwebtoken'

const key = "Some super secret key shhhhhhhhhhhhhhhhh!!!!!"

const getUsernameFromToken = (token) => {
    const data = jwt.verify(token.replace(/"/g, ''), key);
    return data.username;
}

const createToken = (username) => {
    const options = {
        expiresIn: '1h' // Token will expire in 1 hour
    };
    const data = { username: username }
    const token = jwt.sign(data, key, options)
    return token;
}

const verifyToken = (token) => {
    jwt.verify(token.replace(/"/g, ''), key);
}

export {getUsernameFromToken , createToken ,verifyToken};