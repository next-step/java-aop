package samples;

import com.interface21.transaction.PlatformTransactionManager;

public class FakeTransactionManager implements PlatformTransactionManager {

    private TransactionState state;
    private boolean began = false;
    private boolean commited = false;
    private boolean rollbacked = false;

    @Override
    public void begin() {
        this.state = TransactionState.BEGIN;
        this.began = true;
    }

    @Override
    public void commit() {
        this.state = TransactionState.COMMIT;
        this.commited = true;
    }

    @Override
    public void rollback() {
        this.state = TransactionState.ROLLBACK;
        this.rollbacked = true;
    }

    public TransactionState getState() {
        return this.state;
    }

    public boolean isBegan() {
        return this.began;
    }

    public boolean isCommited() {
        return this.commited;
    }

    public boolean isRollBacked() {
        return this.rollbacked;
    }
}
