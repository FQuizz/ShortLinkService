package fpt.shortlink.shortlink_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.OptionalInt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCreateShortLink {
    @Mock
    ShortLinkRepository shortLinkRepository;
    @InjectMocks
    ShortLinkServiceImpl shortLinkService;

    @Test
    void testCreateShortLinkSuccessfully(){
        String originalUrl = "http://localhost/this/is/very/long/url";
        String createBy = "me";
        when(shortLinkRepository.getShortLink(anyString())).thenReturn(Optional.empty());
        when(shortLinkRepository.createShortLink(any(ShortLink.class))).thenReturn(Optional.of(
            ShortLink.builder()
                .shortCode(ShortLinkServiceImpl.hashMd5(originalUrl))
                .originalUrl(originalUrl)
                .createBy(createBy)
                .build()
        ));
        Optional<ShortLink> shortLink = shortLinkService.createShortLink(ShortLinkDto.builder()
                .originalUrl(originalUrl)
                .createBy(createBy)
            .build());

        assertThat(shortLink).isPresent();
        assertThat(shortLink.get().getShortCode()).isEqualTo(ShortLinkServiceImpl.hashMd5(originalUrl));
        assertThat(shortLink.get().getCreateBy()).isEqualTo(createBy);
        verify(shortLinkRepository,times(1)).getShortLink(anyString());
    }

    @Test
    void testCreateShortLinkSuccessfullyWithCollision(){
        String originalUrl = "http://localhost/this/is/very/long/url";
        String createBy = "me";
        String collisionString = ShortLinkServiceImpl.COLISION_RESOLVE_STRING;
        when(shortLinkRepository.getShortLink(anyString()))
            .thenReturn(Optional.of(new ShortLink()))
            .thenReturn(Optional.of(new ShortLink()))
            .thenReturn(Optional.empty());
        when(shortLinkRepository.createShortLink(any(ShortLink.class))).thenReturn(Optional.of(
            ShortLink.builder()
                .shortCode(ShortLinkServiceImpl.hashMd5(originalUrl + collisionString + collisionString))
                .originalUrl(originalUrl)
                .createBy(createBy)
                .build()
        ));
        Optional<ShortLink> shortLink = shortLinkService.createShortLink(ShortLinkDto.builder()
            .originalUrl(originalUrl)
            .createBy(createBy)
            .build());

        assertThat(shortLink).isPresent();
        assertThat(shortLink.get().getShortCode()).isEqualTo(ShortLinkServiceImpl.hashMd5(originalUrl + collisionString + collisionString ));
        assertThat(shortLink.get().getCreateBy()).isEqualTo(createBy);
        verify(shortLinkRepository,times(3)).getShortLink(anyString());
    }


}
