package com.markokosic.minicrm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshAccessTokenResponseDTO {
	private String accessToken;
}
