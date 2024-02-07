package ru.yandex.incoming34.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ru.yandex.incoming34.structures.dto.CoursesResponce;

public class CoursesResponceSrializer extends StdSerializer<CoursesResponce> {
	private static final long serialVersionUID = 1L;

	public CoursesResponceSrializer() {
		this(null);
	}

	public CoursesResponceSrializer(final Class<CoursesResponce> klass) {
		super(klass);
	}

	public void serialize(CoursesResponce value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		jgen.writeNumberField("errCode", value.getErrorCode().getCode());
		jgen.writeStringField("errMsg", value.getErrorMessage().getMessage());
		jgen.writeEndObject();
	}
}
