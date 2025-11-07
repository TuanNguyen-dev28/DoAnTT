package ecommerce.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Get project root directory
        String projectRoot = System.getProperty("user.dir");
        Path staticPath = Paths.get(projectRoot, "src/main/resources/static/images").toAbsolutePath();
        
        System.out.println("Configuring static resource handler for images");
        System.out.println("Images path: " + staticPath.toString());
        System.out.println("Path exists: " + java.nio.file.Files.exists(staticPath));
        
        // Serve images from file system (for uploaded files at runtime)
        // Priority: file system first (for newly uploaded files), then classpath (for existing files)
        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                    "file:" + staticPath.toString() + "/",  // First: serve from file system
                    "classpath:/static/images/"              // Fallback: serve from classpath
                )
                .setCachePeriod(3600);
        
        // Serve other static resources from classpath
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
}
