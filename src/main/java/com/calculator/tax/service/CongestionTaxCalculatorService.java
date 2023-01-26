package com.calculator.tax.service;

import com.calculator.tax.web.dto.CongestionRequest;
import com.calculator.tax.web.dto.CongestionResponse;
import com.calculator.tax.web.dto.TaxDetails;
import com.calculator.tax.web.dto.VehicleTypes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CongestionTaxCalculatorService implements ICongestionTaxCalculatorService {
    private static final Map<VehicleTypes, Integer> tollFreeVehicles = new HashMap<>();

    static {
        tollFreeVehicles.put(VehicleTypes.motorcycle, 0);
        tollFreeVehicles.put(VehicleTypes.tractor, 1);
        tollFreeVehicles.put(VehicleTypes.emergency, 2);
        tollFreeVehicles.put(VehicleTypes.diplomat, 3);
        tollFreeVehicles.put(VehicleTypes.foreign, 4);
        tollFreeVehicles.put(VehicleTypes.military, 5);
    }

    /**
     * Calculate the tax for a single day
     *
     * @param vehicleType The vehicle to which the timestamps belong
     * @param timestamps Timestamps of a single day
     * @return total fee of a single day for a given vehicle
     */
    public int getTaxByDay(VehicleTypes vehicleType, List<Date> timestamps)
    {
        Date intervalStart = timestamps.get(0);
        int totalFee = 0;

        for (Date date : timestamps) {
            int nextFee = GetTollFee(date, vehicleType);
            int tempFee = GetTollFee(intervalStart, vehicleType);

            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies / 1000 / 60;

            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
            }
        }

        return Math.min(totalFee, 60);
    }

    private boolean IsTollFreeVehicle(VehicleTypes vehicleType) {
        if (vehicleType == null) return false;
        return tollFreeVehicles.containsKey(vehicleType);
    }

    public int GetTollFee(Date timestamp, VehicleTypes vehicleType)
    {
        if (IsTollFreeDate(timestamp) || IsTollFreeVehicle(vehicleType)) {
            return 0;
        }

        int hour = timestamp.getHours();
        int minute = timestamp.getMinutes();

        if (hour == 6 && minute <= 29) return 8;
        else if (hour == 6) return 13;
        else if (hour == 7) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30) return 8;
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour == 15 || hour == 16) return 18;
        else if (hour == 17) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    private Boolean IsTollFreeDate(Date timestamp)
    {
        int year = timestamp.getYear();
        int month = timestamp.getMonth() + 1;
        int day = timestamp.getDay() + 1;
        int dayOfMonth = timestamp.getDate();

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        if (year == 2013) {
            return (month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31));
        }

        return false;
    }

    @Override
    public CongestionResponse calculateTax(CongestionRequest request) {
        var timestampsGroupedByDate = request.getTimestamps().stream().collect(Collectors.groupingBy(localDateTime ->
            localDateTime.truncatedTo(ChronoUnit.DAYS).withDayOfYear(localDateTime.getDayOfYear()).toLocalDate()
        ));

        var details = new HashSet<TaxDetails>();

        var total = timestampsGroupedByDate.entrySet().stream().reduce(0, (acc, group) -> {
            var dateList = group.getValue().stream().map(this::convertToDateViaInstant).toList();
            var dayTax = getTaxByDay(request.getVehicleType(), dateList);

            details.add(TaxDetails.builder().value(dayTax).timestamp(group.getKey()).build());

            return acc + dayTax;
        }, Integer::sum);

        return CongestionResponse
                .builder()
                .total(total)
                .details(details)
                .build();
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
