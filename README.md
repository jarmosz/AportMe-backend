# AportMe-backend
Backend service for AportMe application.



KeyKloack docker configuration with basic settings.
```bash
docker run --rm --name keycloak-server -p 8180:8080 \
    -e KEYCLOAK_USER=admin \
    -e KEYCLOAK_PASSWORD=admin \
    -e KEYCLOAK_IMPORT=/realm/realm-export.json \
    -v $(pwd)/realm/:/realm/ \
    jboss/keycloak
```

```bash
http://localhost:8180/auth/realms/AportMe/protocol/openid-connect/token

Content-Type: application/x-www-form-urlencoded

username: wojtas
password: admin
client_id: aportme-frontend
grant_type: password
```

Will give you token and refresh token.

We cant get into API resources by adding Bearer: token header when attempting to POST /api/foundation
