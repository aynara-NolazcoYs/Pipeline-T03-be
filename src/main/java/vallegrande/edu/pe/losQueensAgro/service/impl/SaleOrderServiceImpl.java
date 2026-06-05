package vallegrande.edu.pe.losQueensAgro.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.losQueensAgro.dto.SaleItemRequest;
import vallegrande.edu.pe.losQueensAgro.dto.SaleOrderRequest;
import vallegrande.edu.pe.losQueensAgro.model.OrderDetail;
import vallegrande.edu.pe.losQueensAgro.model.SaleOrder;
import vallegrande.edu.pe.losQueensAgro.model.product;
import vallegrande.edu.pe.losQueensAgro.repository.OrderDetailRepository;
import vallegrande.edu.pe.losQueensAgro.repository.SaleOrderRepository;
import vallegrande.edu.pe.losQueensAgro.service.ProductService;
import vallegrande.edu.pe.losQueensAgro.service.SaleOrderService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {

    private final SaleOrderRepository saleOrderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;

    public SaleOrderServiceImpl(SaleOrderRepository saleOrderRepository,
                                OrderDetailRepository orderDetailRepository,
                                ProductService productService) {
        this.saleOrderRepository = saleOrderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
    }

    @Override
    public List<SaleOrder> findAll() {
        return saleOrderRepository.findAll();
    }

    @Override
    @Transactional
    public SaleOrder registerSale(SaleOrderRequest request) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        double total = 0.0;

        for (SaleItemRequest item : request.getItems()) {
            product soldProduct = productService.decreaseStock(item.getProductId(), item.getQuantity());
            double subTotal = soldProduct.getUnit_price() * item.getQuantity();
            total += subTotal;

            orderDetails.add(OrderDetail.builder()
                    .productId(soldProduct.getId())
                    .quantity(item.getQuantity())
                    .subTotal(subTotal)
                    .build());
        }

        SaleOrder saleOrder = SaleOrder.builder()
                .personId(request.getPersonId())
                .saleOrderDate(LocalDateTime.now(ZoneId.of("America/Lima")))
                .status("P")
                .total(total)
                .deliveryType(request.getDeliveryType())
                .paymentMethod(request.getPaymentMethod())
                .notes(request.getNotes())
                .warehouseId(request.getWarehouseId())
                .build();

        SaleOrder savedSaleOrder = saleOrderRepository.save(saleOrder);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setSaleOrderId(savedSaleOrder.getId());
        }

        orderDetailRepository.saveAll(orderDetails);

        return savedSaleOrder;
    }
}