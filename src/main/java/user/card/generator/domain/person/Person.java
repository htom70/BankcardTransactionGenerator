package user.card.generator.domain.person;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String cardNumber;

    private PersonCategory personCategory;

    private int income;

    @OneToMany(fetch = FetchType.EAGER)
    protected Set<Transaction> transactions = new HashSet<>();

    public Person(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Person() {
    }

    public Person(String cardNumber, PersonCategory personCategory,int income) {
        this.cardNumber = cardNumber;
        this.personCategory = personCategory;
        this.income = income;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public PersonCategory getPersonType() {
        return personCategory;
    }

    public void setPersonType(PersonCategory personCategory) {
        this.personCategory = personCategory;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransactionsToPerson(List<Transaction> transactionsFromGenerate) {
        this.transactions.addAll(transactionsFromGenerate);
    }

    @Override
    public String toString() {
        return "Person " + "id= " + id + ", cardNumber= '" + cardNumber;
    }
}
