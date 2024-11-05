# mobile-status


## Run Tests
- Run Unit Tests:  `sbt test`
- Run Integration Tests: `sbt it:test`
- Run Unit and Integration Tests: `sbt test it:test`
- Run Unit and Integration Tests with coverage report: `sbt clean compile coverage test it:test coverageReport dependencyUpdates`


## Endpoints

### GET /status

Responds with status of mobile services/features and an optional full screen info message

```json
{
  "feature": [
    {
      "name": "userPanelSignUp",
      "enabled": false
    },
    {
      "name": "enablePushNotificationTokenRegistration",
      "enabled": false
    },
    {
      "name": "paperlessAlertDialogs",
      "enabled": false
    },
    {
      "name": "paperlessAdverts",
      "enabled": false
    },
    {
      "name": "htsAdverts",
      "enabled": false
    },
    {
      "name": "customerSatisfactionSurveys",
      "enabled": false
    },
    {
      "name": "findMyNinoAddToWallet",
      "enabled": false
    },
    {
      "name": "disableYourEmploymentIncomeChart",
      "enabled": true
    },
    {
      "name": "disableYourEmploymentIncomeChartAndroid",
      "enabled": true
    },
    {
      "name": "disableYourEmploymentIncomeChartIos",
      "enabled": true
    },
    {
      "name": "findMyNinoAddToGoogleWallet",
      "enabled": false
    },
    {
      "name": "useLegacyWebViewForIv",
      "enabled": false
    }
  ],
  "urls": {
    "manageGovGatewayIdUrl": "/"
  },
  "appAuthThrottle": 0,
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