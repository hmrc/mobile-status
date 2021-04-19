# mobile-status

## Endpoints

### GET /status

Responds with status of mobile services/features and an optional full screen info message

```json
{
  "feature": [
    {
      "name": "componentisedAccessCodes",
      "enabled": false
    }
  ],
  "fullScreenInfoMessage": {
    "id": "496dde52-4912-4af2-8b3c-33c6f8afedf9",
    "type": "Info",
    "content": {
      "title": "Some title",
      "body": "Some body"
    },
    "links": [
      {
        "url": "https://www.abc.com",
        "urlType": "Normal",
        "type": "Secondary",
        "message": "Title 1"
      },
      {
        "urlType": "Dismiss",
        "type": "Primary",
        "message": "Title 2"
      }
    ]
  }
}
```

#### Response

- 200 - Ok