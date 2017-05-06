package test;

import com.aast.Server;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by UltimateZero on 5/6/2017.
 */
public class ServerTest {
	Server server;
	@Before
	public void setUp() throws Exception {
		server = new Server();
	}

	@Test
	public void testAdd() {
		String str = "Junit is working fine";
		assertEquals("Junit is working fine",str);
	}

	@Test
	public void startListeningTest() {
		boolean result = server.startListening();
		assertEquals(true, result);
	}


}