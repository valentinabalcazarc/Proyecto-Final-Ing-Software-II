package com.piedraazul.app_client.services;

import com.piedraazul.app_client.dto.UnavailableDayDTO;

public interface UnavailableDayService {
    boolean create(UnavailableDayDTO dto);
}