package com.example.sunlighttracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Początkowe wartości czasu
        Map<String, Long> timeElapsed = calculateTimeElapsed();

        model.addAttribute("days", timeElapsed.get("days"));
        model.addAttribute("hours", timeElapsed.get("hours"));
        model.addAttribute("minutes", timeElapsed.get("minutes"));
        model.addAttribute("seconds", timeElapsed.get("seconds"));

        return "home";
    }

    @GetMapping("/api/time-elapsed")
    @ResponseBody
    public Map<String, Long> getTimeElapsed() {
        return calculateTimeElapsed();
    }

    private Map<String, Long> calculateTimeElapsed() {
        // Pobierz aktualną datę i czas
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("Europe/Paris"));

        // Początek roku
        LocalDateTime startOfYear = LocalDateTime.of(currentDate.getYear(), 1, 1, 0, 0);

        // Oblicz upłynięty czas
        long days = ChronoUnit.DAYS.between(startOfYear, currentDate);
        long hours = ChronoUnit.HOURS.between(startOfYear.plusDays(days), currentDate);
        long minutes = ChronoUnit.MINUTES.between(startOfYear.plusDays(days).plusHours(hours), currentDate);
        long seconds = ChronoUnit.SECONDS.between(startOfYear.plusDays(days).plusHours(hours).plusMinutes(minutes), currentDate);

        Map<String, Long> result = new HashMap<>();
        result.put("days", days);
        result.put("hours", hours);
        result.put("minutes", minutes);
        result.put("seconds", seconds);

        return result;
    }
}
