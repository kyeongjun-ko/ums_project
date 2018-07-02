package com.bccns.umsserviceweb.common.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * AccountPortal 에 요청해서 받은 회원정보 XML 데이터를 담을 Java 객체입니다.
 */
@XmlRootElement(name = "BasicInfoVO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"gUID", "emailID", "phoneID", "countryCallingCode", "birthDate", "nationality", "title", "firstName", "lastName", "affiliation", "postalCode", "province", "city", "address1", "address2", "address3", "phoneNumber", "homePhoneNumber", "newsLetterReceive", "smsReceiveYNFlag", "customDefinedFieldA", "customDefinedFieldB"})
public class BasicInfoVO implements Serializable {

    private static final long serialVersionUID = 1931764534290588814L;

    @XmlElement(name = "gUID")
    private String gUID = "";

    @XmlElement(name = "emailID")
    private String emailID = "";

    @XmlElement(name = "phoneID")
    private String phoneID;

    @XmlElement(name = "countryCallingCode")
    private String countryCallingCode;
    
    @XmlElement(name = "birthDate")
    private String birthDate = "";
    
    @XmlElement(name = "nationality")
    private String nationality = "";

    @XmlElement(name = "title")
    private String title = "";
    
    @XmlElement(name = "firstName")
    private String firstName = "";
    
    @XmlElement(name = "lastName")
    private String lastName = "";

    @XmlElement(name = "affiliation")
    private String affiliation = "";
    
    @XmlElement(name = "postalCode")
    private String postalCode = "";
    
    @XmlElement(name = "province")
    private String province = "";
    
    @XmlElement(name = "city")
    private String city = "";
    
    @XmlElement(name = "address1")
    private String address1 = "";
    
    @XmlElement(name = "address2")
    private String address2 = "";
    
    @XmlElement(name = "address3")
    private String address3 = "";

    @XmlElement(name = "phoneNumber")
    private String phoneNumber = "";            // 휴대폰 번호

    @XmlElement(name = "homePhoneNumber")
    private String homePhoneNumber = "";        // 집전화 번호

    @XmlElement(name = "newsLetterReceive")
    private String newsLetterReceive = "";

    @XmlElement(name = "smsReceiveYNFlag")
    private String smsReceiveYNFlag = "";

    @XmlElement(name = "customDefinedFieldA")
    private String customDefinedFieldA = "";

    @XmlElement(name = "customDefinedFieldB")
    private String customDefinedFieldB = "";

    public String getgUID() {
        return gUID;
    }

    public void setgUID(String gUID) {
        this.gUID = gUID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getNewsLetterReceive() {
        return newsLetterReceive;
    }

    public void setNewsLetterReceive(String newsLetterReceive) {
        this.newsLetterReceive = newsLetterReceive;
    }

    public String getSmsReceiveYNFlag() {
        return smsReceiveYNFlag;
    }

    public void setSmsReceiveYNFlag(String smsReceiveYNFlag) {
        this.smsReceiveYNFlag = smsReceiveYNFlag;
    }

    public String getCustomDefinedFieldA() {
        return customDefinedFieldA;
    }

    public void setCustomDefinedFieldA(String customDefinedFieldA) {
        this.customDefinedFieldA = customDefinedFieldA;
    }

    public String getCustomDefinedFieldB() {
        return customDefinedFieldB;
    }

    public void setCustomDefinedFieldB(String customDefinedFieldB) {
        this.customDefinedFieldB = customDefinedFieldB;
    }
    
}
