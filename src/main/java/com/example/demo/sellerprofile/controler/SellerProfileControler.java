package com.example.demo.sellerprofile.controler;

import com.example.demo.sellerprofile.dto.CreateSellerProfileDTO;
import com.example.demo.sellerprofile.dto.SellerProfileDTO;
import com.example.demo.sellerprofile.dto.UpdateSellerProfileDTO;
import com.example.demo.sellerprofile.service.SellerProfileServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellerProfiles")
public class SellerProfileControler {

    private final SellerProfileServiceImpl sellerProfileService;

    public SellerProfileControler(SellerProfileServiceImpl sellerProfileService) {
        this.sellerProfileService = sellerProfileService;
    }
     // modificar la muestra de libros para evitar una muestra infinita
    @GetMapping
    public ResponseEntity<List<SellerProfileDTO>> getAllSellers () {
        List<SellerProfileDTO> sellers = sellerProfileService.getAll();
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerProfileDTO> getSellerById (@PathVariable Long id) {
        Optional<SellerProfileDTO> seller = sellerProfileService.getById(id);
        return seller.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // la relacion con el usuario deberia ser automatica
    @PostMapping
    public ResponseEntity<SellerProfileDTO> createSellerProfile(@RequestBody CreateSellerProfileDTO createSellerProfileDTO){
        SellerProfileDTO sellerDTO = sellerProfileService.createSellerProfile(createSellerProfileDTO);
        return new ResponseEntity<>(sellerDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerProfileDTO> updateSellerProfile(@PathVariable Long id, @RequestBody UpdateSellerProfileDTO updateSellerProfileDTO){
        Optional<SellerProfileDTO> updatedBook = sellerProfileService.updateSellerProfile(id, updateSellerProfileDTO);
        return updatedBook.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        boolean deleted = sellerProfileService.deleteSellerProfile(id);
        if (deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
