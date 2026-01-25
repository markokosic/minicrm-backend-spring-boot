package com.markokosic.minicrm.modules.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.token")
public class TokenProperties {
	private final Token access = new Token();
	private final Token refresh = new Token();

	TokenProperties() {}

	public static class Token {
		private long expirationMinutes;
		public long getExpirationMinutes(){
			return expirationMinutes;
		}
		public void setExpirationMinutes(long expirationMinutes){
			this.expirationMinutes = expirationMinutes;
		}
	}

	public Token getAccess() { return access; }
	public Token getRefresh() { return refresh; }

}
