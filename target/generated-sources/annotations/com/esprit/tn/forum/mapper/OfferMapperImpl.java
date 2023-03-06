package com.esprit.tn.forum.mapper;

import com.esprit.tn.forum.dto.OfferDto;
import com.esprit.tn.forum.model.Offer;
import com.esprit.tn.forum.model.University;
import com.esprit.tn.forum.model.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-06T22:30:39+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class OfferMapperImpl implements OfferMapper {

    @Override
    public OfferDto toDto(Offer offer) {
        if ( offer == null ) {
            return null;
        }

        OfferDto offerDto = new OfferDto();

        offerDto.setRecruiterId( offerRecruiterUserId( offer ) );
        offerDto.setUniversityId( offerUniversityIdUniversity( offer ) );
        offerDto.setIdOffer( offer.getIdOffer() );
        offerDto.setTitle( offer.getTitle() );
        offerDto.setDescription( offer.getDescription() );
        offerDto.setScoreOffer( offer.getScoreOffer() );
        offerDto.setDateCreation( offer.getDateCreation() );
        offerDto.setNbrPlaceDisponible( offer.getNbrPlaceDisponible() );
        offerDto.setDateExpiration( offer.getDateExpiration() );
        offerDto.setTypeOffer( offer.getTypeOffer() );
        offerDto.setApplied( offer.isApplied() );

        return offerDto;
    }

    @Override
    public Offer toEntity(OfferDto offerDto) {
        if ( offerDto == null ) {
            return null;
        }

        Offer offer = new Offer();

        offer.setIdOffer( offerDto.getIdOffer() );
        offer.setTitle( offerDto.getTitle() );
        offer.setDescription( offerDto.getDescription() );
        offer.setScoreOffer( offerDto.getScoreOffer() );
        offer.setDateCreation( offerDto.getDateCreation() );
        offer.setNbrPlaceDisponible( offerDto.getNbrPlaceDisponible() );
        offer.setDateExpiration( offerDto.getDateExpiration() );
        offer.setTypeOffer( offerDto.getTypeOffer() );
        offer.setApplied( offerDto.isApplied() );

        return offer;
    }

    private Long offerRecruiterUserId(Offer offer) {
        if ( offer == null ) {
            return null;
        }
        User recruiter = offer.getRecruiter();
        if ( recruiter == null ) {
            return null;
        }
        Long userId = recruiter.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private Long offerUniversityIdUniversity(Offer offer) {
        if ( offer == null ) {
            return null;
        }
        University university = offer.getUniversity();
        if ( university == null ) {
            return null;
        }
        Long idUniversity = university.getIdUniversity();
        if ( idUniversity == null ) {
            return null;
        }
        return idUniversity;
    }
}
