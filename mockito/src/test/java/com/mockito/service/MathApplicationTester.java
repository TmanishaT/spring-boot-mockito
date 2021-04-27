package com.mockito.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import static org.mockito.BDDMockito.*;

/*JUnit and Mockito interation*/
//@RunWith attaches a runner with the test class to initialize the test data
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

	@Test(expected = RuntimeException.class)
	public void testExpcetion() {

		  //add the behavior to throw exception
	      doThrow(new RuntimeException("Add operation not implemented"))
	         .when(calcService).add(10.0,20.0);

	      //test the add functionality
	      Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0); 
	}
	
	@Test
	   public void testAddStubbingWithGenericInterface(){

	      //add the behavior to add numbers
	      when(calcService.add(20.0,10.0)).thenAnswer(new Answer<Double>() {

	         @Override
	         public Double answer(InvocationOnMock invocation) throws Throwable {
	            //get the arguments passed to mock
	            Object[] args = invocation.getArguments();
				
	            //get the mock 
	            Object mock = invocation.getMock();	
				
	            //return the result
	            return 30.0;
	         }
	      });

	      //test the add functionality
	      Assert.assertEquals(mathApplication.add(20.0, 10.0),30.0,0);
	   }
	
	@Test
	   public void testAddBehaviorDriven(){

	      //Given
	      given(calcService.add(20.0,10.0)).willReturn(30.0);

	      //when
	      double result = calcService.add(20.0,10.0);

	      //then
	      Assert.assertEquals(result,30.0,0);   
	   }
}
