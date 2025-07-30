# KMP Home FTP Server

A Ktor-based server for file storage, authentication, and image previewing. Supports both production
and test environments with mirrored endpoints.
Test environment storage for files is reset every time Server launches.

---

## API Overview

All endpoints are available in two environments:

- **Prod:** `http://localhost:8080/route`
- **Test:** `http://localhost:8080/test/route`

> Replace `localhost:8080` with your actual host and port if different.

---

## Authentication

### Login

**POST** `/login` (Prod)  
**POST** `/test/login` (Test)

Authenticate a user and receive a JWT token and a refresh token.

**Request Body:**

```json
{
  "username": "user",
  "password": "pass"
}
```

**Response:**

- `200 OK`
  ```json
  {
    "token": "<jwt-token>",
    "refreshToken": "<refresh-token>"
  }
  ```
- `401 Unauthorized` if credentials are invalid

---

### Refresh Token

**POST** `/refresh` (Prod)  
**POST** `/test/refresh` (Test)

Obtain a new access token and refresh token using a valid refresh token.

**Request Body (form-urlencoded):**

- `refreshToken`: string (required)

**Response:**

- `200 OK`
  ```json
  {
    "token": "<new-jwt-token>",
    "refreshToken": "<new-refresh-token>"
  }
  ```
- `400 Bad Request` if missing refreshToken
- `401 Unauthorized` if refresh token is invalid or expired

---

### Logout

**POST** `/logout` (Prod)  
**POST** `/test/logout` (Test)

Invalidate a refresh token, logging the user out (the refresh token cannot be used again).

**Request Body (form-urlencoded):**

- `refreshToken`: string (required)

**Response:**

- `200 OK`
  ```json
  {
    "success": true
  }
  ```
- `400 Bad Request` if missing refreshToken
- `401 Unauthorized` if refresh token is invalid or already expired

---

## JWT-Protected Endpoints

All endpoints below require a valid JWT token in the `Authorization: Bearer <token>` header.

### GET `/directory`

Requests directory content with pagination support

**Query parameters:**

| Param      | Type   | Required          | Desciption                        |
|------------|--------|-------------------|-----------------------------------|
| `path`     | String | No                | Path to directory                 |
| `page`     | Int    | No (default `0`)  | Index of the page (starts with 0) |
| `pageSize` | Int    | No (default `30`) | Count of elements on the page     |

**Response (200 OK):**

```json
{
  "files": [
    {
      "name": "image1.jpg",
      "type": "IMAGE",
      "size": 123456
    },
    {
      "name": "subdir",
      "type": "DIRECTORY"
    }
    // ...
  ],
  "hasNextPage": false
}
```

---

---

### Get Image

**GET** `/image?path=...&preview=...` (Prod)
**GET** `/test/image?path=...&preview=...` (Test)

Retrieve an image file or its preview.

**Query Parameters: **

- `path` (string, required): Path to the image file
- `preview` (boolean, optional): If `true`, returns a lower-quality preview

**Response: **

- `200 OK`: Image file
- `400 Bad Request`: If `path` is missing
- `404 Not Found`: If file not found or not an image

---

## Well-Known

### JWKS (Public Keys)

**GET** `/.well-known/jwks.json`

Returns the public keys for JWT validation.

---

## Example Usage

### 1. Login

```http
POST /login
Content-Type: application/json

{
"username": "user",
"password": "pass"
}
```

### 2. Use JWT Token

Add the following header to all protected requests:

```http
Authorization: Bearer <jwt-token>
```

### 3. Refresh Token

```http
POST /refresh
Content-Type: application/x-www-form-urlencoded

refreshToken=<refresh-token>
```

### 4. Logout

```http
POST /logout
Content-Type: application/x-www-form-urlencoded

refreshToken=<refresh-token>
```

### 5. Get Directory

```http
GET /directory?path=some/folder
Authorization: Bearer <jwt-token>
```

### 6. Get Image

```http
GET /image?path=photo.png&preview=true
Authorization: Bearer <jwt-token>
```

---

## Token Refresh & Logout Flow

- When your access token expires, use the `/refresh` endpoint with your refresh token to obtain a
  new access token and refresh token.
- Always use the latest refresh token returned by the server (refresh tokens are single-use).
- To log out, call `/logout` with your refresh token to invalidate it.
- If the refresh token is invalid or expired, the user must log in again.

---

## Notes

- All `/test/*` routes behave the same as their prod counterparts but use test data and storage.
- JWT-protected routes require a valid token from the `/login` endpoint.
- The `/image` and `/directory` endpoints are for file and directory access, respectively.
- The `/preview` parameter for images returns a lower-quality version for faster loading.

---

## Postman Collection

A ready-to-import Postman collection is available in [
`postman_collection.json`](./postman_collection.json).

**How to import:**

1. Open Postman
2. Click **Import** → **File** → select `postman_collection.json`
3. Set `baseUrl` to your server's address (e.g., `http://localhost:8080`)
4. Use the "Login" request to get a JWT, then set `jwtToken` variable for authorized requests