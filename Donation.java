package com.example.helper;

public class Donation {
    private String recipientUid;
    private String recipientName;
    private String donationDate;

    public Donation() {
        // Default constructor required for Firebase
    }

    public Donation(String recipientUid, String recipientName, String donationDate) {
        this.recipientUid = recipientUid;
        this.recipientName = recipientName;
        this.donationDate = donationDate;
    }

    public String getRecipientUid() {
        return recipientUid;
    }

    public void setRecipientUid(String recipientUid) {
        this.recipientUid = recipientUid;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }
}
