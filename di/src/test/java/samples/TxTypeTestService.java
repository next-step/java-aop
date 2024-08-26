package samples;

import com.interface21.transaction.annotation.Transactional;

@Transactional
public class TxTypeTestService {

    public void expectCommit() {
    }

    public void expectRollBack() {
        throw new RuntimeException();
    }

}
