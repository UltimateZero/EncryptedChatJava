package test;

import com.aast.Server;
import org.junit.After;
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
		try {
			server = new Server(54543);
			System.out.println("Listening on port "+ 54543);
		} catch (Exception e){
			server = new Server(54545);
			System.out.println("Listening on alt port "+ 54545);
		}
	}

	@After
	public void tearDown() {
		System.out.println("Tearing down...");
		server.stop();
	}

	@Test
	public void testJUnit() {
		String str = "Junit is working fine";
		assertEquals("Junit is working fine",str);
	}

	@Test
	public void startListeningTest() {
		boolean result = server.startListening();
		assertEquals(true, result);
	}


}