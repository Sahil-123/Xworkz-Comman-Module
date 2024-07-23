package com.xworkz.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVExport {

    private static Map<String, Object> toFieldMap(Object object) {
        Map<String, Object> fieldMap = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                fieldMap.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldMap;
    }

    private static <T> String[]  exportToStringArray(String[] headers, T t){
        Map<String, Object> map = toFieldMap(t);

        ArrayList<String> data = new ArrayList<>();

        for (String field : headers){
            Object value = map.get(field);
            data.add(value != null ? value.toString() : "");
        }

        return data.toArray(new String[]{});
    }

    public static <T> void sendCSV(PrintWriter writer, List<T> list, String[] headers) throws IOException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("The list of data to write to CSV is null or empty");
        }

        try(CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))){
            for (T item: list){
                String[] s = exportToStringArray(headers,item);
                printer.printRecord((Object[]) s);
            }

            printer.flush();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }



//        System.out.println(headers);
//        Arrays.stream(headers).forEach(e-> System.out.print(e+" "));
//        System.out.println();
//        for (T item: list){
//            String[] s = exportToStringArray(headers,item);
//            Arrays.stream(s).forEach(e-> System.out.print(e+" "));
//            System.out.println();
//        }

    }


}
