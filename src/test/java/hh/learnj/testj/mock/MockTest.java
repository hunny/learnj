/**
 * 
 */
package hh.learnj.testj.mock;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 * 测试Mock使用
 * 
 * <p>
 * 参见：
 * <a href="http://blog.csdn.net/sdyy321/article/details/38757135/">mockito简单教程</a>
 * <p>
 * <a href="https://gojko.net/2009/10/23/mockito-in-six-easy-examples/">Mockito in six easy examples</a>
 * <p>
 * <a href="http://bijian1013.iteye.com/blog/1987361">Mockito(一) --入门篇</a>
 * 
 * <p>
 * 9. 或者使用built-in runner：MockitoJUnitRunner
 * <p>
 * 更多的注解还有@Captor,@Spy,@InjectMocks
 * 
 * @author huzexiong
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MockTest {

	/**
	 * 9. 使用注解来快速模拟
	 * <p>
	 * 在上面的测试中我们在每个测试方法里都mock了一个List对象，为了避免重复的mock，是测试类更具有可读性，
	 * 我们可以使用下面的注解方式来快速模拟对象：
	 */
	@SuppressWarnings("rawtypes")
	@Mock
	private List mockList;

	/**
	 * 9. 运行这个测试类你会发现报错了，9的注解mockList的mock对象为NULL，为此我们必须在基类中添加初始化mock的代码
	 */
	public MockTest() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * 9. 用注解的mock对象测试
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void shorthand() {
		mockList.add(1);
		Mockito.verify(mockList).add(1);
	}

	/**
	 * 1、验证行为
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void verify_behaviour() {
		// 模拟创建一个List对象
		List mock = Mockito.mock(List.class);
		// 使用mock的对象
		mock.add(1);
		mock.clear();
		// 验证add(1)和clear()行为是否发生
		Mockito.verify(mock).add(1);
		Mockito.verify(mock).clear();
	}

	/**
	 * 2、模拟我们所期望的结果
	 */
	@SuppressWarnings({ "rawtypes" })
	@Test
	public void when_thenReturn() {
		// mock一个Iterator类
		Iterator iterator = Mockito.mock(Iterator.class);
		// 预设当iterator调用next()时第一次返回hello，第n次都返回world
		Mockito.when(iterator.next()).thenReturn("hello").thenReturn("world");
		// 使用mock的对象
		String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
		// 验证结果
		Assert.assertEquals("hello world world", result);
	}

	/**
	 * 2、模拟我们所期望的结果
	 */
	@Test(expected = IOException.class)
	public void when_thenThrow() throws IOException {
		OutputStream outputStream = Mockito.mock(OutputStream.class);
		// 预设当流关闭时抛出异常
		Mockito.doThrow(new IOException()).when(outputStream).close();
		outputStream.close();
	}

	/**
	 * 3、参数匹配
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void with_arguments() {
		Comparable comparable = Mockito.mock(Comparable.class);
		// 预设根据不同的参数返回不同的结果
		Mockito.when(comparable.compareTo("Test")).thenReturn(1);
		Mockito.when(comparable.compareTo("Omg")).thenReturn(2);
		Assert.assertEquals(1, comparable.compareTo("Test"));
		Assert.assertEquals(2, comparable.compareTo("Omg"));
		// 对于没有预设的情况会返回默认值
		Assert.assertEquals(0, comparable.compareTo("Not stub"));
	}

	/**
	 * 除了匹配制定参数外，还可以匹配自己想要的任意参数
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void with_unspecified_arguments() {
		List list = Mockito.mock(List.class);
		// 匹配任意参数
		Mockito.when(list.get(Mockito.anyInt())).thenReturn(1);
		Mockito.when(list.contains(Mockito.argThat(new IsValid()))).thenReturn(true);
		Assert.assertEquals(1, list.get(1));
		Assert.assertEquals(1, list.get(999));
		Assert.assertTrue(list.contains(1));
		Assert.assertTrue(!list.contains(3));
	}

	@SuppressWarnings("rawtypes")
	private class IsValid extends ArgumentMatcher<List> {
		@Override
		public boolean matches(Object o) {
			return (o.equals(1) || o.equals(2));
		}
	}

	/**
	 * 需要注意的是如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void all_arguments_provided_by_matchers() {
		Comparator comparator = Mockito.mock(Comparator.class);
		comparator.compare("nihao", "hello");
		// 如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
		Mockito.verify(comparator).compare(Mockito.anyString(), Mockito.eq("hello"));
		// 下面的为无效的参数匹配使用
		// Mockito.verify(comparator).compare(Mockito.anyString(),"hello");
	}

	/**
	 * 4、验证确切的调用次数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void verifying_number_of_invocations() {
		List list = Mockito.mock(List.class);
		list.add(1);
		list.add(2);
		list.add(2);
		list.add(3);
		list.add(3);
		list.add(3);
		// 验证是否被调用一次，等效于下面的times(1)
		Mockito.verify(list).add(1);
		Mockito.verify(list, Mockito.times(1)).add(1);
		// 验证是否被调用2次
		Mockito.verify(list, Mockito.times(2)).add(2);
		// 验证是否被调用3次
		Mockito.verify(list, Mockito.times(3)).add(3);
		// 验证是否从未被调用过
		Mockito.verify(list, Mockito.never()).add(4);
		// 验证至少调用一次
		Mockito.verify(list, Mockito.atLeastOnce()).add(1);
		// 验证至少调用2次
		Mockito.verify(list, Mockito.atLeast(2)).add(2);
		// 验证至多调用3次
		Mockito.verify(list, Mockito.atMost(3)).add(3);
	}

	/**
	 * 5、模拟方法体抛出异常
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected = RuntimeException.class)
	public void doThrow_when() {
		List list = Mockito.mock(List.class);
		Mockito.doThrow(new RuntimeException()).when(list).add(1);
		list.add(1);
	}

	/**
	 * 6、验证执行顺序
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void verification_in_order() {
		List list = Mockito.mock(List.class);
		List list2 = Mockito.mock(List.class);
		list.add(1);
		list2.add("hello");
		list.add(2);
		list2.add("world");
		// 将需要排序的mock对象放入InOrder
		InOrder inOrder = Mockito.inOrder(list, list2);
		// 下面的代码不能颠倒顺序，验证执行顺序
		inOrder.verify(list).add(1);
		inOrder.verify(list2).add("hello");
		inOrder.verify(list).add(2);
		inOrder.verify(list2).add("world");
	}

	/**
	 * 7、确保模拟对象上无互动发生
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void verify_interaction() {
		List list = Mockito.mock(List.class);
		List list2 = Mockito.mock(List.class);
		List list3 = Mockito.mock(List.class);
		list.add(1);
		Mockito.verify(list).add(1);
		Mockito.verify(list, Mockito.never()).add(2);
		// 验证零互动行为
		Mockito.verifyZeroInteractions(list2, list3);
	}

	/**
	 * 8、找出冗余的互动(即未被验证到的)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(expected = NoInteractionsWanted.class)
	public void find_redundant_interaction() {
		List list = Mockito.mock(List.class);
		list.add(1);
		list.add(2);
		Mockito.verify(list, Mockito.times(2)).add(Mockito.anyInt());
		// 检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过
		Mockito.verifyNoMoreInteractions(list);

		List list2 = Mockito.mock(List.class);
		list2.add(1);
		list2.add(2);
		Mockito.verify(list2).add(1);
		// 检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常
		Mockito.verifyNoMoreInteractions(list2);
	}

	/**
	 * 10、连续调用
	 */
	@Test(expected = RuntimeException.class)
	public void consecutive_calls() {
		// 模拟连续调用返回期望值，如果分开，则只有最后一个有效
		Mockito.when(mockList.get(0)).thenReturn(0);
		Mockito.when(mockList.get(0)).thenReturn(1);
		Mockito.when(mockList.get(0)).thenReturn(2);
		Mockito.when(mockList.get(1)).thenReturn(0).thenReturn(1).thenThrow(new RuntimeException());
		Assert.assertEquals(2, mockList.get(0));
		Assert.assertEquals(2, mockList.get(0));
		Assert.assertEquals(0, mockList.get(1));
		Assert.assertEquals(1, mockList.get(1));
		// 第三次或更多调用都会抛出异常
		mockList.get(1);
	}

	/**
	 * 11、使用回调生成期望值
	 */
	@Test
	public void answer_with_callback() {
		// 使用Answer来生成我们我们期望的返回
		Mockito.when(mockList.get(Mockito.anyInt())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return "hello world:" + args[0];
			}
		});
		Assert.assertEquals("hello world:0", mockList.get(0));
		Assert.assertEquals("hello world:999", mockList.get(999));
	}

	/**
	 * 12、监控真实对象
	 * <p>
	 * 使用spy来监控真实的对象，需要注意的是此时我们需要谨慎的使用when-then语句，而改用do-when语句
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(expected = IndexOutOfBoundsException.class)
	public void spy_on_real_objects() {
		List list = new LinkedList();
		List spy = Mockito.spy(list);
		// 下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
		// when(spy.get(0)).thenReturn(3);

		// 使用doReturn-when可以避免when-thenReturn调用真实对象api
		Mockito.doReturn(999).when(spy).get(999);
		// 预设size()期望值
		Mockito.when(spy.size()).thenReturn(100);
		// 调用真实对象的api
		spy.add(1);
		spy.add(2);
		Assert.assertEquals(100, spy.size());
		Assert.assertEquals(1, spy.get(0));
		Assert.assertEquals(2, spy.get(1));
		Mockito.verify(spy).add(1);
		Mockito.verify(spy).add(2);
		Assert.assertEquals(999, spy.get(999));
		spy.get(2);
	}

	/**
	 * 13、修改对未预设的调用返回默认期望值
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void unstubbed_invocations() {
		// mock对象使用Answer来对未预设的调用返回默认期望值
		List mock = Mockito.mock(List.class, new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return 999;
			}
		});
		// 下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
		Assert.assertEquals(999, mock.get(1));
		// 下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
		Assert.assertEquals(999, mock.size());
	}

	/**
	 * 14、捕获参数来进一步断言
	 */
	@Test
	public void capturing_args() {
		PersonDao personDao = Mockito.mock(PersonDao.class);
		PersonService personService = new PersonService(personDao);
		ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
		personService.update(1, "jack");
		Mockito.verify(personDao).update(argument.capture());
		Assert.assertEquals(1, argument.getValue().getId());
		Assert.assertEquals("jack", argument.getValue().getName());
	}

	class Person {
		private int id;
		private String name;

		Person(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	interface PersonDao {
		public void update(Person person);
	}

	class PersonService {
		private PersonDao personDao;

		PersonService(PersonDao personDao) {
			this.personDao = personDao;
		}

		public void update(int id, String name) {
			personDao.update(new Person(id, name));
		}
	}

	/**
	 * 15、真实的部分mock
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void real_partial_mock() {
		// 通过spy来调用真实的api
		List list = Mockito.spy(new ArrayList());
		Assert.assertEquals(0, list.size());
		A a = Mockito.mock(A.class);
		// 通过thenCallRealMethod来调用真实的api
		Mockito.when(a.doSomething(Mockito.anyInt())).thenCallRealMethod();
		Assert.assertEquals(999, a.doSomething(999));
	}

	class A {
		public int doSomething(int i) {
			return i;
		}
	}

	/**
	 * 16、重置mock
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void reset_mock() {
		List list = Mockito.mock(List.class);
		Mockito.when(list.size()).thenReturn(10);
		list.add(1);
		Assert.assertEquals(10, list.size());
		// 重置mock，清除所有的互动和预设
		Mockito.reset(list);
		Assert.assertEquals(0, list.size());
	}

}
