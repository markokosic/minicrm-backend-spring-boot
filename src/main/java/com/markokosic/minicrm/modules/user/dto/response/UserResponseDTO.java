
package com.markokosic.minicrm.modules.user.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
