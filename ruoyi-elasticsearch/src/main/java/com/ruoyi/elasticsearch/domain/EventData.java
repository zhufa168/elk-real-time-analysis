package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class EventData {
    private String SignatureStatus;
    private String company;
    private String NewTheadId;
    private String Description;
    private String LogonGuid;
    private String User;
    private String EventType;
    private String IntergrityLevel;
    private String TerminalSessionId;
    private String ParentUser;
    private String Product;
    private String CreationUtcTime;
    private String StartAddress;
    private String Archived;
    private String TargetUser;
    private String FileVersion;
    private String TargetImage;
    private String CallTrace;
    private String LogonId;
    private String TargetProcessGUID;
    private String GrantedAccess;
    private String Signed;
    private String StartModule;
    private String TargetObject;
    private String SourcePortName;
    private String PreviousCreationUtcTime;
    private String Detail;
    private String TargetProcessId;
    private String SourceUser;
    private String Signature;
    private String StartFunction;
    private String TargetProcessGuid;
}
