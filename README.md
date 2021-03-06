# Spring Doma Sample



# Run

- run app
```$xslt
docker-compose up -d
gradle bootRun
```


# Check examples

### Pick up piyo group people.

pick up 'person' record by group name

```$xslt
curl http://localhost:8080/demo/test?groupName=piyo
```


### rollback check

Register 10 valid record into person, then fail to add illegal group_id record.
All 10 valid record will be rollback.

```$xslt
curl http://localhost:8080/demo/rollback
```

### commit work


```$xslt
curl http://localhost:8080/demo/commit?
```

### CRUD


- get

```
curl http://localhost:8080/person/2
```

- create

```
curl -X POST http://localhost:8080/person \
-H 'Content-type: application/json' \
-d '{ "firstName": "user", "lastName": "rest"} '
```


- update

```
curl -X PUT http://localhost:8080/person \
-H 'Content-type: application/json' \
-d '{ "id":"3", "firstName": "guy", "lastName": "updated"} '
```

- delete

```$xslt
curl -X DELETE http://localhost:8080/person/62
```

### Validation

- on-Request (Spring standard like struts)

```$xslt
curl -X POST http://localhost:8080/validate/request \
 -H 'Content-type: application/json' \
 -d '{ "firstName":"hoge","lastName": "rest", "groupId": 5} ' | jq .
```

- on service (Intercept on service)
```$xslt
curl -X POST http://localhost:8080/validate/service \
 -H 'Content-type: application/json' \
 -d '{ "firstName":"hoge","lastName": "rest", "groupId": 5} ' | jq .

```

- on dao (Intercept on dao)
```$xslt
curl -X POST http://localhost:8080/validate/dao \
 -H 'Content-type: application/json' \
 -d '{ "firstName":"hoge","lastName": "rest", "groupId": 5} ' | jq .

```

### Datasource switch

The groupDao uses slave config. so, group api returns slave connection values.
Especially, master database has 3 groups. There ids are 1 to 3.
But dummy slave database has only 2record. There ids are 1 and 2.

So creating person record with group_id 3 will fail with validation error.
That's not a database rejection.

- The data-source switching check
```
curl http://localhost:8080/ds-demo/slave/2
```
