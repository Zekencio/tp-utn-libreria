package com.example.demo.sellerprofile.service;

import com.example.demo.sellerprofile.dto.CreateSellerProfileDTO;
import com.example.demo.sellerprofile.dto.SellerProfileDTO;
import com.example.demo.sellerprofile.dto.UpdateSellerProfileDTO;
import com.example.demo.sellerprofile.model.SellerProfile;
import com.example.demo.sellerprofile.repository.SellerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerProfileService{

    private final SellerProfileRepository repository;

    public SellerServiceImpl(SellerProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SellerProfileDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<SellerProfileDTO> getById(Long id) {
        Optional<SellerProfile> seller = repository.findById(id);
        return seller.map(this::convertToDTO);
    }

    @Override
    public boolean delteSellerProfile(Long id) {
        Optional<SellerProfile> seller = repository.findById(id);
        if (seller.isPresent()){
            return false;
        }else {
            repository.deleteById(id);
            return true;
        }
    }

    @Override
    public Optional<SellerProfileDTO> updateSellerProfile(Long id, UpdateSellerProfileDTO updateSellerProfileDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateSellerProfileDTO.getName() != null){
                        existing.setName(updateSellerProfileDTO.getName());
                    }
                    if (updateSellerProfileDTO.getAddress() != null){
                        existing.setAddress(updateSellerProfileDTO.getAddress());
                    }
                    if (updateSellerProfileDTO.getSellerUser() != null){
                        existing.setSellerUser(updateSellerProfileDTO.getSellerUser());
                    }
                    SellerProfile saved = repository.save(existing);
                    return convertToDTO(saved);
                });
    }

    @Override
    public SellerProfileDTO createSellerProfile(CreateSellerProfileDTO createSellerProfileDTO) {
        SellerProfile newSeller = convertToEntity(createSellerProfileDTO);
        SellerProfile savedSeller = repository.save(newSeller);
        return convertToDTO(savedSeller);
    }

    @Override
    public SellerProfileDTO convertToDTO(SellerProfile sellerProfile) {
        return new SellerProfileDTO(sellerProfile.getId(), sellerProfile.getName(), sellerProfile.getAddress(), sellerProfile.getSellerUser());
    }

    @Override
    public SellerProfile convertToEntity(CreateSellerProfileDTO createSellerProfileDTO) {
        return new SellerProfile(createSellerProfileDTO.getName(), createSellerProfileDTO.getAddress(), createSellerProfileDTO.getSellerUser());
    }
}
