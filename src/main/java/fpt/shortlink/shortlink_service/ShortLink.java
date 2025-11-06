package fpt.shortlink.shortlink_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortLink {
    private String id;
    private String shortCode;
    private String originalUrl;
    private LocalDateTime createAt;
    private String createBy;
    private Long clickCount;
    private Boolean isActive;
}
