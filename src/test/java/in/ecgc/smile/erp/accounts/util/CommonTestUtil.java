package in.ecgc.smile.erp.accounts.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CommonTestUtil {
	private CommonTestUtil() {
	}
	public static String convertToJSON(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		/*
		 * To configure Jackson to map a LocalDate into a String like 1982-06-23 The
		 * module teaches the ObjectMapper how to work with LocalDates and the parameter
		 * WRITE_DATES_AS_TIMESTAMPS tells the mapper to represent a Date as a String in
		 * JSON.
		 */
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		return mapper.writeValueAsString(object);
	}
}
