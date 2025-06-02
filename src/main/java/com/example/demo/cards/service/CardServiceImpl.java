package com.example.demo.cards.service;

import com.example.demo.author.model.Author;
import com.example.demo.cards.dto.CardDTO;
import com.example.demo.cards.dto.CreateCardDTO;
import com.example.demo.cards.dto.UpdateCardDTO;
import com.example.demo.cards.model.Card;
import com.example.demo.cards.repository.CardsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService{

    private final CardsRepository repository;

    public CardServiceImpl(CardsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CardDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CardDTO> getById(Long id) {
        Optional<Card> card = repository.findById(id);
        return card.map(this::convertToDTO);
    }

    @Override
    public CardDTO createCard(CreateCardDTO createCardDTO) {
        Card newCard = convertToEntity(createCardDTO);
        Card savedCard = repository.save(newCard);
        return convertToDTO(savedCard);
    }

    @Override
    public boolean delteCard(Long id) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()){
            return false;
        }else {
            repository.deleteById(id);
            return true;
        }
    }

    @Override
    public Optional<CardDTO> updateCard(Long id, UpdateCardDTO updateCardDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateCardDTO.getCardNumber() != null){
                        existing.setCardNumber(updateCardDTO.getCardNumber());
                    }
                    if (updateCardDTO.getBank() != null){
                        existing.setBank(updateCardDTO.getBank());
                    }
                    if (updateCardDTO.getCvv() != null){
                        existing.setCvv(updateCardDTO.getCvv());
                    }
                    if (updateCardDTO.getOwner() != null){
                        existing.setOwner(updateCardDTO.getOwner());
                    }
                    Card saved = repository.save(existing);
                    return convertToDTO(saved);
                });
    }

    @Override
    public Card convertToEntity(CreateCardDTO createCardDTO) {
        return new Card(createCardDTO.getCardNumber(),createCardDTO.getBank(), createCardDTO.getCvv(),createCardDTO.getOwner());
    }

    @Override
    public CardDTO convertToDTO(Card card) {
        return new CardDTO(card.getId(),card.getCardNumber(),card.getBank(),card.getCvv(),card.getOwner());
    }
}
