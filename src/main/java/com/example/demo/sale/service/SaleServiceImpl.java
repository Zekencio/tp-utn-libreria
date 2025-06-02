package com.example.demo.sale.service;

import com.example.demo.sale.dto.CreateSaleDTO;
import com.example.demo.sale.dto.SaleDTO;
import com.example.demo.sale.dto.UpdateSaleDTO;
import com.example.demo.sale.model.Sale;
import com.example.demo.sale.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService{

    private final SaleRepository repository;

    public SaleServiceImpl(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SaleDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<SaleDTO> getById(Long id) {
        Optional<Sale> sale = repository.findById(id);
        return sale.map(this::convertToDTO);
    }

    @Override
    public SaleDTO createSale(CreateSaleDTO createSaleDTO) {
        Sale newSale = convertToEntity(createSaleDTO);
        Sale savedSale = repository.save(newSale);
        return convertToDTO(savedSale);
    }

    @Override
    public boolean delteSale(Long id) {
        Optional<Sale> sale = repository.findById(id);
        if (sale.isPresent()){
            return false;
        }else {
            repository.deleteById(id);
            return true;
        }
    }

    @Override
    public Optional<SaleDTO> updateSale(Long id, UpdateSaleDTO updateSaleDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateSaleDTO.getDate() != null){
                        existing.setDate(updateSaleDTO.getDate());
                    }
                    if (updateSaleDTO.getUser() != null){
                        existing.setUser(updateSaleDTO.getUser());
                    }
                    if (updateSaleDTO.getCard() != null){
                        existing.setCard(updateSaleDTO.getCard());
                    }
                    if (updateSaleDTO.getBooks() != null){
                        existing.setBooks(updateSaleDTO.getBooks());
                    }
                    Sale sale = repository.save(existing);
                    return convertToDTO(sale);
                });
    }

    @Override
    public Sale convertToEntity(CreateSaleDTO createSaleDTO) {
        return new Sale(createSaleDTO.getDate(),createSaleDTO.getUser(),createSaleDTO.getCard(),createSaleDTO.getBooks());
    }

    @Override
    public SaleDTO convertToDTO(Sale sale) {
        return new SaleDTO(sale.getId(),sale.getDate(),sale.getUser(),sale.getCard(),sale.getBooks());
    }
}
