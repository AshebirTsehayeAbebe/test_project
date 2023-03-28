package optifoodmanagement.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		
		java.util.List<Parameter> aParameters = new ArrayList<>();
		List<String> header = new ArrayList<>();
		header.add("Authorization");
		//		header.add("X-TenantID");
		for (int i = 0; i < header.size(); i++) {
			ParameterBuilder aParameterBuilder = new ParameterBuilder();
			aParameterBuilder.name(header.get(i)).modelRef(new ModelRef("string")).parameterType("header").defaultValue("")
			        .required(false).build();
			aParameters.add(aParameterBuilder.build());
		}
		
		return new Docket(DocumentationType.SWAGGER_2).select()
		        .apis(RequestHandlerSelectors.basePackage("optifoodmanagement"))
		        .paths(PathSelectors.ant("/api/**")).build().apiInfo(apiDetails()).globalOperationParameters(aParameters);
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfoBuilder().title("Optifood Management").description("Swagger configuration for application")
		        .version("1.1.0").license("Apache 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
		        .contact(new Contact("Optifood", "https://www.optifood.com", "info@optifood.com")).build();
	}
}
