package com.task.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long articleId;

    @Column(name = "article_name", nullable = false)
    private String articleName;

    @Column(name = "article_number", nullable = false)
    private String articleNumber;

    @Column(name = "article_count", nullable = false)
    private Long articleCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityId")
    private Activity activity;
}
