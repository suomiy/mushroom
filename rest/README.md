##REST INTERFACE DOCUMENTATION

Rest is accessible through http://localhost:8080/pa165/rest.
Every entity controller responses to several requests. This document shows just a few cases.

###Functionality examples:
#####Hunter - HunterController.java

Register new Hunter.
```
curl -X POST -H 'Content-Type: application/json' -d '{"firstName":"Jandak","surname":"","nick":"honysek","email":"mahony@globaldjs.com","type":"USER","rank":"BEGINNER","unencryptedPassword":"123456"}' http://localhost:8080/pa165/rest/hunter/register
```

Delete Hunter.
```
curl -X DELETE  http://localhost:8080/pa165/rest/hunter/3
```

Find Hunter by id.
```
curl http://localhost:8080/pa165/rest/hunter/7
```

Find Hunter by email
```
curl http://localhost:8080/pa165/rest/hunter/findbyemail?email=michalkysilko@gmail.com
```

#####Mushroom - MushroomController.java

Create a new Mushroom
```
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Hlíva ústřičná","description":"houba,jedlá","fromDate":1,"toDate":11111111111,"type":"POISONOUS"}' http://localhost:8081/pa165/rest/mushroom/create
```

Find Mushrooms by type
```
curl http://localhost:8081/pa165/rest/mushroom/findbytype?type=EDIBLE
```

Find Mushrooms by date
```
curl -X POST -H "Content-Type: application/json" -d '{"date":"2016-11-30 00:00"}'  http://localhost:8081/pa165/rest/mushroom/findbydate
```

Find Mushrooms by date interval
```
curl -X POST -H "Content-Type: application/json" -d '{"from":"2016-07-30 00:00","to":"2016-08-10 00:00"}'  http://localhost:8081/pa165/rest/mushroom/findbydateinterval
```

#####Forest - ForestController.java

Create a new Forest.
```
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Les","localityDescription":"Mezi dalšími lesy"}' http://localhost:8080/pa165/rest/forest/create
```

Update a Forest.
```
url -X POST -H 'Content-Type: application/json' -d '{"id":"3", "name":"Myslivcův les","localityDescription":"Leží v oblasti velkých kamenů."}' http://localhost:8080/pa165/rest/forest/update
```

Find a Forest by name.
```
curl http://localhost:8080/pa165/rest/forest/find?name=Vranovsk%C3%A9%20lesy
```

#####MushroomCount - MushroomCountController.java

Find all MushroomCounts
```
curl http://localhost:8081/pa165/rest/mushroomcount/findall
```

#####Visit - VisitController.java

Find a Visit by Hunter id
```
curl http://localhost:8081/pa165/rest/visit/findbyhunter?id=3
```

Find a Visit by Forest id
```
curl http://localhost:8081/pa165/rest/visit/findbyforest?id=3
```

