# GreenStitch Authentication

Authentication APIs for GreenStitch.

## Installation

1. First clone the GitHub Repository by pasting the command given below in the terminal.
```bash
git clone https://github.com/Ratnesh2003/GreenStitch-Auth.git
```
2. In application.properties you can change your email and password by modifying "spring.mail.username" and "spring.mail.password".
4. In application.properties file, you can modify username and password for the h2-console. By default username="sa" and password="".
3. If you have opened the project on IntelliJ, click on the maven button to install the dependencies.
4. This project uses Java 17 and Spring Boot 3.0.6 . Make sure to have JDK 17 for best working.
5. Run the "GreenStitchAuthenticationApplication" file to start the application.



## Usage

Here is the Postman Collection link for the APIs: [GreenStitch APIs](https://api.postman.com/collections/20949772-65b2813b-f160-4595-aa15-e5a6267b4f1d?access_key=PMAT-01H08BBHGRPFFVYCHWEBF02H93). 

You can import the Collection by opening the Postman --> Hamburger menu --> File --> Import
Then paste the link given above to import the collection.

## APIs
1. Register (POST)
```
localhost:8080/api/auth/signup
```
```json
{
    "firstName": "Ratnesh",
    "lastName": "Mishra",
    "email": "ratneshmishrarulz@gmail.com",
    "password": "Abcd@1234",
    "role": "ROLE_USER"
}
```
2. Verify Email (POST)
```
localhost:8080/api/auth/verify
```
```json
{
    "email": "ratneshmishrarulz@gmail.com",
    "otp": 478568
}
```
3. Login (POST)
```
localhost:8080/api/auth/login
```
```json
{
    "email": "ratneshmishrarulz@gmail.com",
    "password": "Abcd@1234"
}
```
4. Refresh Token (POST)
```
localhost:8080/api/auth/refresh-token
```
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXRuZXNobWlzaHJhcnVsekBnbWFpbC5jb20iLCJpYXQiOjE2ODM4OTcxNDYsImV4cCI6MTY4NjQ4OTE0Nn0.vWX1Zojb7Y6DyqpOOJFZrtny3a1XUIvvV1-P6o3Lj5nmzJdBuIOLRp1CgQ7m_HzOHdcDnfpYNKTYpwak_J8fyQ"
}
```
5. Forgot Password (GET)
```
localhost:8080/api/auth/forgot-password?email=ratneshmishrarulz@gmail.com
```
6. Verify Reset OTP (POST)
```
localhost:8080/api/auth/verify-reset-otp
```
```json
{
    "email": "ratneshmishrarulz@gmail.com",
    "otp": 377602
}
```
7. Reset Password (PUT)
```
localhost:8080/api/auth/reset-password
```
```json
{
    "email": "ratneshmishrarulz@gmail.com",
    "password": "Abc@123",
    "otp": 377602
}
```
8. Home Route Test (GET)
```
localhost:8080/home
```
Authorization Header:
```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXRuZXNobWlzaHJhcnVsekBnbWFpbC5jb20iLCJpYXQiOjE2ODM4OTcxNDYsImV4cCI6MTY4NjQ4OTE0Nn0.vWX1Zojb7Y6DyqpOOJFZrtny3a1XUIvvV1-P6o3Lj5nmzJdBuIOLRp1CgQ7m_HzOHdcDnfpYNKTYpwak_J8fyQ
```




## Flow

1. First you must register your self with the Register API, it will send an email on the specified Email ID. Your account details will be in cache for 5 minutes. If you verify your account within 5 minutes, you will be registered successfully. Otherwise, you will have to register again.
2. For verifying email ID, use the Verify Email API.
3. You can now login using the Login API and get your access and refresh tokens in response.
4. Your access token will expire after 10 minutes. to refresh the access token, you must use the Refresh Token API.
5. To check the Auth tokens, you can call the Home Route API, you will have to add tokens in header of the request or else you will get an error of 401 Unauthorized.
6. To change the password, in case you forget it, you can call the Forgot Password API, which will send you an email with an OTP.
7. After receiving the OTP, you can call the Verify Reset OTP API to check if the OTP was valid or invalid.
8. If the OTP is valid, you may call the Reset Password API along with the OTP and new password.

## Note
1. Please note that I have not implemented many validations in the APIs on purpose for the ease of testing.
2. In case you are getting an error regarding the exceeded limit of the mail ID I provided, for sending mails, you can use this one:
```json
"username": "drreamboy9@gmail.com",
"password": "znpfhusycrboojjm"
```
3. Possible values for role are "ROLE_ADMIN" and "ROLE_USER".
