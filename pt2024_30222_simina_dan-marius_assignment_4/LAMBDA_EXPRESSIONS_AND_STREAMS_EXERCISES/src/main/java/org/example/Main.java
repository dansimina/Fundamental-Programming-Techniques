package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
//        Probleme ex
//        List<MonitoredData> list = Files.lines(Paths.get("data/Activities.txt")).map(line -> line.split("\\t+")).filter(parts -> parts.length == 3).map(parts -> new MonitoredData(parts[0], parts[1], parts[2])).collect(Collectors.toList());
//        System.out.println(list);
//        List<String> distinctDays = list.stream().map(data -> data.getStartTime().split(" ")[0]).distinct().collect(Collectors.toList());
//        System.out.println(distinctDays.size());
//        Map<String, Long> map = list.stream().collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting()));
//        System.out.println(map);
//        System.out.println(list.stream().map(data -> data.getStartTime().split(" ")[0]).distinct().collect(Collectors.counting()));
//
//        Long totalTime = list.stream().filter(activity -> Objects.equals(activity.getActivity(), "Sleeping")).collect(Collectors.summingLong(entry -> {
//            try {
//                return ((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(entry.getEndTime()))).getTime() - (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(entry.getStartTime())).getTime();
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        }));
//        System.out.println(totalTime);

//        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-11-28 10:18:11");
//        System.out.println(date);

//        Test
//        Extract data
        List<MonitoredData> list = Files.lines(Paths.get("data/Activities.txt")).map(line -> line.split("\\t+")).filter(parts -> parts.length == 3).map(parts -> new MonitoredData(parts[0], parts[1], parts[2])).collect(Collectors.toList());
        System.out.println(list);

//        The activity that appears the most often besides Spare_Time/TV
        String activity = String.valueOf(list.stream().filter(entry -> !Objects.equals(entry.getActivity(), "Spare_Time/TV")).collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(entry -> entry.getKey()));
        System.out.println(activity);

//        Map with activities plus a map that has the date plus the total time for that day
        Map<String, Map<LocalDate, Long>> map = list.stream().collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.groupingBy(entry-> LocalDate.parse(entry.startTime.split(" ")[0]), Collectors.summingLong(entry-> {
            try {
                return ((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(entry.getEndTime()))).getTime() - (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(entry.getStartTime())).getTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }))));
        System.out.println(map);

    }
}