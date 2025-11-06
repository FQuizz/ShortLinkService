package fpt.shortlink.shortlink_service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShortLinkMapper {
    ShortLinkMapper INSTANCE = Mappers.getMapper(ShortLinkMapper.class);
    ShortLink toShortLink(ShortLinkDto shortLinkDto);
}
