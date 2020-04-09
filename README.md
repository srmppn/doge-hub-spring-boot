# doge-hub-spring-boot
This is my personal project which I'm working in my free time for fun and learning purpose. I used Spring booot framework for entire back-end and I have another fron-end in seperate repository which using reactJs. These two front-end and back-end communicate via API.
# Feature
Currently update, This project now have sign up, sign in, account information service, file upload service, video service.
# Security
I used basic authentication to authenticate and authorize by using jwt token. Here's how it works, first client needs to send credential within authorization header like { Authorization : 'Basic ' + credential } through API. I encoded the credential with base64 encoder ('username:password') and then if the authentication is successfully it will return a jwt token within response headers like { Authorization : 'Bearer ' + jwtToken }. I store the password by hashing it once using Bcrypt and store it in database via ORM. I'll add salt generator for more security in the next feature soon.
# Testing
For unit testing, I have tested a few service classes already, but I'll test all of it soon.
