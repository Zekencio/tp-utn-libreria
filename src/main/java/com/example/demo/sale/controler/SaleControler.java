package com.example.demo.sale.controler;

import com.example.demo.sale.dto.CreateSaleDTO;
import com.example.demo.sale.dto.SaleDTO;
import com.example.demo.sale.dto.UpdateSaleDTO;
import com.example.demo.sale.service.SaleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleControler {

    private final SaleServiceImpl saleService;

    public SaleControler(SaleServiceImpl saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales () {
        List<SaleDTO> sales = saleService.getAll();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSaleById (@PathVariable Long id) {
        Optional<SaleDTO> sale = saleService.getById(id);
        return sale.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody CreateSaleDTO createSaleDTO){
        SaleDTO saleDTO = saleService.createSale(createSaleDTO);
        return new ResponseEntity<>(saleDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> updateSale(@PathVariable Long id, @RequestBody UpdateSaleDTO updateSaleDTO){
        Optional<SaleDTO> updatedSale = saleService.updateSale(id, updateSaleDTO);
        return updatedSale.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        boolean deleted = saleService.delteSale(id);
        if (deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
