package com.example.demo.sellerprofile.service;


import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.sellerprofile.dto.CreateSellerProfileDTO;
import com.example.demo.sellerprofile.dto.SellerProfileDTO;
import com.example.demo.sellerprofile.dto.UpdateSellerProfileDTO;
import com.example.demo.sellerprofile.model.SellerProfile;

import java.util.List;
import java.util.Optional;

public interface SellerProfileService {
    Optional<SellerProfileDTO> getById(Long id);
    List<SellerProfileDTO> getAll();
    SellerProfileDTO createSellerProfile(CreateSellerProfileDTO createSellerProfileDTO) throws AlreadyExistingException, NotFoundException;
    Optional<SellerProfileDTO> updateSellerProfile (Long id, UpdateSellerProfileDTO updateSellerProfileDTO);
    boolean deleteSellerProfile(Long id);

    SellerProfile convertToEntity(CreateSellerProfileDTO createSellerProfileDTO);
    SellerProfileDTO convertToDTO(SellerProfile sellerProfile);
}
