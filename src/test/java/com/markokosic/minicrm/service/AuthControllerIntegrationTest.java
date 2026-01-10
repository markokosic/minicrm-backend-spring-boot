package com.markokosic.minicrm.service;


import com.markokosic.minicrm.dto.response.RegisterTenantResponseDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "/application-test.properties")
public class AuthControllerIntegrationTest {

	private TestRestTemplate testRestTemplate;

	@Test
	void testRegister_whenValidDetailsProvided_shouldReturnTenantDetails() throws JSONException {

		JSONObject registerTenantDetailsRequestJson = new JSONObject();
		registerTenantDetailsRequestJson.put("tenantName", "testTenant");
		registerTenantDetailsRequestJson.put("password", "testPassword");
		registerTenantDetailsRequestJson.put("firstName", "Max");
		registerTenantDetailsRequestJson.put("lastName", "Mustermann");
		registerTenantDetailsRequestJson.put("email", "test@email.com");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		HttpEntity<String> request = new HttpEntity<>(registerTenantDetailsRequestJson.toString(), headers);

		//ACT
		ResponseEntity<RegisterTenantResponseDTO> createdTenantDetailsEntity =  this.testRestTemplate.postForEntity("/auth/register",
				registerTenantDetailsRequestJson,
				RegisterTenantResponseDTO.class);

		RegisterTenantResponseDTO createdTenantDetails = createdTenantDetailsEntity.getBody();

		//Assert
		Assertions.assertEquals(HttpStatus.OK, createdTenantDetailsEntity.getStatusCode());
		Assertions.assertNotNull(createdTenantDetails);
		Assertions.assertEquals(registerTenantDetailsRequestJson.getString("tenantName"), createdTenantDetails.getTenantName(), "Returned tenants name seems to be incorrect");



	}


}
