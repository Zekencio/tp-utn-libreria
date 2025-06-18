package com.example.demo.sellerprofile.service;

import com.example.demo.configuration.CurrentUserUtils;
import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.sellerprofile.dto.CreateSellerProfileDTO;
import com.example.demo.sellerprofile.dto.SellerProfileDTO;
import com.example.demo.sellerprofile.dto.UpdateSellerProfileDTO;
import com.example.demo.sellerprofile.model.SellerProfile;
import com.example.demo.sellerprofile.repository.SellerProfileRepository;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerProfileServiceImpl implements SellerProfileService{

    private final SellerProfileRepository repository;
    private final UserServiceImpl userService;

    public SellerProfileServiceImpl(SellerProfileRepository repository, UserServiceImpl userService) {
        this.repository = repository;
        this.userService = userService;
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
    public boolean deleteSellerProfile() throws NotFoundException {
        User user = userService.getCurrentUser();
        Optional<SellerProfile> seller = Optional.ofNullable(user.getSellerProfile());
        if (seller.isPresent()){
            repository.deleteById(seller.get().getId());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public SellerProfileDTO updateSellerProfile(UpdateSellerProfileDTO updateSellerProfileDTO) throws NotFoundException {
        User user = userService.getCurrentUser();
        Optional<SellerProfile> profile = Optional.ofNullable(user.getSellerProfile());
        if (profile.isEmpty()){
            throw new NotFoundException("No hay un perfil de vendedor registrado para tu usuario");
        }else {
            return profile.map(existing -> {
                if (updateSellerProfileDTO.getName() != null) {
                    existing.setName(updateSellerProfileDTO.getName());
                }
                if (updateSellerProfileDTO.getAddress() != null) {
                    existing.setAddress(updateSellerProfileDTO.getAddress());
                }
                if (updateSellerProfileDTO.getSellerUser() != null) {
                    existing.setSellerUser(updateSellerProfileDTO.getSellerUser());
                }
                SellerProfile saved = repository.save(existing);
                return convertToDTO(saved);
            }).get();
        }
    }

    @Override
    public SellerProfileDTO createSellerProfile(CreateSellerProfileDTO createSellerProfileDTO) throws AlreadyExistingException, NotFoundException {
        SellerProfile newSeller = convertToEntity(createSellerProfileDTO);
        User user =userService.getCurrentUser();
        if(user.getSellerProfile() != null){
            throw new AlreadyExistingException("El usuario ya esta registrado como vendedor");
        }
        newSeller.setSellerUser(user);
        if (repository.findAll().contains(newSeller)){
            throw new AlreadyExistingException("El vendedor ya esta registrado");
        }
        SellerProfile savedSeller = repository.save(newSeller);
        return convertToDTO(savedSeller);
    }

    @Override
    public SellerProfileDTO convertToDTO(SellerProfile sellerProfile) {
        return new SellerProfileDTO(sellerProfile.getId(), sellerProfile.getName(), sellerProfile.getAddress());
    }

    @Override
    public SellerProfile convertToEntity(CreateSellerProfileDTO createSellerProfileDTO) {
        return new SellerProfile(createSellerProfileDTO.getName(), createSellerProfileDTO.getAddress(), createSellerProfileDTO.getSellerUser());
    }
}
