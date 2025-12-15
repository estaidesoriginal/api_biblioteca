package biblioteca_G_v2.demo.controller;

import biblioteca_G_v2.demo.dto.CartItemDTO;
import biblioteca_G_v2.demo.model.Order;
import biblioteca_G_v2.demo.model.OrderItem;
import biblioteca_G_v2.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class PurchaseController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        
        return orderRepository.findById(id).map(order -> {
            order.setStatus(newStatus);
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> confirmPurchase(
            @RequestBody List<CartItemDTO> items,
            @RequestParam String userId
    ) {
        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body("El carrito está vacío");
        }

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setUserId(userId); 
        order.setStatus("pendiente");

        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemDTO itemDto : items) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemDto.getProduct().getId());
            item.setQuantity(itemDto.getQuantity());
            item.setPriceAtPurchase(itemDto.getProduct().getPrice());
            
            total += item.getPriceAtPurchase() * item.getQuantity();
            orderItems.add(item);
        }

        order.setTotal(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }
}


