package com.example.demo.sale.service;

import com.example.demo.genre.dto.CreateGenreDTO;
import com.example.demo.genre.dto.GenreDTO;
import com.example.demo.genre.dto.UpdateGenreDTO;
import com.example.demo.genre.model.Genre;
import com.example.demo.sale.dto.CreateSaleDTO;
import com.example.demo.sale.dto.SaleDTO;
import com.example.demo.sale.dto.UpdateSaleDTO;
import com.example.demo.sale.model.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    Optional<SaleDTO> getById(Long id);
    List<SaleDTO> getAll();
    SaleDTO createSale(CreateSaleDTO createSaleDTO);
    Optional<SaleDTO> updateSale (Long id, UpdateSaleDTO updateSaleDTO);
    boolean delteSale(Long id);

    Sale convertToEntity(CreateSaleDTO createSaleDTO);
    SaleDTO convertToDTO(Sale sale);
}
