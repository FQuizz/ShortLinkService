package fpt.shortlink.shortlink_service;


import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public interface ShortLinkService {
    @GetMapping("/{shortCode}")
    Optional<ShortLink> getShortLink(@PathVariable String shortCode);
    @PostMapping("/")
    Optional<ShortLink> createShortLink(@RequestBody ShortLinkDto shortLink);
    @PutMapping("/{shortCode}")
    void updateShortLink(@PathVariable String shortCode, @RequestBody ShortLink shortLink);
    @DeleteMapping("/{shortCode}")
    void deleteShortLink(@PathVariable String shortCode);
}
