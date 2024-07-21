package com.xworkz.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;


public class CsvUtil {
    public static <T> void writeComplaintsToCsv(HttpServletResponse response, List<T> complaints, Class<T> objectSchema) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(objectSchema).withHeader().withColumnReordering(false);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"download.csv\"");

        mapper.writer(schema).writeValues(response.getWriter()).writeAll(complaints);
    }

    public static <T> void writeListToCsv(HttpServletResponse response, List<T> list, Class<T> clazz, String[] fieldNames) throws IOException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("The list of data to write to CSV is null or empty");
        }

        CsvMapper mapper = new CsvMapper();

        // Register the JavaTimeModule and custom LocalDateTime serializer
        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(javaTimeModule);
//        mapper.registerModule(module);

        mapper.configOverride(LocalDateTime.class).setFormat(JsonFormat.Value.empty());

        // Build the schema dynamically based on the provided field names
        CsvSchema.Builder schemaBuilder = CsvSchema.builder().setUseHeader(true);

        for (String field : fieldNames) {
            schemaBuilder.addColumn(field,CsvSchema.ColumnType.STRING);
        }

        CsvSchema schema = schemaBuilder.
//                setNullValue("-").
                disableQuoteChar().
                build();

        // Debugging: Print out the schema to check
        System.out.println("CSV Schema: " + schema);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        // Write the data to CSV
        try(PrintWriter writer = response.getWriter()) {
//            mapper.writer(schema).writeValue(response.getWriter(), list);
            mapper.writerFor(mapper.getTypeFactory().constructCollectionType(List.class, clazz))
                    .with(schema)
                    .writeValue(writer, list);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error writing CSV data", e);
        }
    }
}
