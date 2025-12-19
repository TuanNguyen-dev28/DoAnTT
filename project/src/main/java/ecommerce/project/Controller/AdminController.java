package ecommerce.project.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ecommerce.project.entity.About;
import ecommerce.project.entity.BlogPost;
import ecommerce.project.entity.Contact;
import ecommerce.project.entity.ContactInfo;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;
import ecommerce.project.repository.AboutRepository;
import ecommerce.project.repository.BlogPostRepository;
import ecommerce.project.repository.ContactInfoRepository;
import ecommerce.project.repository.ContactRepository;
import ecommerce.project.repository.OrderRepository;
import ecommerce.project.repository.ProductRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.service.OrderService;
import ecommerce.project.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @Autowired
    private BlogPostRepository blogPostRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    
    private Path getUploadPath() throws IOException {
        // Get the project root directory - try multiple approaches
        Path uploadPath = null;
        
        // Method 1: Try from current working directory
        Path currentDir = Paths.get("").toAbsolutePath().normalize();
        uploadPath = currentDir.resolve(UPLOAD_DIR);
        
        // Method 2: If that doesn't exist, try from project directory
        if (!Files.exists(uploadPath.getParent())) {
            // Try to find project root by looking for pom.xml or build.gradle
            Path testPath = currentDir;
            for (int i = 0; i < 3; i++) {
                if (Files.exists(testPath.resolve("pom.xml")) || Files.exists(testPath.resolve("build.gradle"))) {
                    uploadPath = testPath.resolve(UPLOAD_DIR);
                    break;
                }
                testPath = testPath.getParent();
                if (testPath == null) break;
            }
        }
        
        // Method 3: If still not found, use absolute path from user.dir
        if (uploadPath == null || !Files.exists(uploadPath.getParent())) {
            String userDir = System.getProperty("user.dir");
            if (userDir != null) {
                uploadPath = Paths.get(userDir).resolve(UPLOAD_DIR);
            }
        }
        
        // Final fallback: use relative path
        if (uploadPath == null || !Files.exists(uploadPath.getParent())) {
            uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
        }
        
        // Create directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            log.info("Created upload directory: {}", uploadPath.toAbsolutePath());
        }
        
        log.info("Using upload path: {}", uploadPath.toAbsolutePath());
        log.debug("Upload path exists: {}", Files.exists(uploadPath));
        log.debug("Upload path is writable: {}", Files.isWritable(uploadPath));
        
        return uploadPath;
    }

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
        // Pass error message if any
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
        
        log.info("Saving product: {}", product.getName());
        
        try {
            // Handle file upload if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                // Validate file
                String originalFilename = imageFile.getOriginalFilename();
                if (originalFilename == null || originalFilename.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Invalid image file name");
                    return product.getId() != null ? "redirect:/admin/products/edit/" + product.getId() : "redirect:/admin/products/add";
                }
                
                // Check file size (max 10MB)
                if (imageFile.getSize() > 10 * 1024 * 1024) {
                    redirectAttributes.addFlashAttribute("error", "Image file is too large. Maximum size is 10MB");
                    return product.getId() != null ? "redirect:/admin/products/edit/" + product.getId() : "redirect:/admin/products/add";
                }
                
                // Get upload directory
                Path uploadPath = getUploadPath();
                
                // Generate unique filename
                String extension = originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase()
                    : ".jpg";
                String filename = "product-" + System.currentTimeMillis() + extension;
                
                // Save file
                Path filePath = uploadPath.resolve(filename);
                
                log.debug("Attempting to save file to: {}", filePath.toAbsolutePath());
                
                // Copy file
                try {
                    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    log.debug("File copy completed");
                } catch (IOException e) {
                    log.error("Error copying file: {}", e.getMessage(), e);
                    redirectAttributes.addFlashAttribute("error", "Failed to save image file: " + e.getMessage());
                    return product.getId() != null ? "redirect:/admin/products/edit/" + product.getId() : "redirect:/admin/products/add";
                }
                
                // Verify file was saved
                if (!Files.exists(filePath)) {
                    log.error("File was not saved! Path: {}", filePath.toAbsolutePath());
                    redirectAttributes.addFlashAttribute("error", "Failed to save image file - file not found after copy");
                    return product.getId() != null ? "redirect:/admin/products/edit/" + product.getId() : "redirect:/admin/products/add";
                }
                
                // Check file size
                long fileSize = Files.size(filePath);
                log.info("File saved successfully! Size: {} bytes", fileSize);
                
                // Set image URL (relative to static/images)
                product.setImageUrl("images/" + filename);
                
                log.info("Image saved successfully to: {}", filePath.toAbsolutePath());
            } else {
                // If no new file uploaded
                if (product.getId() != null) {
                    // Editing existing product - keep existing image if no new one uploaded
                    Product existingProduct = productService.findById(product.getId()).orElse(null);
                    if (existingProduct != null && (product.getImageUrl() == null || product.getImageUrl().isEmpty())) {
                        product.setImageUrl(existingProduct.getImageUrl());
                    }
                } else {
                    // New product - require either file upload or imageUrl
                    if (product.getImageUrl() == null || product.getImageUrl().trim().isEmpty()) {
                        redirectAttributes.addFlashAttribute("error", "Please upload an image file or enter an image URL");
                        return "redirect:/admin/products/add";
                    }
                }
            }
            
            // Verify product has imageUrl before saving
            if (product.getImageUrl() == null || product.getImageUrl().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Product must have an image. Please upload an image file or enter an image URL.");
                return product.getId() != null ? "redirect:/admin/products/edit/" + product.getId() : "redirect:/admin/products/add";
            }
            
            // Save product to database
            log.debug("Saving product to database: {}", product);
            
            Product savedProduct = productService.save(product);
            
            log.info("Product saved successfully with ID: {}", savedProduct.getId());
            
            redirectAttributes.addFlashAttribute("success", "Product saved successfully! Image: " + savedProduct.getImageUrl());
        } catch (IOException e) {
            log.error("IOException during save: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Failed to upload image: " + e.getMessage());
            return product.getId() != null ? "redirect:/admin/products/edit/" + product.getId() : "redirect:/admin/products/add";
        } catch (Exception e) {
            log.error("Exception during save: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error saving product: " + e.getMessage());
            return product.getId() != null ? "redirect:/admin/products/edit/" + product.getId() : "redirect:/admin/products/add";
        }
        
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
            @RequestParam(required = false) Order.OrderStatus status,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage;

        if (status != null) {
            Order probe = new Order();
            probe.setStatus(status);
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnorePaths("id", "user", "orderDate", "totalPrice", "shippingAddress", "phone", "email", "fullName", "cancellationReason", "orderItems");
            Example<Order> example = Example.of(probe, matcher);
            orderPage = orderRepository.findAll(example, pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }

        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalItems", orderPage.getTotalElements());
        model.addAttribute("currentStatus", status);
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
    @Transactional
    public String saveContactInfo(
            @ModelAttribute ContactInfo contactInfo,
            RedirectAttributes redirectAttributes) {
        try {
            // Always get the existing record first (if any)
            ContactInfo existingInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(null);
            
            if (existingInfo != null) {
                // Update existing record - always update all fields
                existingInfo.setAddress(contactInfo.getAddress() != null ? contactInfo.getAddress().trim() : null);
                existingInfo.setPhone(contactInfo.getPhone() != null ? contactInfo.getPhone().trim() : null);
                existingInfo.setEmail(contactInfo.getEmail() != null ? contactInfo.getEmail().trim() : null);
                existingInfo.setDescription(contactInfo.getDescription() != null ? contactInfo.getDescription().trim() : null);
                // Use saveAndFlush to ensure changes are persisted immediately
                contactInfoRepository.saveAndFlush(existingInfo);
            } else {
                // Create new record if none exists
                if (contactInfo == null) {
                    contactInfo = new ContactInfo();
                }
                // Trim all fields
                if (contactInfo.getAddress() != null) {
                    contactInfo.setAddress(contactInfo.getAddress().trim());
                }
                if (contactInfo.getPhone() != null) {
                    contactInfo.setPhone(contactInfo.getPhone().trim());
                }
                if (contactInfo.getEmail() != null) {
                    contactInfo.setEmail(contactInfo.getEmail().trim());
                }
                if (contactInfo.getDescription() != null) {
                    contactInfo.setDescription(contactInfo.getDescription().trim());
                }
                // Use saveAndFlush to ensure changes are persisted immediately
                contactInfoRepository.saveAndFlush(contactInfo);
            }
            
            // Clean up any duplicate records (keep only the first one)
            try {
                List<ContactInfo> allRecords = contactInfoRepository.findAll();
                if (allRecords.size() > 1) {
                    // Keep the first record, delete all others
                    for (int i = 1; i < allRecords.size(); i++) {
                        contactInfoRepository.deleteById(allRecords.get(i).getId());
                    }
                }
            } catch (Exception e) {
                // Ignore cleanup errors
            }
            
            redirectAttributes.addFlashAttribute("success", "Contact information updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating contact information: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "redirect:/admin/contact-info";
    }

    // ==================== BLOG POST MANAGEMENT ====================
    @GetMapping("/blog-posts")
    public String listBlogPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogPost> blogPostPage = blogPostRepository.findAllByOrderByCreatedAtDesc(pageable);
        model.addAttribute("blogPosts", blogPostPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPostPage.getTotalPages());
        model.addAttribute("totalItems", blogPostPage.getTotalElements());
        return "admin/blog-posts";
    }

    @GetMapping("/blog-posts/add")
    public String showAddBlogPostForm(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "admin/blog-post-form";
    }

    @GetMapping("/blog-posts/edit/{id}")
    public String showEditBlogPostForm(@PathVariable("id") Long id, Model model) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog post Id:" + id));
        model.addAttribute("blogPost", blogPost);
        return "admin/blog-post-form";
    }

    @PostMapping("/blog-posts/save")
    public String saveBlogPost(
            @ModelAttribute BlogPost blogPost,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        
        // Handle file upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Create upload directory if it doesn't exist
                Path uploadPath = getUploadPath();
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Generate unique filename
                String originalFilename = imageFile.getOriginalFilename();
                String extension = originalFilename != null && originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                    : "";
                String filename = "blog-" + System.currentTimeMillis() + extension;
                
                // Save file
                Path filePath = uploadPath.resolve(filename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                // Set image URL (relative to static/images)
                blogPost.setImageUrl("images/" + filename);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Failed to upload image: " + e.getMessage());
                return "redirect:/admin/blog-posts";
            }
        }
        
        // If no new file uploaded and imageUrl is empty, keep existing one
        if ((blogPost.getImageUrl() == null || blogPost.getImageUrl().isEmpty()) && blogPost.getId() != null) {
            BlogPost existingPost = blogPostRepository.findById(blogPost.getId()).orElse(null);
            if (existingPost != null) {
                blogPost.setImageUrl(existingPost.getImageUrl());
            }
        }
        
        blogPostRepository.save(blogPost);
        redirectAttributes.addFlashAttribute("success", "Blog post saved successfully!");
        return "redirect:/admin/blog-posts";
    }

    @PostMapping("/blog-posts/delete/{id}")
    public String deleteBlogPost(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            blogPostRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Blog post deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting blog post: " + e.getMessage());
        }
        return "redirect:/admin/blog-posts";
    }
}
