package com.esprit.tn.forum.mapper;

import com.esprit.tn.forum.dto.OfferDto;
import com.esprit.tn.forum.model.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source = "recruiter.userId", target = "recruiterId")
    @Mapping(source = "university.idUniversity", target = "universityId")
    OfferDto toDto(Offer offer);

    Offer toEntity(OfferDto offerDto);
}
