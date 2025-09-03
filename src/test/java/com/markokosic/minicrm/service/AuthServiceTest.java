//package com.markokosic.minicrm.service;
//
//import com.markokosic.minicrm.dto.request.LoginRequestDTO;
//import com.markokosic.minicrm.dto.response.AuthResponseDTO;
//import com.markokosic.minicrm.exception.BadCredentialsException;
//import com.markokosic.minicrm.model.User;
//import com.markokosic.minicrm.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class AuthServiceTest {
//
//	@Mock
//	private UserRepository userRepository;
//
//	@Mock
//	private AuthenticationManager authenticationManager;
//
//	@Mock
//	private JWTService jwtService;
//
//	private AuthService authService;
//
//	@BeforeEach
////	void setUp() {
////		MockitoAnnotations.openMocks(this);
////		authService = new AuthService(null, userRepository, null, null);
////		authService.jwtService = jwtService;
////		authService.authenticationManager = authenticationManager;
//	}
//
//	@Test
//	void login_withValidCredentials_shouldReturnAuthResponse() {
//		LoginRequestDTO request = new LoginRequestDTO();
//		request.setEmail("test@example.com");
//		request.setPassword("password");
//
//		Authentication authMock = mock(Authentication.class);
//		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//				.thenReturn(authMock);
//
//		User user = new User();
//		user.setFirstName("Max");
//		user.setLastName("Mustermann");
//		user.setEmail("test@example.com");
//		when(userRepository.findByEmail("test@example.com")).thenReturn(user);
//
//		when(jwtService.generateToken("test@example.com", "3")).thenReturn("fake-jwt-token");
//
//		AuthResponseDTO response = authService.login(request);
//
//		assertNotNull(response);
//		assertEquals("fake-jwt-token", response.getAccessToken());
//		assertEquals("Max", response.getUser().getFirstName());
//		assertEquals("Mustermann", response.getUser().getLastName());
//		assertEquals("test@example.com", response.getUser().getEmail());
//	}
//
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
//}
