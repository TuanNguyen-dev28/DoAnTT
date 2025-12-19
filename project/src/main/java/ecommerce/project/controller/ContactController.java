package ecommerce.project.controller;

import ecommerce.project.entity.Contact;
import ecommerce.project.entity.ContactInfo;
import ecommerce.project.repository.ContactInfoRepository;
import ecommerce.project.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @GetMapping("/contact")
    public String contactPage(Model model) {
        ContactInfo contactInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(null);
        model.addAttribute("contactInfo", contactInfo);
        return "contact";
    }

    @PostMapping("/contact/submit")
    public String submitContact(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {
        
        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setSubject(subject);
        contact.setMessage(message);
        
        contactRepository.save(contact);
        
        redirectAttributes.addFlashAttribute("success", "Thank you for contacting us! We will get back to you soon.");
        return "redirect:/contact";
    }
}


