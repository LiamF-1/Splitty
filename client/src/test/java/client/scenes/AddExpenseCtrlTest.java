package client.scenes;

import org.junit.jupiter.api.BeforeEach;
import utils.TestServerUtils;
import utils.TestWebsocket;


public class AddExpenseCtrlTest {

    AddExpenseCtrl ctrl;

    TestServerUtils server = new TestServerUtils();

    @BeforeEach
    void setUp() {
        MainCtrl mainCtrl = new MainCtrl(new TestWebsocket());
        ctrl = new AddExpenseCtrl(server, mainCtrl);
    }


}