package user.card.generator.batch.transaction;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.Transaction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

    private List<Transaction> transactions;

    public TransactionBatchPreparedStatementSetter(List<Transaction> transactions) {
        super();
        this.transactions = transactions;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
        Transaction transaction = transactions.get(i);
        preparedStatement.setString(1, transaction.getCardNumber());
//        preparedStatement.setString(2, String.valueOf(transaction.getTransactionType().getValue()));
        preparedStatement.setString(3, String.valueOf(transaction.getTimestamp()));
        preparedStatement.setString(4, String.valueOf(transaction.getAmount()));
        preparedStatement.setString(5, transaction.getCurrencyName());
//        preparedStatement.setString(6, String.valueOf(transaction.getResponseCode().getValue()));
        preparedStatement.setString(7, transaction.getCountryName());
        preparedStatement.setString(8, transaction.getVendorCode());
        preparedStatement.setString(9, String.valueOf(transaction.getField1()));
        preparedStatement.setString(10, String.valueOf(transaction.getField2()));
        preparedStatement.setDouble(11, transaction.getField3());
        preparedStatement.setDouble(12, transaction.getField4());
        preparedStatement.setDouble(13, transaction.getField5());
        preparedStatement.setDouble(14, transaction.getField6());
        preparedStatement.setDouble(15, transaction.getField7());
        preparedStatement.setDouble(16, transaction.getField8());
        preparedStatement.setDouble(17, transaction.getField9());
        preparedStatement.setDouble(18, transaction.getField10());
        preparedStatement.setDouble(19, transaction.getField11());
        preparedStatement.setDouble(20, transaction.getField12());
        preparedStatement.setDouble(21, transaction.getField13());
        preparedStatement.setDouble(22, transaction.getField14());
        preparedStatement.setDouble(23, transaction.getField15());
        preparedStatement.setDouble(24, transaction.getField16());
        preparedStatement.setDouble(25, transaction.getField17());
        preparedStatement.setDouble(26, transaction.getField18());
        preparedStatement.setDouble(27, transaction.getField19());
        preparedStatement.setDouble(28, transaction.getField20());
        preparedStatement.setDouble(29, transaction.getField21());
        preparedStatement.setDouble(30, transaction.getField22());
        preparedStatement.setDouble(31, transaction.getField23());
        preparedStatement.setDouble(32, transaction.getField24());
        preparedStatement.setDouble(33, transaction.getField25());
        preparedStatement.setString(34, String.valueOf(transaction.getField26()));
        preparedStatement.setString(35, String.valueOf(transaction.getField27()));
        preparedStatement.setString(36, String.valueOf(transaction.getField28()));
        preparedStatement.setString(37, String.valueOf(transaction.getField29()));
        preparedStatement.setString(38, String.valueOf(transaction.getField30()));
        preparedStatement.setString(39, String.valueOf(transaction.getField31()));
        preparedStatement.setString(40, String.valueOf(transaction.getField32()));
        preparedStatement.setString(41, String.valueOf(transaction.getField33()));
        preparedStatement.setString(42, String.valueOf(transaction.getField34()));
        preparedStatement.setString(43, String.valueOf(transaction.getField35()));
        preparedStatement.setString(44, String.valueOf(transaction.getField36()));
        preparedStatement.setString(45, String.valueOf(transaction.getField37()));
        preparedStatement.setString(46, String.valueOf(transaction.getField38()));
        preparedStatement.setString(47, String.valueOf(transaction.getField39()));
        preparedStatement.setString(48, String.valueOf(transaction.getField40()));
        preparedStatement.setString(49, String.valueOf(transaction.isFraud() ? 1 : 0));

    }

    @Override
    public int getBatchSize() {
        return transactions.size();
    }

}
