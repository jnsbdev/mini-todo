## How to start the app locally
1) Clone the repository to a local machine. 
2) Create a .env file at repo root. You can copy .env.example to .env 
3) Navigate to the repo and use `compose -f docker-compose.yml -p mini-todo up -d`
4) The images are built in temporary containers and then started

## Example API calls
```
# Register
curl -X POST http://localhost:8081/register \
    -H "Content-Type: application/json" \
    -d '{"username":"alice","password":"secret"}'

# Login (returns {"token": "..."})
curl -X POST http://localhost:8081/login \
    -H "Content-Type: application/json" \
    -d '{"username":"alice","password":"secret"}'
  
# Validate token
curl -X POST http://localhost:8081/token \
    -H "Content-Type: application/json" \
    -d '{"token":"your-jwt-token"}'

# Add todo
curl -X POST http://localhost:8082/todos \
    -H "Authorization: Bearer <TOKEN>" \
    -H "Content-Type: application/json" \
    -d '{"text":"Important Task"}'

# List todos
curl http://localhost:8082/todos 
    -H "Authorization: Bearer <TOKEN>"
```

