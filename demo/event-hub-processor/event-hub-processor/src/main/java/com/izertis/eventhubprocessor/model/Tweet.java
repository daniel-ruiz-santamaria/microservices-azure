package com.izertis.eventhubprocessor.model;

import com.google.gson.JsonObject;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="tweet")
@Getter
@ToString(includeFieldNames = true)
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name="created_at", nullable = false,columnDefinition = "DATETIME")
    private Date createdAt;

    @Column(name="text", nullable = true,columnDefinition = "VARCHAR(800)",length = 800)
    private String text;

    @Column(name="coordinates", nullable = true,columnDefinition = "VARCHAR(400)",length = 400)
    private String coordinates;

    @Column(name="source", nullable = true,columnDefinition = "VARCHAR(400)",length = 400)
    private String source;

    @Column(name="place", nullable = true,columnDefinition = "VARCHAR(400)",length = 400)
    private String place;

    @Column(name="retweets", nullable = false,columnDefinition = "int")
    private int retweets;

    @Column(name="favorites", nullable = false,columnDefinition = "int")
    private int favorites;

    @Column(name="sentiment_polarity", nullable = false,columnDefinition = "DECIMAL(16,14)")
    private double sentimentPolarity;

    @Column(name="sentiment_subjectivity", nullable = false,columnDefinition = "DECIMAL(16,14)")
    private double sentimentSubjectivity;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Tweet(User user ,JsonObject jTweet) {

        this.user = user;

        if (jTweet.has("id"))
            this.id = Long.valueOf(jTweet.get("id").getAsString());
        if (jTweet.has("created_at")) {
            String dateStr = jTweet.get("created_at").getAsString();
            if (dateStr!=null) {
                try {
                    this.createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (jTweet.has("text") && !jTweet.get("text").isJsonNull())
            this.text = jTweet.get("text").getAsString();
        if (jTweet.has("coordinates") && !jTweet.get("coordinates").isJsonNull())
            this.coordinates = jTweet.get("coordinates").getAsString();
        if (jTweet.has("source") && !jTweet.get("source").isJsonNull())
            this.source = jTweet.get("source").getAsString();
        if (jTweet.has("place") && !jTweet.get("place").isJsonNull())
            this.place = jTweet.get("place").getAsString();
        if (jTweet.has("retweet_count")  && !jTweet.get("retweet_count").isJsonNull())
            this.retweets = jTweet.get("retweet_count").getAsInt();
        if (jTweet.has("favorite_count") && !jTweet.get("favorite_count").isJsonNull())
            this.favorites = jTweet.get("favorite_count").getAsInt();
        if (jTweet.has("sentiment_polarity") && !jTweet.get("sentiment_polarity").isJsonNull())
            this.sentimentPolarity = jTweet.get("sentiment_polarity").getAsDouble();
        if (jTweet.has("sentiment_subjectivity") && !jTweet.get("sentiment_subjectivity").isJsonNull())
            this.sentimentSubjectivity = jTweet.get("sentiment_subjectivity").getAsDouble();
    }
}
