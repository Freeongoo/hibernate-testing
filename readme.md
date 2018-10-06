# Example test hibernate with H2

### The main rule when testing hibernate

After adding the data and before you get this data for testing, you need to run the code:

```
session.flush();
session.clear();
```

* "Flush" - forcibly writes all entities to the database.
* "Clear" - (clear 1st level cache) Completely clear the session. Evict all loaded instances and cancel all pending saves, updates and deletions

Why is this so important? Since there may be cases when we have in the persistent state two objects with the same id

### Example:

```
// 1. insert/create
User userCreated = getUserByDefault();
int idUserForUpdate = userRepository.createUser(userCreated);

// 2. flush and clear
flushAndClearSession();

// 3. get data and assert
User userExpected = UserUtil.createUserWithoutId("second", "second", "second", "123");
userExpected.setId(idUserForUpdate);
userRepository.updateUser(userExpected);

User actualUser = userRepository.getUser(idUserForUpdate);
assertThat(actualUser, equalTo(userExpected));
```

Without `flushAndClearSession()` will throw: `org.hibernate.NonUniqueObjectException: A different object with the same identifier value was already associated with the session`

### Tests

Run tests and after open file `test.log` for analyse sql queries