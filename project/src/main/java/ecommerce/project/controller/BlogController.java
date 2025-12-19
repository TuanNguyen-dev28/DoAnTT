package ecommerce.project.controller;

import ecommerce.project.entity.BlogPost;
import ecommerce.project.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping("/blog")
    public String blogList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
            @RequestParam(value = "category", required = false) String category,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogPost> blogPosts;
        
        if (category != null && !category.isEmpty()) {
            blogPosts = blogPostRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
        } else {
            blogPosts = blogPostRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        
        List<String> categories = blogPostRepository.findAllDistinctCategories();
        
        model.addAttribute("blogPosts", blogPosts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPosts.getTotalPages());
        model.addAttribute("totalBlogPosts", blogPosts.getTotalElements());
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        
        return "blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable("id") Long id, Model model) {
        Optional<BlogPost> blogPostOpt = blogPostRepository.findById(id);
        
        if (blogPostOpt.isEmpty()) {
            return "redirect:/blog";
        }
        
        BlogPost blogPost = blogPostOpt.get();
        
        // Get related posts (same category, excluding current post)
        List<BlogPost> relatedPosts = blogPostRepository.findByCategoryOrderByCreatedAtDesc(blogPost.getCategory())
                .stream()
                .filter(post -> !post.getId().equals(id))
                .limit(3)
                .toList();
        
        // If not enough related posts, get latest posts
        if (relatedPosts.size() < 3) {
            List<BlogPost> latestPosts = blogPostRepository.findAllByOrderByCreatedAtDesc()
                    .stream()
                    .filter(post -> !post.getId().equals(id))
                    .limit(3 - relatedPosts.size())
                    .toList();
            relatedPosts = List.of(relatedPosts, latestPosts).stream()
                    .flatMap(List::stream)
                    .limit(3)
                    .toList();
        }
        
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("relatedPosts", relatedPosts);
        
        return "blog-detail";
    }
}

