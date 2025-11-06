package fpt.shortlink.shortlink_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShortLinkDto {
    private String originalUrl;
    private String createBy;
}
