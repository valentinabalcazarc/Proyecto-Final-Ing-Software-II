package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.UnavailableDayDTO;
import com.piedraazul.people_service.model.UnavailableDay;

import java.util.List;

public interface UnavailableDayService {
    UnavailableDay create(UnavailableDayDTO dto);
    List<UnavailableDay> findByProfessional(Long codProf);
    boolean delete(Long id);
}