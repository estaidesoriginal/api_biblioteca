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
import java.util.UUID;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class PurchaseController {

    @Autowired
    private OrderRepository orderRepository;

    // Modificamos para recibir el userId como parámetro en la URL
    // Ejemplo: POST /compras?userId=uuid-del-usuario
    @PostMapping
    public ResponseEntity<?> confirmPurchase(
            @RequestBody List<CartItemDTO> items,
            @RequestParam String userId
    ) {
        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body("El carrito está vacío");
        }

        // 1. Crear la Orden
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        
        // CORRECCIÓN: Usamos el ID real del usuario que viene de la App
        // Esto evita el error de Foreign Key en la base de datos
        order.setUserId(userId); 

        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        // 2. Procesar Items
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

        // 3. Guardar en Base de Datos
        Order savedOrder = orderRepository.save(order);

        // 4. Devolvemos la orden guardada (con su ID real) para la boleta
        return ResponseEntity.ok(savedOrder);
    }
}