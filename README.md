# spring-boot-mockito
Sample spring mockito to demonstrate test methods for Controllers, Service and Repository.
Mockito is an open-source Java-based behavior-driven testing framework. The prime functionality of this framework is that mock objects are auto-created and there is no need to create them explicitly.
Pros:
•	Support for exceptions
•	Mocks can be created using annotations
•	Underlying support for the return values
•	Mock objects don’t need to be manually written
Cons:
•	No support for local variable’s mocking
•	No support for private and static methods
•	For any subclass we write, there is no control over the private fields.

## About project
Test cases are present under src/test/java.
There are test cases for controllers, service and repository under resective folders.

## Requirements

* Starter web

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
    
```
* starter JPA for Hibernate
```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

* starter test for junit

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-test</artifactId>
   <scope>test</scope>
</dependency>
```

* H2 database for saving test data in Inmemory database -- for repository test cases

```
<dependency>
   <groupId>com.h2database</groupId>
   <artifactId>h2</artifactId>
   <scope>test</scope>
   <version>1.4.194</version>
</dependency>
```

## Explanation

use @Autowired to test the current class and use @Mockbean to mock dependencies

```
public class EmployeeServiceImplIntegrationTest {

 @Autowired
 private EmployeeService employeeService;

  @MockBean
  private EmployeeRepository employeeRepository;
}
```
use @RunWith(MockitoJUnitRunner.class) for integrating Mockito with JUnit framework.
using Mockito mock() method, to creates mocks without bothering about the order of method calls that the mock is going to make in due course of its action.
```
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

	private MathApplication mathApplication;
	private CalculatorService calcService;

	@Before
	public void setUp() {
		mathApplication = new MathApplication();
		calcService = mock(CalculatorService.class);
		mathApplication.setCalculatorService(calcService);
	}

	@Test
	public void testAddAndSubtract() {

		// add the behavior to add numbers
		when(calcService.add(20.0, 10.0)).thenReturn(30.0);

		// subtract the behavior to subtract numbers
		when(calcService.subtract(20.0, 10.0)).thenReturn(10.0);

		// test the subtract functionality
		Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

		// limit the method call to 1, no less and no more calls are allowed
		verify(calcService, times(1)).add(20.0, 10.0);

		// verify that method was never called on a mock
		verify(calcService, never()).multiply(10.0, 20.0);

		// check a minimum 1 call count
		verify(calcService, atLeastOnce()).subtract(20.0, 10.0);

		// check if add function is called minimum 2 times
		//verify(calcService, atLeast(2)).add(20.0, 10.0);

		// check if add function is called maximum 3 times
		verify(calcService, atMost(3)).add(20.0, 10.0);

		// verify call to calcService is made or not with same arguments.
		//verify(calcService).add(10.0, 20.0);// this will fail as the order of arguments is diff in the call

		// verify call to add method to be completed within 100 ms
		verify(calcService, timeout(100)).add(20.0, 10.0);

		// invocation count can be added to ensure multiplication invocations
		// can be checked within given timeframe
		verify(calcService, timeout(100).times(1)).subtract(20.0, 10.0);
		System.out.println("I m here");
	}
```

