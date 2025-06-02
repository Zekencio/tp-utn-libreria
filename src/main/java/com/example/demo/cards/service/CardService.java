package com.example.demo.cards.service;

import com.example.demo.author.dto.AuthorDTO;
import com.example.demo.author.dto.CreateAuthorDTO;
import com.example.demo.author.dto.UpdateAuthorDTO;
import com.example.demo.author.model.Author;
import com.example.demo.cards.dto.CardDTO;
import com.example.demo.cards.dto.CreateCardDTO;
import com.example.demo.cards.dto.UpdateCardDTO;
import com.example.demo.cards.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<CardDTO> getById(Long id);
    List<CardDTO> getAll();
    CardDTO createCard(CreateCardDTO createCardDTO);
    Optional<CardDTO> updateCard (Long id, UpdateCardDTO updateCardDTO);
    boolean delteCard(Long id);

    Card convertToEntity(CreateCardDTO createCardDTO);
    CardDTO convertToDTO(Card card);
}
