package com.Purrrfect.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {
    private String email;     // ✅ correct field name
    private String password;  // ✅ correct field name
}
