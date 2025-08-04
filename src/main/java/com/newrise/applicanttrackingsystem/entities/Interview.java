package com.newrise.applicanttrackingsystem.entities;

//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.PreUpdate;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "Interview")
public class Interview 
{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long interviewId;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "jobApplications_id", nullable = false)
//    private JobApplications jobApplications;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "interviewer_id", nullable = false)
//    private Users interviewer;
//
//    @Column(nullable = false)
//    private LocalDateTime scheduledAt;

//    RELATIONSHIP & DataInitializer
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "status_id", nullable = false)
//    private ApplicationStatus applicationStatus; 

//    RELATIONSHIP & DataInitializer
//    @Column(nullable = false)
//    private String mode; // Online, Offline, On-site
//
//    @Column(name = "remarks", length = 500)
//    private String remarks;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @Column
//    private LocalDateTime updatedAt;
//    
//    @PreUpdate
//    public void preUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
}
