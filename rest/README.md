## REST INTERFACE DOCUMENTATION

### Configuration:

* Rest is accessible through http://localhost:8080/pa165/rest.  
* You can try already preconfigured Admin account: `admin@hunter.cz` with password `test`

### Authentication:

It is needed to authenticate yourself before any requests which changes data on the server (except basic registration). 

* All find operations are accessible without authentication. 
* You may still get 403 Forbidden if you are not authorized for that particular operation.

##### Registration

To register jsmith@dmail.com with password 123456

```
curl -X POST -H 'Content-Type: application/json' -d '{
    "firstName":"John",
    "surname":"Smith",
    "nick":"jsmith",
    "email":"jsmith@dmail.com",
    "type":"USER",
    "rank":"BEGINNER",
    "password":"123456"}' http://localhost:8080/pa165/rest/hunter/register
```

Notes

* You don't need to specify `"type":"USER","rank":"BEGINNER"` as these are set by default.
* You have to authorize yourself as Admin to send different roles or ranks.

Possible Roles

* USER
* ADMIN

Possible Ranks

* BEGINNER
* SKILLED
* EXPERT
* GURU

##### Authentication OAuth2

1. Get your client id and client secret:
  * Client Id: `rest-client`
  * Client Secret: `58aa46b5-ddb1-4a29-bed9-55b9f3521280`

2. Generate Authorization header
  * Header (client id + secret in Base64): 
  
    `Authorization: Basic cmVzdC1jbGllbnQ6NThhYTQ2YjUtZGRiMS00YTI5LWJlZDktNTViOWYzNTIxMjgw`

3. Authenticate yourself as jsmith@dmail.com with password 123456

    ```
    curl -X POST -H "Authorization: Basic cmVzdC1jbGllbnQ6NThhYTQ2YjUtZGRiMS00YTI5LWJlZDktNTViOWYzNTIxMjgw" "http://localhost:8080/pa165/oauth/token?grant_type=password&username=jsmith@dmail.com&password=123456"
    ```

4. Get a response

    You will get access token valid for 10 minutes    
    
    ```json
    {
      "access_token": "e1aaa981-9384-4761-b2ae-f21a316794a5",
      "token_type": "bearer",
      "refresh_token": "d40db753-2b3f-4822-bfbc-509d11a32638",
      "expires_in": 600,
      "scope": "read write trust",
      "role": "USER",
      "userId": 13
    }
    ```

5. Setup header before api request

    Setup authorization header before any request to the API: 
    
    `"Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5"`

    ```
    curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" -d '{
        "name": "Conocybe filaris",
        "type": "POISONOUS"
    }' "http://localhost:8080/pa165/rest/mushroom/create"
    ```

6. Refresh expired token
  * You need to get a new access token in step 3 after the old one has expired. 
  * You can call step 3 again to also check the remaining time


### API:
Do not forget to authenticate yourself before all requests except retrieving information (find).


#### Hunter


###### Update a Hunter (User)

```
curl -X POST -H 'Content-Type: application/json' -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" -d '{
    "id":13,
    "firstName":"Jason",
    "surname":"Smith",
    "nick":"jsmith",
    "email":"jsmith@dmail.com",
    "type":"USER",
    "rank":"BEGINNER"}' http://localhost:8080/pa165/rest/hunter/update
```

###### Delete a Hunter (User)

```
curl -X DELETE -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" http://localhost:8080/pa165/rest/hunter/1
```

###### Find Hunter by id
```
curl http://localhost:8080/pa165/rest/hunter/7
```

###### Find Hunter by email
```
curl http://localhost:8080/pa165/rest/hunter/findbyemail?email=michalkysilko@gmail.com
```

###### Find all Hunters

```
curl http://localhost:8080/pa165/rest/hunter/findall
```

#### Mushroom

###### Create a new Mushroom

```
curl -X POST -H 'Content-Type: application/json' -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5"  -d '{
    "name":"Hlíva ústřičná",
    "description":"houba,jedlá",
    "fromDate":"2016-06-01 00:00",
    "toDate":"2016-10-15 00:00",
    "type":"EDIBLE"}' http://localhost:8080/pa165/rest/mushroom/create
```


###### Update a Mushroom

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5"  -d '{
    "id":5,
    "name":"Hlíva ústřičná  (Pleurotus ostreatus)",
    "description":"houba,jedlá",
    "fromDate":"2016-06-01 00:00",
    "toDate":"2016-10-15 00:00",
    "type":"EDIBLE"}' http://localhost:8080/pa165/rest/mushroom/update
```

###### Delete a Mushroom

```
curl -X DELETE -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" http://localhost:8080/pa165/rest/mushroom/1
```

###### Find Mushroom by id
```
curl http://localhost:8080/pa165/rest/mushroom/4
```

###### Find Mushroom by name
```
curl http://localhost:8080/pa165/rest/mushroom/find?name=bedla
```


###### Find Mushrooms by type

```
curl http://localhost:8080/pa165/rest/mushroom/findbytype?type=EDIBLE
```

###### Find Mushrooms by date

```
curl -X POST -H "Content-Type: application/json" -d '{
    "date":"2016-11-30 00:00"}'  http://localhost:8080/pa165/rest/mushroom/findbydate
```

###### Find Mushrooms by date interval

```
curl -X POST -H "Content-Type: application/json" -d '{
    "from":"2016-07-30 00:00",
    "to":"2016-08-10 00:00"}'  http://localhost:8080/pa165/rest/mushroom/findbydateinterval
```


###### Find all Mushrooms

```
curl http://localhost:8080/pa165/rest/mushroom/findall
```


#### Forest

###### Create a new Forest

```
curl -X POST -H 'Content-Type: application/json' -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" -d '{
    "name":"Les",
    "localityDescription":
    "Mezi dalšími lesy"}' http://localhost:8080/pa165/rest/forest/create
```

###### Update a Forest

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5"  -d '{
    "id":4,
    "name":"Černý Les",
    "localityDescription":
    "Mezi dalšími lesy"}' http://localhost:8080/pa165/rest/forest/update
```


###### Delete a Forest

```
curl -X DELETE -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" http://localhost:8080/pa165/rest/forest/1
```


###### Update a Forest

```
url -X POST -H 'Content-Type: application/json' -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" -d '{
    "id":"3",
     "name":"Myslivcův les",
     "localityDescription":"Leží v oblasti velkých kamenů."}' http://localhost:8080/pa165/rest/forest/update
```

###### Find Forest by id
```
curl http://localhost:8080/pa165/rest/forest/4
```

###### Find a Forest by name

```
curl http://localhost:8080/pa165/rest/forest/find?name=Vranovsk%C3%A9%20lesy
```


###### Find all Forests

```
curl http://localhost:8080/pa165/rest/forest/findall
```

#### MushroomCount

###### Create a new MushroomCount

```
curl -X POST -H 'Content-Type: application/json' -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" -d '{
    "mushroomId":1,
    "visitId":4,
    "count":5}' http://localhost:8080/pa165/rest/mushroomcount/create
```

###### Update a MushroomCount

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5"  -d '{
    "id": 5,
    "mushroomId":2,
    "visitId":4,
    "count":5}' http://localhost:8080/pa165/rest/mushroomcount/update
```

###### Delete a MushroomCount

```
curl -X DELETE -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" http://localhost:8080/pa165/rest/mushroomcount/1
```

###### Find MushroomCount by id
```
curl http://localhost:8080/pa165/rest/mushroomcount/1
```

###### Find all MushroomCounts

```
curl http://localhost:8080/pa165/rest/mushroomcount/findall
```

###### Find all RecentlyPickable

```
curl http://localhost:8080/pa165/rest/mushroomcount/findrecentlypickable
```

#### Visit

###### Create a new Visit

```
curl -X POST -H 'Content-Type: application/json' -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" -d '{
    "hunterId":1,
    "forestId":2,
    "note":"Nice visit",
    "fromDate":"2016-09-30 07:36",
    "toDate":"2016-09-30 12:00"}' http://localhost:8080/pa165/rest/visit/create
```

###### Update a Visit

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5"  -d '{
    "id": 2,
    "hunterId":2,
    "forestId":3,
    "note":"Nice visit",
    "fromDate":"2016-09-30 07:36",
    "toDate":"2016-09-30 12:00",
    "mushroomsCount": [{
        "id":6, 
        "mushroomId":1,
        "visitId":2,
        "count":20}
    ]}' http://localhost:8080/pa165/rest/visit/update
```
###### Delete a Visit

```
curl -X DELETE -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" http://localhost:8080/pa165/rest/visit/1
```

###### Find Visit by id
```
curl http://localhost:8080/pa165/rest/visit/1
```

###### Find a Visit by Hunter id

```
curl http://localhost:8080/pa165/rest/visit/findbyhunter?id=3
```

###### Find a Visit by Forest id

```
curl http://localhost:8080/pa165/rest/visit/findbyforest?id=3
```

###### Find a Visit by Mushroom

```
curl http://localhost:8080/pa165/rest/visit/findbymushroom?id=3
```

###### Find all Visits

```
curl http://localhost:8080/pa165/rest/visit/findall
```

