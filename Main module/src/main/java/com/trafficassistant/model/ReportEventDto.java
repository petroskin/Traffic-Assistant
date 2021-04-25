package com.trafficassistant.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ReportEventDto {
    String username;
    String reportDate;
    String reportComment;
    String eventType;
    String eventDate;
    String eventComment;
    String likesAndDislikes;

    public ReportEventDto(String username, LocalDateTime reportDate, String reportComment, String eventType, LocalDateTime eventDate, String eventComment, Map<String, Boolean> likesAndDislikes) {
        this.username = username;
        this.reportDate = reportDate.toString().split("T")[0];
        this.reportComment = reportComment;
        this.eventType = eventType;
        this.eventDate = eventDate.toString().split("T")[0];
        this.eventComment = eventComment;
        long likes = likesAndDislikes.entrySet().stream().filter(Map.Entry::getValue).count();
        long dislikes = likesAndDislikes.size() - likes;
        this.likesAndDislikes = "Likes: " + likes + "\nDislikes: " + dislikes;
    }

    public String getToolTip(){
        return "Date: " + eventDate + "\nComment: " + eventComment + "\n" + likesAndDislikes;
    }
}
