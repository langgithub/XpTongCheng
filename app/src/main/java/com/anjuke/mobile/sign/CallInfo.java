package com.anjuke.mobile.sign;

public class CallInfo {

    private String callTypeStr;
    private String callName;
    private String callNumber;
    private String callDurationStr;
    private String callDateStr;


    public String getCallTypeStr() {
        return callTypeStr;
    }

    public void setCallTypeStr(String callTypeStr) {
        this.callTypeStr = callTypeStr;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallDurationStr() {
        return callDurationStr;
    }

    public void setCallDurationStr(String callDurationStr) {
        this.callDurationStr = callDurationStr;
    }

    public String getCallDateStr() {
        return callDateStr;
    }

    public void setCallDateStr(String callDateStr) {
        this.callDateStr = callDateStr;
    }

    @Override
    public String toString() {
        return "CallInfo{" +
                "callTypeStr='" + callTypeStr + '\'' +
                ", callName='" + callName + '\'' +
                ", callNumber='" + callNumber + '\'' +
                ", callDurationStr='" + callDurationStr + '\'' +
                ", callDateStr='" + callDateStr + '\'' +
                '}';
    }
}
