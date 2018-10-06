# Example test hibernate with H2

## The main rule when testing hibernate

After adding the data and before you get this data for testing, you need to run the code:

```
session.flush();
session.clear();
```

* "Flush" - forcibly writes all entities to the database.
* "Clear" - (clear 1st level cache) Completely clear the session. Evict all loaded instances and cancel all pending saves, updates and deletions

## Example:

```
// 1. insert/create
User userCreated = getUserByDefault();
int idUserForUpdate = userRepository.createUser(userCreated);

// 2. flush and clear
session.flush();
session.clear();

// 3. get data and assert
User actualUser = userRepository.getUser(idUserForUpdate);
assertThat(actualUser, equalTo(userExpected));
```