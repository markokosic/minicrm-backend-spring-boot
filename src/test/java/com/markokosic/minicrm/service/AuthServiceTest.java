package com.markokosic.minicrm.service;

import com.markokosic.minicrm.dto.request.LoginRequestDTO;
import com.markokosic.minicrm.dto.request.RegisterTenantRequestDTO;
import com.markokosic.minicrm.dto.response.AuthResponseDTO;
import com.markokosic.minicrm.dto.response.RegisterTenantResponseDTO;
import com.markokosic.minicrm.model.Tenant;
import com.markokosic.minicrm.model.User;
import com.markokosic.minicrm.repository.TenantRepository;
import com.markokosic.minicrm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private TenantRepository tenantRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JWTService jwtService;

	@InjectMocks
	private AuthService authService;


	@Test
	void register_newTenant_shouldReturnRegisterTenantResponse() {
		RegisterTenantRequestDTO request = new RegisterTenantRequestDTO();
		request.setTenantName("Test Tenant");
		request.setFirstName("Test First Name");
		request.setLastName("Test Last Name");
		request.setEmail("test@test.com");
		request.setPassword("Password123");

		Tenant savedTenant = new Tenant();
		savedTenant.setId(1L);
		savedTenant.setName(request.getTenantName());


		when(tenantRepository.save(any(Tenant.class))).thenReturn(savedTenant);
		when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

		RegisterTenantResponseDTO response = authService.registerNewTenant(request);

		assertEquals(1L, response.getTenantId());
		assertEquals("Test Tenant", response.getTenantName());

		verify(userRepository, times(1)).save(argThat(user ->
				user.getEmail().equals(request.getEmail()) &&
						user.getTenantId().equals(savedTenant.getId()) &&
						user.getFirstName().equals(request.getFirstName()) &&
						user.getLastName().equals(request.getLastName()) &&
						user.getPassword().equals("encodedPassword")
		));


		verify(tenantRepository, times(1)).save(any(Tenant.class));
	}





	@Test
	void login_withValidCredentials_shouldReturnAuthResponse() {
		LoginRequestDTO request = new LoginRequestDTO();
		request.setEmail("test@example.com");
		request.setPassword("password");

		Authentication authMock = mock(Authentication.class);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authMock);

		User user = new User();
		user.setFirstName("Max");
		user.setLastName("Mustermann");
		user.setEmail("test@example.com");
		user.setTenantId(1L);
		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		when(jwtService.generateToken("test@example.com", 1L)).thenReturn("fake-jwt-token");

		AuthResponseDTO response = authService.login(request);

		assertNotNull(response);
		assertEquals("fake-jwt-token", response.getAccessToken());
		assertEquals("Max", response.getUser().getFirstName());
		assertEquals("Mustermann", response.getUser().getLastName());
		assertEquals("test@example.com", response.getUser().getEmail());
	}

//	@Test
//	void login_withInvalidCredentials_shouldThrowBadCredentialsException() {
//		LoginRequestDTO request = new LoginRequestDTO();
//		request.setEmail("wrong@example.com");
//		request.setPassword("wrongpass");
//
//		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//				.thenThrow(new BadCredentialsException("bad credentials"));
//
//		assertThrows(BadCredentialsException.class, () -> authService.login(request));
//	}
//
//	@Test
//	void login_userNotFound_shouldThrowNullPointer() {
//		LoginRequestDTO request = new LoginRequestDTO();
//		request.setEmail("notfound@example.com");
//		request.setPassword("password");
//
//		Authentication authMock = mock(Authentication.class);
//		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//				.thenReturn(authMock);
//
//		when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);
//
//		assertThrows(NullPointerException.class, () -> authService.login(request));
//	}
};
