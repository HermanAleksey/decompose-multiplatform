# Server application

## Description of API

### Authentication

1. http://localhost:8080/login

> Authentication request

Requires body with username and password:
```json
{
  "username": "user",
  "password": "pass"
}
```
Returns token for user if user exists:
```json
{
    "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwL2hlbGxvIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODA4MC8iLCJ1c2VybmFtZSI6InVzZXIiLCJleHAiOjE3MjU4MTc1MjZ9.TVmOgFVqusJWI8GBIuTHxEvNbCKD8jl3EAGQnYN5LCe1h6eMBSNAVtvavnsN68kYwHiF7Lxop1uKCTF8043_dg"
}
```
Otherwise returns 401 (Unauthorized) exception

2. http://localhost:8080/hello

> Test auth user request

Requires bearer token as a Header
```json
Authorization - Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwL2hlbGxvIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODA4MC8iLCJ1c2VybmFtZSI6ImpldGJyYWlucyIsImV4cCI6MTcyMTQxOTA3MX0.tRfjUHqPfukpEIcdKwHdzAHmbiEiuFDkIQQ_tdB0pbugyWQo5eABnhOoO42FiXWSU7iNoEc49eQgS6XxdguDhA
```

Returns "Hello, $username" text if request succeed and `Token is not valid or has expired` otherwise


### File system

1. http://localhost:8080/directory?path=inner_directory/dir1

> Get all files and directories in selected directory

- `path` param is path to the desired directory

Example of response:
```json
[
    {
        "uri": "inner_directory",
        "name": "inner_directory",
        "isFolder": true
    },
    {
        "uri": "photo.png",
        "name": "photo.png",
        "isFolder": false
    },
    {
        "uri": "previews",
        "name": "previews",
        "isFolder": true
    }
]
```

2. http://localhost:8080/image?path=photo.png&preview=true

> Get image from storage

- `path` param is path to the desired image, including file name (i.e. path=images/photo.png)
- `preview` param allows to load preview of photo file, lower quality and image size

Response is an image file
If path is not correct (not to a `.png` file) - respond 404 NotFound

