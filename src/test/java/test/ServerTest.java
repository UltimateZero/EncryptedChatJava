package test;

import com.aast.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
		boolean result = false;
		try {
			result = server.startListening();
		} catch (Exception e) {
			try {
				server = new Server(4456);
				server.startListening();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		assertEquals(true, result);
	}


}