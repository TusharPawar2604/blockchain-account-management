package org.hyperledger.fabric.samples.account;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;


import java.util.Objects;
import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Account {

    @Property()
    private final String dealerId;

    @Property()
    private final String msisdn;

    @Property()
    private final String mpin;

    @Property()
    private final double balance;

    @Property()
    private final String status;

    @Property()
    private final double transAmount;

    @Property()
    private final String transType;

    @Property()
    private final String remarks;



    public Account(
            @JsonProperty("dealerId") final String dealerId,
            @JsonProperty("msisdn") final String msisdn,
            @JsonProperty("mpin") final String mpin,
            @JsonProperty("balance") final double balance,
            @JsonProperty("status") final String status,
            @JsonProperty("transAmount") final double transAmount,
            @JsonProperty("transType") final String transType,
            @JsonProperty("remarks") final String remarks) {

        this.dealerId = dealerId;
        this.msisdn = msisdn;
        this.mpin = mpin;
        this.balance = balance;
        this.status = status;
        this.transAmount = transAmount;
        this.transType = transType;
        this.remarks = remarks;
    }


    public String getDealerId() {
        return dealerId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getMpin() {
        return mpin;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public String getTransType() {
        return transType;
    }

    public String getRemarks() {
        return remarks;
    }



    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Account other = (Account) obj;

        return Objects.equals(dealerId, other.dealerId)
                && Objects.equals(msisdn, other.msisdn)
                && Objects.equals(mpin, other.mpin)
                && Double.compare(balance, other.balance) == 0
                && Objects.equals(status, other.status)
                && Double.compare(transAmount, other.transAmount) == 0
                && Objects.equals(transType, other.transType)
                && Objects.equals(remarks, other.remarks);
    }


   
    @Override
    public int hashCode() {

        return Objects.hash(
                dealerId,
                msisdn,
                mpin,
                balance,
                status,
                transAmount,
                transType,
                remarks
        );
    }



    @Override
    public String toString() {

        return "Account{"
                + "dealerId='" + dealerId + '\''
                + ", msisdn='" + msisdn + '\''
                + ", mpin='" + mpin + '\''
                + ", balance=" + balance
                + ", status='" + status + '\''
                + ", transAmount=" + transAmount
                + ", transType='" + transType + '\''
                + ", remarks='" + remarks + '\''
                + '}';
    }
}