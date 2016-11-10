/**
 * 
 */
package hh.learnj.testj.mock.enchance;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * @author huzexiong
 *
 */
public class MockitoTest {

	@Mock
	MyDatabase databaseMock;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Test
	public void testQuery() {
		ClassToTest t = new ClassToTest(databaseMock);
		boolean check = t.query("* from t");
		Assert.assertTrue(check);
		verify(databaseMock).query("* from t");
	}

	@Test
	public void testMockito() {
		ClassToTest t = Mockito.mock(ClassToTest.class);
		Mockito.when(t.query(Mockito.anyString())).thenReturn(true);
		boolean check = t.query("* from t");
		Assert.assertTrue(check);
	}

	@Test
	public void test1() {
		// create mock
		ClassToTest test = Mockito.mock(ClassToTest.class);

		// define return value for method getUniqueId()
		Mockito.when(test.getUniqueId()).thenReturn(43);

		// use mock in test....
		Assert.assertEquals(test.getUniqueId(), 43);
	}

	// Demonstrates the return of multiple values
	@SuppressWarnings("rawtypes")
	@Test
	public void testMoreThanOneReturnValue() {
		Iterator i = Mockito.mock(Iterator.class);
		Mockito.when(i.next()).thenReturn("Mockito").thenReturn("rocks");
		String result = i.next() + " " + i.next();
		// assert
		Assert.assertEquals("Mockito rocks", result);
	}

	// this test demonstrates how to return values based on the input
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testReturnValueDependentOnMethodParameter() {
		Comparable c = Mockito.mock(Comparable.class);
		Mockito.when(c.compareTo("Mockito")).thenReturn(1);
		Mockito.when(c.compareTo("Eclipse")).thenReturn(2);
		// assert
		Assert.assertEquals(1, c.compareTo("Mockito"));
	}

	// this test demonstrates how to return values independent of the input
	// value

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testReturnValueInDependentOnMethodParameter1() {
		Comparable c = Mockito.mock(Comparable.class);
		Mockito.when(c.compareTo(Mockito.anyInt())).thenReturn(-1);
		// assert
		Assert.assertEquals(-1, c.compareTo(9));
	}

	// return a value based on the type of the provide parameter

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testReturnValueInDependentOnMethodParameter2() {
		Comparable c = Mockito.mock(Comparable.class);
		Mockito.when(c.compareTo(Mockito.isA(Todo.class))).thenReturn(0);
		// assert
		Todo todo = new Todo(5);
		Assert.assertEquals(c.compareTo(todo), c.compareTo(new Todo(1)));
	}

	// this test demonstrates how use doThrow

	@Test(expected = IOException.class)
	public void testForIOException() throws IOException {
		// create an configure mock
		OutputStream mockStream = Mockito.mock(OutputStream.class);
		Mockito.doThrow(new IOException()).when(mockStream).close();

		// use mock
		OutputStreamWriter streamWriter = new OutputStreamWriter(mockStream);
		streamWriter.close();
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testSpy() {
		// Lets mock a LinkedList
		List list = new LinkedList();
		List spyList = Mockito.spy(list);

		// You have to use doReturn() for stubbing
		Mockito.doReturn("foo").when(spyList).get(0);

		// this would not work
		// real method is called so spy.get(0)
		// throws IndexOutOfBoundsException (list is still empty)
		Mockito.when(spyList.get(0)).thenReturn("foo");
	}
}
