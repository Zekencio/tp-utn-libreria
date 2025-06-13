package com.example.demo.cards.controler;

import com.example.demo.cards.dto.CardDTO;
import com.example.demo.cards.dto.CreateCardDTO;
import com.example.demo.cards.dto.UpdateCardDTO;
import com.example.demo.cards.service.CardServiceImpl;
import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardsControler {

    private final CardServiceImpl cardService;

    public CardsControler(CardServiceImpl cardService) {
        this.cardService = cardService;
    }

    //solo podes modificar, ver o borrar las tarjetas que correspondan al usuario actual

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllcards () {
        List<CardDTO> cards = cardService.getAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById (@PathVariable Long id) {
        Optional<CardDTO> card = cardService.getById(id);
        return card.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CreateCardDTO createCardDTO){
        try {
            CardDTO cardDTO = cardService.createCard(createCardDTO);
            return new ResponseEntity<>(cardDTO, HttpStatus.CREATED);
        } catch (AlreadyExistingException | NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable Long id, @RequestBody UpdateCardDTO updateCardDTO){
        Optional<CardDTO> updatedCard = cardService.updateCard(id, updateCardDTO);
        return updatedCard.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id){
        boolean deleted = cardService.deleteCard(id);
        if (deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
