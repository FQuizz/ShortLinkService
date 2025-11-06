package fpt.shortlink.shortlink_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@FeignClient(name = "shortlinks", url = "http://localhost:8084")
public interface ShortLinkRepository {
    @GetMapping("get-short-link/{shortCode}")
    Optional<ShortLink> getShortLink(@PathVariable String shortCode);
    @PostMapping("create-short-link")
    Optional<ShortLink> createShortLink(@RequestBody ShortLink shortLink);
    @PutMapping("update-short-link/{shortCode}")
    void updateShortLink(@PathVariable String shortCode, @RequestBody ShortLink shortLink);
    @DeleteMapping("delete-short-link/{shortCode}")
    void deleteShortLink(@PathVariable String shortCode);
}
