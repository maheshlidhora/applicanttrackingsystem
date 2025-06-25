package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Token")
public class Token
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId", unique = true)
    private long tokenId;

    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;

    @Column(name = "isBlacklisted", nullable = false)
    private boolean isBlacklisted;		
    // 	Revoked (isBlacklisted) --> Manual invalidation means Token issued for 1 hour → User logs out after 10 mins

    @Column(name = "expired", nullable = false)
    private boolean expired;			
    //	Time-based automatic invalidation --> Token issued with 30 min expiry → 30 minutes later, token is no longer valid due to expiry.

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expiresAt", nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private Users user;
	
	//	*************************************  Getter, Setter & Constructors  *************************************

	public long getTokenId() {
		return tokenId;
	}

	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isBlacklisted() {
		return isBlacklisted;
	}

	public void setBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Users getUserId() {
		return user;
	}

	public void setUserId(Users userId) {
		this.user = userId;
	}

	public Token(long tokenId, String token, boolean isBlacklisted, boolean expired, LocalDateTime createdAt,
			LocalDateTime expiresAt, Users userId) {
		super();
		this.tokenId = tokenId;
		this.token = token;
		this.isBlacklisted = isBlacklisted;
		this.expired = expired;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.user = userId;
	}

	public Token() {
		super();
	}
}
