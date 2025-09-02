
package com.markokosic.minicrm.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String email;

}
