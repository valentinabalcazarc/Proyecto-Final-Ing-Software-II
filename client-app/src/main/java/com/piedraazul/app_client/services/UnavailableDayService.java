package com.piedraazul.app_client.services;

import com.piedraazul.app_client.dto.UnavailableDayDTO;
import java.util.List;

public interface UnavailableDayService {
    boolean create(UnavailableDayDTO dto);
    List<UnavailableDayDTO> getByProfessional(Long codProf);
    boolean delete(Long id);
}