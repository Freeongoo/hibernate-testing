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

# Tests

When you run tests in the console, the display sql using lib "p6spy" - with params in sql:)

The log file `test.log` contains the sql requests generated using standard tools of hibernate.

## Testing inheritance in Hibernate

#### No inheritance

This strategy is used if we want to share Java code between entity classes.  
Use `@MappedSuperclass` in base class  

see dir: `/src/test/java/org/example/inheritance/no_inheritance`

#### Single table

The Single Table strategy creates one table for each class hierarchy. This is also the default strategy chosen by JPA if we don’t specify one explicitly.

see dir: `/src/test/java/org/example/inheritance/single_table`

#### Table Per Class

The Table Per Class strategy maps each entity to its table which contains all the properties of the entity, including the ones inherited.  
Like "No inheritance"

see dir: `/src/test/java/org/example/inheritance/table_per_class`