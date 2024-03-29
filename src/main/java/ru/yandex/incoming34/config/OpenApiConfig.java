package ru.yandex.incoming34.config;

import java.io.File;
import java.util.Objects;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootConfiguration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenApi() {
		OpenAPI openAPI = new OpenAPI();
		openAPI.setInfo(info());
		return openAPI;
	}

	@Bean
	Info info() {
		return new Info().title("Задание для второй сессии технического интервью").description("Test project")
				.version(componentVersion()).contact(contact());
	}

	@Bean
	Contact contact() {
		return new Contact().email("incoming34@yandex.ru").name("Sergei Aidinov");
	}

	@SuppressWarnings("deprecation")
	private String componentVersion() {
		String propertiesFileName = "pom.xml";
		String componentVersion = "Версия не указана";
		File file = new File(propertiesFileName);
		XmlMapper xmlMapper = new XmlMapper();
		try {
			JsonSchema jsonSchema = xmlMapper.generateJsonSchema(String.class);
			JsonSchema json = xmlMapper.readValue(file, jsonSchema.getClass());
			componentVersion = Objects.nonNull(json.getSchemaNode().get("version"))
					? String.valueOf(json.getSchemaNode().get("version")).replaceAll("\"", "")
					: componentVersion;
		} catch (Exception e) {
			return componentVersion;
		}
		return componentVersion;
	}

}
