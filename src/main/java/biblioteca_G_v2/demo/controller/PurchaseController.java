package biblioteca_G_v2.demo.controller;

import biblioteca_G_v2.demo.dto.CartItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class PurchaseController {

    @PostMapping
    public ResponseEntity<?> confirmPurchase(@RequestBody List<CartItemDTO> items) {
        System.out.println("Procesando compra de " + items.size() + " items.");
        return ResponseEntity.ok().build();
    }
}