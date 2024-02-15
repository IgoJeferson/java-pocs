# Messaging with Redis

## Standing up a Redis server

```
docker run -d --name some-redis -p 6379:6379 redis
```

It might be required to bind your redis instance to 0.0.0.0 to be able to access local host

Access the instance
```
redis-cli
set bind 0.0.0.0
save
``` 