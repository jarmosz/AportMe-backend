# AportMe-backend
Backend service for AportMe application.

## OAuth configuration
Tokens are sign with RSA keys, so we should generate it first.

Go to ***/AportMe-backend*** and run command in your bash terminal (It will generate private-key and save it to resource folder):

```bash
openssl genrsa -out src/main/resources/private-key.pem 2048
```

After that, let's generate public key:

```bash
openssl rsa -in src/main/resources/private-key.pem -pubout -out src/main/resources/public-key.pem
```

Congratulations! You have generated RSA keys, so now you can run the application.

## OAuth basic authorization flow


Register new account on:

```bash
POST
https://localhost:8080/register
```

With valid credentials (valid email and password with length >= 8)

```json
{
	"email": "asd@asd.pl",
	"password": "asd12312"
}
```

If user with this email doesn't exist in database, you should get 200 HTTP code which means, that user has been registered properly.

After that, you should retrieve access token and refresh token:

```bash
POST
https://localhost:8080/oauth/token
```

```bash
Credentials:
"username": "asd@asd.pl"
"password": "asd12312"
"grant_type": "password"
Request headers:
"content-type": "application/x-www-form-urlencoded" ,
"authorization": "Basic base64(clientId:clientSecret)"
```
Remember to add appriopriate headers, set grant_type = password, and encode clientId:clientSecret with Base64

To retrieve access to concrete endpoint for example ***/api/pets***, make a request like this:

```bash
GET
http://localhost:8080/api/pets?access_token={access_token_here}
```

If you have permission to that endpoint, server should return you your data in JSON format.
