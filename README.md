## How to start the app locally
1) Clone the repository to a local machine. 
2) Create a .env file at repo root. You can copy .env.example to .env
3) Make sure to prived a valid secret. You can generate one with `openssl rand -base64 32`
4) Navigate to the repo and use `docker compose up -d`

The images are built in temporary containers and then started

## Example API calls

user service runs on port 8080

todo-service runs on port 8081

### Register
```
curl -X POST http://localhost:8080/register \
-H "Content-Type: application/json" \
-d '{"username":"alice","password":"secret"}'
```

### Login (returns {"token": "..."})
```
curl -X POST http://localhost:8080/login \
-H "Content-Type: application/json" \
-d '{"username":"alice","password":"secret"}'
```
### Validate token
```
curl -X POST http://localhost:8080/token \
-H "Content-Type: application/json" \
-d '{"token":"your-jwt-token"}'
```

### Add todo
```
curl -X POST http://localhost:8081/todos \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{"text":"Important Task"}'
```

### List todos
```
curl http://localhost:8081/todos 
-H "Authorization: Bearer <TOKEN>"
```

