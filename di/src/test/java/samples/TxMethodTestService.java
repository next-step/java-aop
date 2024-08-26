package samples;

import com.interface21.transaction.annotation.Transactional;

public class TxMethodTestService {

    @Transactional
    public void expectCommit() {
    }

    @Transactional
    public void expectRollBack() {
        throw new RuntimeException();
    }

    public void expectNothing() {
    }

}
