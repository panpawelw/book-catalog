package pl.pjm77.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "pl.pjm77")
public class AppConfig implements WebMvcConfigurer {

	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/").setCachePeriod(31556926);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/books/**").allowedMethods("GET", "POST", "PUT", "DELETE"); //.allowedOrigins("http://localhost");
	}
	// @Bean
	// public ViewResolver viewResolver() {
	// InternalResourceViewResolver viewResolver = new
	// InternalResourceViewResolver();
	// viewResolver.setViewClass(JstlView.class);
	// viewResolver.setPrefix("/WEB-INF/views/");
	// viewResolver.setSuffix(".jsp");
	// return viewResolver;
	// }
}