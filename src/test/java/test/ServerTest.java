package test;

import com.aast.Server;
import com.aast.exceptions.IdException;
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
		server = new Server(1);
	}
	@Test
	public void getId() throws Exception {
		assertEquals(1, server.getId());
	}

	@Test
	public void setId() throws Exception {
		server.setId(3);
		assertEquals(3, server.getId());
	}

	@Test(expected = IdException.class)
	public void setInvalidId() throws Exception {
		server.setId(-1);
	}
}