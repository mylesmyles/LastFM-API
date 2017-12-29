package net.mylesputnam.lastfm.api.requests;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class LastFmRequestorTest {
	private static final String FAKE_XML_RESPONSE = "<fake>This is a fake response!</fake>";
	
	private static LastFmApiRequestor createMockedRequestWithDefaultDelay() throws InterruptedException, IOException {
		LastFmApiRequestor requestor = Mockito.spy(LastFmApiRequestor.createWithDefaultRequestDelay());
		Mockito.doReturn(FAKE_XML_RESPONSE).when(requestor).getResponse(Mockito.any(URL.class));
		return requestor;
	}
	
	private static LastFmApiRequestor createMockedRequestWithDelay(long requestDelay) throws InterruptedException, IOException {
		LastFmApiRequestor requestor = Mockito.spy(LastFmApiRequestor.createWithCustomRequestDelay(requestDelay));
		Mockito.doReturn(FAKE_XML_RESPONSE).when(requestor).getResponse(Mockito.any(URL.class));
		return requestor;
	}
	
	private static LastFmRequest createGenericRequest() {
		return LastFmRequestBuilder.create("hi").withDefaultParam(LastFmParam.artist("Yes")).buildRequest();
	}
	
	private void assertTimeIsWithinMax(long startTime, long maxTimeInMs) {
		long timeTaken = Math.abs(System.currentTimeMillis() - startTime);
		Assert.assertTrue(
				"Time taken [" + timeTaken + "] is greater than than max time [" + maxTimeInMs + "]",
				timeTaken < maxTimeInMs);
	}
	
	private void assertTimeIsAboveMin(long startTime, long minTimeInMs) {
		long timeTaken = Math.abs(System.currentTimeMillis() - startTime);
		Assert.assertTrue(
				"Time taken [" + timeTaken + "] is less than or equal to min time [" + minTimeInMs + "]",
				timeTaken > minTimeInMs);
	}
	
	@Test
	public void testCreateDefaultUsesDefaultDelay()  throws InterruptedException, IOException {
		LastFmApiRequestor requestor = createMockedRequestWithDefaultDelay();
		Assert.assertEquals(RequestScheduler.DEFAULT_REQUEST_DELAY_MS, requestor.requestSpacer.requestDelayMs);
	}
	
	@Test
	public void testCreateCustomSetsCorrectDelay() throws InterruptedException, IOException {
		LastFmApiRequestor requestor = createMockedRequestWithDelay(100);
		Assert.assertEquals(100, requestor.requestSpacer.requestDelayMs);
	}
	
	@Test
	public void testSyncResponseGetsResult() throws InterruptedException, IOException {
		LastFmApiRequestor requestor = createMockedRequestWithDelay(100);
		LastFmRequest request = createGenericRequest();
		Assert.assertTrue(!requestor.request(request).isEmpty());
	}
	
	@Test
	public void testFirstSyncRequestTakesNoTime() throws InterruptedException, IOException {
		LastFmApiRequestor requestor = createMockedRequestWithDelay(100);
		LastFmRequest request = createGenericRequest();
		long startTime = System.currentTimeMillis();
		requestor.request(request);
		assertTimeIsWithinMax(startTime, 10);
	}
	
	@Test
	public void testSyncResponsesAfterFirstTakesDelayTime() throws InterruptedException, IOException {
		LastFmApiRequestor requestor = createMockedRequestWithDelay(100);
		Assert.assertEquals(100, requestor.requestSpacer.requestDelayMs);
		LastFmRequest requestFirst = createGenericRequest();
		LastFmRequest requestSecond = createGenericRequest();
		LastFmRequest requestThird = createGenericRequest();
		long startTime = System.currentTimeMillis();
		requestor.request(requestFirst);
		requestor.request(requestSecond);
		requestor.request(requestThird);
		assertTimeIsWithinMax(startTime, 220);
		assertTimeIsAboveMin(startTime, 199);
	}
	
	@Test
	public void testFirstAsyncRequestTakesNoTime() throws InterruptedException, IOException, ExecutionException {
		LastFmApiRequestor requestor = createMockedRequestWithDelay(100);
		LastFmRequest request = createGenericRequest();
		long startTime = System.currentTimeMillis();
		Future<String> future = requestor.asyncRequest(request);
		future.get();
		assertTimeIsWithinMax(startTime, 10);
	}
	
	@Test
	public void testAsyncResponsesAfterFirstTakesDelayTime() throws InterruptedException, IOException, ExecutionException {
		LastFmApiRequestor requestor = createMockedRequestWithDelay(100);
		Assert.assertEquals(100, requestor.requestSpacer.requestDelayMs);
		LastFmRequest requestFirst = createGenericRequest();
		LastFmRequest requestSecond = createGenericRequest();
		LastFmRequest requestThird = createGenericRequest();
		long startTime = System.currentTimeMillis();
		requestor.asyncRequest(requestFirst);
		requestor.asyncRequest(requestSecond);
		Future<String> future = requestor.asyncRequest(requestThird);
		future.get();
		assertTimeIsWithinMax(startTime, 220);
		assertTimeIsAboveMin(startTime, 199);
	}
}
