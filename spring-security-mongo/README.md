# spring-security-mongo
Spring Security with mongo backend and default setup


'MongoSecurityUserRoles' collection needs to have at least 'username', 'password' and 'roles' fields. An example collection is as follows.

```json
{
    "username" : "user",
    "password" : "111",
    "roles" : [
        "ROLE_NAME_QUERY"
    ]
},
{
    "username" : "admin",
    "password" : "222",
    "roles" : [
        "ROLE_NAME_QUERY",
        "ROLE_NAME_COMMAND"
    ]
}
```
