package vallegrande.edu.pe.losQueensAgro.service;

import vallegrande.edu.pe.losQueensAgro.dto.SaleOrderRequest;
import vallegrande.edu.pe.losQueensAgro.model.SaleOrder;

import java.util.List;

public interface SaleOrderService {

    List<SaleOrder> findAll();

    SaleOrder registerSale(SaleOrderRequest request);
}