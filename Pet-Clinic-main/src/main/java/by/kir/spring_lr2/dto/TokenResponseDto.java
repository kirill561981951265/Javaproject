package by.kir.spring_lr2.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {
    private String username;
    private String role;
    private String token;
    private String error;
}