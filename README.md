# AportMe-backend
Backend service for AportMe application.

## OAuth configuration
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
