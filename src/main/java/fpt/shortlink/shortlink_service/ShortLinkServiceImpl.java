package fpt.shortlink.shortlink_service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShortLinkServiceImpl implements ShortLinkService{
    private final ShortLinkRepository shortLinkRepository;
    public final static String COLISION_RESOLVE_STRING = "fdaklfjakfjkajflkjakldfjakldjfkl";
    public static String hashMd5(String originalUrl){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(originalUrl.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<ShortLink> getShortLink(String shortCode) {
        return shortLinkRepository.getShortLink(shortCode);
    }

    @Override
    public Optional<ShortLink> createShortLink(ShortLinkDto shortLinkDto) {
        StringBuilder originalUrl = new StringBuilder(shortLinkDto.getOriginalUrl());
        String shortCode = null;
        do {
            if(shortCode != null){
                originalUrl.append(COLISION_RESOLVE_STRING);
            }
            shortCode = hashMd5(originalUrl.toString());
        } while (shortLinkRepository.getShortLink(shortCode).isPresent());
        ShortLink shortLink = ShortLinkMapper.INSTANCE.toShortLink(shortLinkDto);
        shortLink.setShortCode(shortCode);
        return shortLinkRepository.createShortLink(shortLink);
    }

    @Override
    public void updateShortLink(String shortCode, ShortLink shortLink) {
        shortLinkRepository.updateShortLink(shortCode,shortLink);
    }

    @Override
    public void deleteShortLink(String shortCode) {
        shortLinkRepository.deleteShortLink(shortCode);
    }
}
