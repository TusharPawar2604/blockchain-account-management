package org.hyperledger.fabric.samples.account;

import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;

@Contract(
        name = "account",
        info = @Info(
                title = "Account Management",
                description = "Blockchain based account management",
                version = "1.0"
        )
)
@Default
public final class AccountContract implements ContractInterface {

    private final Genson genson = new Genson();

    private enum AccountContractErrors {
        ACCOUNT_NOT_FOUND,
        ACCOUNT_ALREADY_EXISTS
    }

    /**
     * Create a new account on the ledger.
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Account CreateAccount(
            final Context ctx,
            final String dealerId,
            final String msisdn,
            final String mpin,
            final double balance,
            final String status,
            final double transAmount,
            final String transType,
            final String remarks) {

        if (AccountExists(ctx, dealerId)) {
            String errorMessage =
                    String.format("Account %s already exists", dealerId);

            throw new ChaincodeException(
                    errorMessage,
                    AccountContractErrors.ACCOUNT_ALREADY_EXISTS.toString());
        }

        Account account = new Account(
                dealerId,
                msisdn,
                mpin,
                balance,
                status,
                transAmount,
                transType,
                remarks);

        String accountJSON = genson.serialize(account);

        ctx.getStub().putStringState(dealerId, accountJSON);

        return account;
    }

    /**
     * Check whether an account exists on the ledger.
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean AccountExists(
            final Context ctx,
            final String dealerId) {

        String accountJSON =
                ctx.getStub().getStringState(dealerId);

        return accountJSON != null && !accountJSON.isEmpty();
    }

    /**
     * Read an account from the ledger.
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Account ReadAccount(
            final Context ctx,
            final String dealerId) {

        String accountJSON =
                ctx.getStub().getStringState(dealerId);

        if (accountJSON == null || accountJSON.isEmpty()) {

            String errorMessage =
                    String.format("Account %s does not exist", dealerId);

            throw new ChaincodeException(
                    errorMessage,
                    AccountContractErrors.ACCOUNT_NOT_FOUND.toString());
        }

        return genson.deserialize(accountJSON, Account.class);
    }

    /**
     * Update an existing account on the ledger.
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Account UpdateAccount(
            final Context ctx,
            final String dealerId,
            final String msisdn,
            final String mpin,
            final double balance,
            final String status,
            final double transAmount,
            final String transType,
            final String remarks) {

        if (!AccountExists(ctx, dealerId)) {

            String errorMessage =
                    String.format("Account %s does not exist", dealerId);

            throw new ChaincodeException(
                    errorMessage,
                    AccountContractErrors.ACCOUNT_NOT_FOUND.toString());
        }

        Account updatedAccount = new Account(
                dealerId,
                msisdn,
                mpin,
                balance,
                status,
                transAmount,
                transType,
                remarks);

        String accountJSON = genson.serialize(updatedAccount);

        ctx.getStub().putStringState(dealerId, accountJSON);

        return updatedAccount;
    }

   /**
 * Retrieve all accounts from the world state.
 */
@Transaction(intent = Transaction.TYPE.EVALUATE)
public String GetAllAccounts(final Context ctx) {

    QueryResultsIterator<KeyValue> results =
            ctx.getStub().getStateByRange("", "");

    List<Account> accounts = new ArrayList<>();

    for (KeyValue result : results) {

        String accountJSON = result.getStringValue();

        if (accountJSON != null && !accountJSON.isEmpty()) {

            Account account =
                    genson.deserialize(accountJSON, Account.class);

            accounts.add(account);
        }
    }

    return genson.serialize(accounts);
}

    /**
     * Retrieve the transaction history of an account.
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAccountHistory(
            final Context ctx,
            final String dealerId) {

        if (!AccountExists(ctx, dealerId)) {

            String errorMessage =
                    String.format("Account %s does not exist", dealerId);

            throw new ChaincodeException(
                    errorMessage,
                    AccountContractErrors.ACCOUNT_NOT_FOUND.toString());
        }

        QueryResultsIterator<KeyModification> history =
                ctx.getStub().getHistoryForKey(dealerId);

        List<String> historyResults = new ArrayList<>();

        for (KeyModification modification : history) {

            String historyRecord =
                    "{"
                    + "\"txId\":\"" + modification.getTxId() + "\","
                    + "\"value\":" + modification.getStringValue() + ","
                    + "\"isDeleted\":" + modification.isDeleted()
                    + "}";

            historyResults.add(historyRecord);
        }

        return "[" + String.join(",", historyResults) + "]";
    }
}