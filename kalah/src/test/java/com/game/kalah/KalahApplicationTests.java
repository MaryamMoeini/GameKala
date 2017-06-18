package com.game.kalah;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KalahApplicationTests {

	@Autowired
	WebApplicationContext context;

	@Autowired
	ObjectMapper mapper;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private static final String URL_PREFIX = "http://localhost:8080";
	private MockMvc moc;
	@Before
	public void setup() {
		this.moc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void contextLoads() throws JsonProcessingException {
		
		int[] player1 = {6,6,6,6,6,6,0};
		int[] player2 = {0,6,6,6,6,6,6};
		String player = "pl1";
		int position = 0;
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URL_PREFIX + "/move")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(player1))
				.content(mapper.writeValueAsString(player2))
				.content(mapper.writeValueAsString(player))
				.content(mapper.writeValueAsString(position));
		
		
		}
	}

