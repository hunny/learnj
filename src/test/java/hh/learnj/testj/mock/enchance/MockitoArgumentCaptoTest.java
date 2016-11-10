/**
 * 
 */
package hh.learnj.testj.mock.enchance;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * @author huzexiong
 *
 */
public class MockitoArgumentCaptoTest {
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Captor
	private ArgumentCaptor<List<String>> captor;

	@SuppressWarnings("unchecked")
	@Test
	public final void shouldContainCertainListItem() {
		List<String> asList = Arrays.asList("someElement_test", "someElement");
		final List<String> mockedList = Mockito.mock(List.class);
		mockedList.addAll(asList);

		Mockito.verify(mockedList).addAll(captor.capture());
		final List<String> capturedArgument = captor.getValue();
		assertThat(capturedArgument, Matchers.hasItem("someElement"));
	}
}
