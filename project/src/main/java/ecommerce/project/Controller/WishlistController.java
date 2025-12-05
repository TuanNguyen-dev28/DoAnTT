package ecommerce.project.controller;

import ecommerce.project.entity.Wishlist;
import ecommerce.project.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/wishlist")
    public String wishlistPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Wishlist> wishlistItems = wishlistService.getUserWishlist(principal.getName());
        model.addAttribute("wishlistItems", wishlistItems);
        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToWishlist(
            @RequestParam("productId") Long productId,
            Principal principal) {
        if (principal == null) {
            return createUnauthorizedResponse("Please login to add items to wishlist");
        }
        return toggleWishlist(productId, principal);
    }

    @PostMapping("/wishlist/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFromWishlist(
            @RequestParam("productId") Long productId,
            Principal principal) {
        if (principal == null) {
            return createUnauthorizedResponse("Please login");
        }
        return toggleWishlist(productId, principal);
    }

    @GetMapping("/wishlist/check")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkWishlist(
            @RequestParam("productId") Long productId,
            Principal principal) {
        Map<String, Object> response = new HashMap<>();
        
        if (principal == null) {
            response.put("inWishlist", false);
            return ResponseEntity.ok(response);
        }

        boolean inWishlist = wishlistService.isInWishlist(productId, principal.getName());
        response.put("inWishlist", inWishlist);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wishlist/toggle")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleWishlist(
            @RequestParam("productId") Long productId,
            Principal principal) {
        if (principal == null) {
            return createUnauthorizedResponse("Please login to add items to wishlist");
        }

        Map<String, Object> response = new HashMap<>();

        try {
            boolean inWishlist = wishlistService.isInWishlist(productId, principal.getName());
            if (inWishlist) {
                wishlistService.removeFromWishlist(productId, principal.getName());
                response.put("action", "removed");
            } else {
                wishlistService.addToWishlist(productId, principal.getName());
                response.put("action", "added");
            }
            int count = wishlistService.getWishlistCount(principal.getName());
            response.put("success", true);
            response.put("count", count);
            response.put("inWishlist", !inWishlist);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Re-initialize or clear for error case
            response.clear();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Creates a standard response for unauthorized users.
     * @param message The message to include in the response.
     * @return A ResponseEntity with an unauthorized status.
     */
    private ResponseEntity<Map<String, Object>> createUnauthorizedResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        // It's better to return a 401 Unauthorized status code
        // The client can then use this status to redirect to the login page.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
