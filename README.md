**EXAMPLE**
---
- SIGN UP:

Request: 

~~~
curl -X POST \
  http://localhost:8080/auth/sign-up \
  -H 'Accept-Language: en' \
  -H 'Content-Type: application/json' \
  -d '{
	"email" : "test@test.ru",
	"password": "testTest"
}'
~~~

Response: 
~~~
{
    "message": "User registered successfully!"
}
~~~

---
- SIGN IN:

Request: 

~~~
curl -X POST \
  http://localhost:8080/auth/sign-in \
  -H 'Accept-Language: en' \
  -H 'Content-Type: application/json' \
  -d '{
	"email" : "test@test.ru",
	"password": "testTest"
}'
~~~

Response:
~~~
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QucnUiLCJpYXQiOjE1ODQ0ODczNzIsImV4cCI6MTU4NDU3Mzc3Mn0.4FGyffXPxWmEdlTn-U0gboE53b-cU2mFkgwmV9CM6pqeKbNB9xBeWOZYlbZfDsyKLC5EzOcjxj1ZdNvTnu_Q8w",
    "id": 3,
    "email": "test@test.ru",
    "roles": [
        "ROLE_USER"
    ],
    "tokenType": "Bearer"
}
~~~

---
- OTHER REQUESTS:

Request Bank accounts:
~~~
curl -X GET \
  http://localhost:8080/bank-accounts/ \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QucnUiLCJpYXQiOjE1ODQ0ODczNzIsImV4cCI6MTU4NDU3Mzc3Mn0.4FGyffXPxWmEdlTn-U0gboE53b-cU2mFkgwmV9CM6pqeKbNB9xBeWOZYlbZfDsyKLC5EzOcjxj1ZdNvTnu_Q8w'
~~~

Response:
```
[
    {
        "id": 4,
        "name": null,
        "cardNumber": 111122223334444,
        "expirationDateTime": "2020-04-12T14:45:33.701Z",
        "cardCvvNumber": 555,
        "cardType": "DEBIT",
        "currency": "RUB",
        "cardSystem": "MASTER_CARD",
        "balance": 0.00,
        "feeFrequency": "MONTHLY",
        "registrationDate": "2020-03-18",
        "bankAccountFeeDto": null,
        "lastFeeWithdrawDate": null
    }
]
```
