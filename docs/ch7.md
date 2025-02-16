# Beyond Classes

## Interface
```java
public abstract interface Run { //abstract implicit modifiers

  public abstract void run();  //public abstract implicit modifiers
}
```
Concise form:

```java
public interface Run {
  void run();
}
```
An interface cannot be marked as final
```java
//does not compile
public final interface Walk {}
```
Interface variables are implicitly public, static, final.
```java
public interface Weight {
  int max_weight = 5;
  public static final int max_height = 50;  
}  

```

### Interfaces with the same default name
Class which implements two interfaces with the same(signature) default method:
```java
@Override
public void go() {
    Run.super.go();
    //or
    Walk.super.go();
}
```
[Full Example](../src/main/java/org/enricogiurin/ocp17/book/ch7/interfaces/defaultmethods/InheritTwoDefaultMethods.java)

### private methods
A private interface method **cannot** be called in a method outside the interface declaration.

```java
interface InterfaceWithAPrivateMethod {
  private void message();
  void hello();
}

class InterfaceWithAPrivateMethodImpl implements InterfaceWithAPrivateMethod {
  @Override
  public void hello(){
    //does not compile!
    //message();
  }
}
```

### Override
_@Override_ annotation informs the compiler that the element is meant to override an element declared
in a superclass and/or interface. While **it is not required** to use this annotation when overriding a method, it helps
to prevent errors.

### Default Methods
First, if a class or interface inherits two interfaces containing default methods with the same
signature, it must override the method with its own implementation.

[Inherit two default methods with the same signature](../src/main/java/org/enricogiurin/ocp17/book/ch7/interfaces/defaultmethods/InheritTwoDefaultMethods.java)

## Sealed
### Same file
The permits clause is optional if the subclass is nested or declared in the same file.

```java
//same file
public sealed class Snake {}
final class Cobra extends Snake {}
```
### Different files
```java
public sealed class Snake permits Cobra, Viper {}
//separated files
final class Cobra extends Snake {}
non-sealed class Viper extends Snake {}
```
```java
//same file
//no permits needed 
sealed class HumanBeing {}

sealed class Male extends HumanBeing {}
non-sealed class EuropeanMale extends HumanBeing {}
final class AsianMale extends HumanBeing {}
```
A subclass (Male) of a sealed class (HumanBeing) must be marked either **final** or **sealed** or **non-sealed**.

### Sealed interfaces
Permits list can apply to:
* a class that implements the interface
* An interface that extends the interface.
```java
// Sealed interface
public sealed interface Pet permits Cat, Dog, Rabbit {}

// Classes permitted to implement sealed interface Pet
public final class Cat implements Pet {}
public final class Dog implements Pet {}

// Interface permitted to extend sealed interface pet
public non-sealed interface Rabbit extends Pet {}
```
[Example of sealed interface](../src/main/java/org/enricogiurin/ocp17/book/ch7/sealed/interfaces/Pet.java)
### Sealed Classes
The permits clause is optional if the subclass is nested or declared in the same file.
## Final
```java
final class AFinal {
  public static void main(String[] args) {
    //I can't override a final class
    //new AFinal() {}; //does not compile
  }
}
```
## Record
### Compact Constructor
```java
public record Person(String firstName, String lastName) {

  //this is a compact constructor
  public Person {  //no parenthesis!!!
    if (firstName.isEmpty() || lastName.isEmpty()) {
      throw new IllegalArgumentException("invalid");
    }
  }
}
```
A compact constructor cannot set an instance variable through this but just with the normal assignment operator.
```java
public record Name(String name) {
  Name {
    name = "Enrico"; //this works
    //this.name = "Enrico"; //this does not compile
  }
}
```

### Overloaded constructor
```java
public record Person(String firstName, String lastName) {

  //this is an overloaded constructor 
  public Person() {  
    //he first line must be a call to another constructor,
    this("Enrico", "Giurin");
  }
}
```
### Record with instance variables
```java
record Game() {
  //does not compile
   final int score = 10;
}

```
It does not compile because records cannot include instance variables not listed in the declaration of the record, 
as it could break immutability.

## enum
### Abstract method
If an enum contains an abstract method, then every enum value must include an override of this abstract method.
```java
public enum Gender {
  MALE {
    @Override
    public String description() {
      return "male";
    }
  }, FEMALE {
    @Override
    public String description() {
      return "female";
    }
  };
  public abstract String description();
}
```
### Constructors
enum constructors are implicitly private.
```java
  public SeasonWithValues(String description) {  //does not compile!
    this.description = description;
  }
```
[Enum With Fields](../src/main/java/org/enricogiurin/ocp17/book/ch7/useofenum/SeasonWithValues.java)