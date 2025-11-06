package ecommerce.project.controller;

import ecommerce.project.entity.About;
import ecommerce.project.entity.Contact;
import ecommerce.project.entity.ContactInfo;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;
import ecommerce.project.repository.AboutRepository;
import ecommerce.project.repository.ContactInfoRepository;
import ecommerce.project.repository.ContactRepository;
import ecommerce.project.repository.OrderRepository;
import ecommerce.project.repository.ProductRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.service.OrderService;
import ecommerce.project.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    // Redirect /admin to /admin/dashboard
    @GetMapping("")
    public String adminRoot() {
        return "redirect:/admin/dashboard";
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AboutRepository aboutRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    // ==================== DASHBOARD ====================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalProducts = productRepository.count();
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        List<Order> recentOrders = orderRepository.findAllByOrderByOrderDateDesc()
                .stream().limit(5).toList();
        
        // Calculate total revenue from all orders
        BigDecimal totalRevenue = orderRepository.findAll().stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Count orders by status
        long pendingOrders = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.PENDING)
                .count();
        long deliveredOrders = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.DELIVERED)
                .count();
        
        // Count unread contacts
        long unreadContacts = contactRepository.countByIsReadFalse();

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        model.addAttribute("unreadContacts", unreadContacts);
        model.addAttribute("recentOrders", recentOrders);
        return "admin/dashboard";
    }

    // ==================== PRODUCT MANAGEMENT ====================
    @GetMapping("/products")
    public String listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.findAll(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(
            @ModelAttribute Product product,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        
        // Handle file upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Create upload directory if it doesn't exist
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Generate unique filename
                String originalFilename = imageFile.getOriginalFilename();
                String extension = originalFilename != null && originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                    : "";
                String filename = "product-" + System.currentTimeMillis() + extension;
                
                // Save file
                Path filePath = uploadPath.resolve(filename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                // Set image URL (relative to static/images)
                product.setImageUrl("images/" + filename);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Failed to upload image: " + e.getMessage());
                return "redirect:/admin/products";
            }
        }
        
        // If no new file uploaded and imageUrl is empty, keep existing one
        if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
            if (product.getId() != null) {
                Product existingProduct = productService.findById(product.getId()).orElse(null);
                if (existingProduct != null) {
                    product.setImageUrl(existingProduct.getImageUrl());
                }
            }
        }
        
        productService.save(product);
        redirectAttributes.addFlashAttribute("success", "Product saved successfully!");
        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting product: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }

    // ==================== USER MANAGEMENT ====================
    @GetMapping("/users")
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/user-form";
    }

    @PostMapping("/users/update")
    public String updateUser(
            @RequestParam("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam("enabled") boolean enabled,
            RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(enabled);
        
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "User updated successfully!");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // ==================== ORDER MANAGEMENT ====================
    @GetMapping("/orders")
    public String listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalItems", orderPage.getTotalElements());
        return "admin/orders";
    }

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "admin/order-detail";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") Order.OrderStatus status,
            RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Order status updated successfully!");
        return "redirect:/admin/orders/" + id;
    }

    // ==================== CONTACT MANAGEMENT ====================
    @GetMapping("/contacts")
    public String listContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Contact> contactPage = contactRepository.findAllByOrderByCreatedAtDesc(pageable);
        model.addAttribute("contacts", contactPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactPage.getTotalPages());
        model.addAttribute("totalItems", contactPage.getTotalElements());
        model.addAttribute("unreadCount", contactRepository.countByIsReadFalse());
        return "admin/contacts";
    }

    @GetMapping("/contacts/{id}")
    public String viewContact(@PathVariable("id") Long id, Model model) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found"));
        
        // Mark as read
        contact.setRead(true);
        contactRepository.save(contact);
        
        model.addAttribute("contact", contact);
        return "admin/contact-detail";
    }

    @PostMapping("/contacts/{id}/delete")
    public String deleteContact(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            contactRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Contact deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting contact: " + e.getMessage());
        }
        return "redirect:/admin/contacts";
    }

    // ==================== ABOUT MANAGEMENT ====================
    @GetMapping("/about")
    public String aboutPage(Model model) {
        About about = aboutRepository.findFirstByOrderByIdAsc().orElse(new About());
        model.addAttribute("about", about);
        return "admin/about-form";
    }

    @PostMapping("/about/save")
    public String saveAbout(
            @ModelAttribute About about,
            RedirectAttributes redirectAttributes) {
        // If about doesn't have an ID, it's new, otherwise update existing
        if (about.getId() == null) {
            About existingAbout = aboutRepository.findFirstByOrderByIdAsc().orElse(null);
            if (existingAbout != null) {
                about.setId(existingAbout.getId());
            }
        }
        aboutRepository.save(about);
        redirectAttributes.addFlashAttribute("success", "About page updated successfully!");
        return "redirect:/admin/about";
    }

    // ==================== CONTACT INFO MANAGEMENT ====================
    @GetMapping("/contact-info")
    public String contactInfoPage(Model model) {
        ContactInfo contactInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(new ContactInfo());
        model.addAttribute("contactInfo", contactInfo);
        return "admin/contact-info-form";
    }

    @PostMapping("/contact-info/save")
    public String saveContactInfo(
            @ModelAttribute ContactInfo contactInfo,
            RedirectAttributes redirectAttributes) {
        // Check if contactInfo is null
        if (contactInfo == null) {
            contactInfo = new ContactInfo();
        }
        
        // If contactInfo doesn't have an ID, try to get existing one
        if (contactInfo.getId() == null) {
            ContactInfo existingInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(null);
            if (existingInfo != null) {
                // Update existing record
                existingInfo.setAddress(contactInfo.getAddress());
                existingInfo.setPhone(contactInfo.getPhone());
                existingInfo.setEmail(contactInfo.getEmail());
                existingInfo.setDescription(contactInfo.getDescription());
                contactInfoRepository.save(existingInfo);
            } else {
                // Create new record
                contactInfoRepository.save(contactInfo);
            }
        } else {
            // Update existing record
            contactInfoRepository.save(contactInfo);
        }
        
        redirectAttributes.addFlashAttribute("success", "Contact information updated successfully!");
        return "redirect:/admin/contact-info";
    }
}
