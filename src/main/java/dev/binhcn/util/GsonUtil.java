package dev.binhcn.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Base64;

public class GsonUtil {

  private static final Gson gsonSnakeCase;
  private static final Gson gson;

  public static String toJsonStringSnakeCase(Object obj) {
    return gsonSnakeCase.toJson(obj);
  }

  public static String toJsonString(Object obj) {
    return gson.toJson(obj);
  }

  public static <T> T fromJsonSnakeCase(String jsonSnakeCase, Class<T> t) {
    return gsonSnakeCase.fromJson(jsonSnakeCase, t);
  }

  public static <T> T fromJsonString(String sJson, Class<T> t) {
    return gson.fromJson(sJson, t);
  }

  static {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    gsonBuilder.registerTypeAdapter(byte[].class, new TypeAdapter<byte[]>() {
      @Override
      public void write(JsonWriter out, byte[] value) throws IOException {
        out.value(Base64.getEncoder().encodeToString(value));
      }

      @Override
      public byte[] read(JsonReader in) throws IOException {
        return Base64.getDecoder().decode(in.nextString());
      }
    });
    gson = gsonBuilder.disableHtmlEscaping().create();

    GsonBuilder snakeCaseGsonBuilder = new GsonBuilder();
    snakeCaseGsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    snakeCaseGsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    snakeCaseGsonBuilder.registerTypeAdapter(byte[].class, new TypeAdapter<byte[]>() {
      @Override
      public void write(JsonWriter out, byte[] value) throws IOException {
        out.value(Base64.getEncoder().encodeToString(value));
      }

      @Override
      public byte[] read(JsonReader in) throws IOException {
        return Base64.getDecoder().decode(in.nextString());
      }
    });
    gsonSnakeCase = snakeCaseGsonBuilder.create();
  }

}
