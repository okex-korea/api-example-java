import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.Transfer;
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.SpotAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.SpotOrderAPIServive;
import com.okcoin.commons.okex.open.api.service.spot.SpotProductAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotAccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotOrderApiServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotProductAPI;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotProductAPIServiceImpl;
import com.okcoin.commons.okex.open.api.test.account.AccountAPITests;
import com.okcoin.commons.okex.open.api.test.spot.SpotAccountAPITest;
import com.okcoin.commons.okex.open.api.test.spot.SpotOrderAPITest;
import com.okcoin.commons.okex.open.api.test.spot.SpotProductAPITest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class sampleTest {


    //API(Wallet, Token Trading) 사용에 필요한 Service 단들

    private AccountAPIService accountAPIService;
    private SpotAccountAPIService spotAccountAPIService;
    private SpotOrderAPIServive spotOrderAPIServive;
    private SpotProductAPIService spotProductAPIService;


    //
    //기본 환경 세팅, okex 에 가입후 apikey, secret key, Passphrase를 짛어넣어야 됨
    //

    @Before
    public void before() {

        APIConfiguration config = new APIConfiguration();


        config.setEndpoint("https://okex.co.kr");
        config.setApiKey("");
        config.setSecretKey("");
        config.setPassphrase("");

        config.setPrint(true);
        config.setI18n(I18nEnum.KOREA);


        this.accountAPIService = new AccountAPIServiceImpl(config);
        this.spotAccountAPIService = new SpotAccountAPIServiceImpl(config);
        this.spotOrderAPIServive = new SpotOrderApiServiceImpl(config);
        this.spotProductAPIService = new SpotProductAPIServiceImpl(config);



    }

    @Test
    //GET Currencies 부분
    //api/account/v3/currencies
    //Private
    public void getCurrencies() {
        List<Currency> result = this.accountAPIService.getCurrencies();

        for(int i = 0; i < result.size(); i++) {
            System.out.println("The result is : " + result.get(i));
        }
    }

    @Test
    //Wallet information, Wallet of a Currency 부분
    //GET /api/account/v3/wallet
    //GET /api/account/v3/wallet/<currency>
    //Private
    public void getWallet() {
        List<Wallet> result = this.accountAPIService.getWallet();

        for(int i = 0; i < result.size(); i++) {
            System.out.println("The result is : " + result.get(i));
        }

        List<Wallet> result2 = this.accountAPIService.getWallet("btc");

        for(int i = 0; i < result2.size(); i++) {
            System.out.println("The result is : " + result2.get(i));
        }

    }
    @Test

    //Funds Transfer 부분
    //private
    //34008 : 您的余额不足 Balance가 불충
    public void transfer() {
        Transfer transfer = new Transfer();
        transfer.setFrom(1);
        transfer.setTo(6);
        transfer.setCurrency("eos");
        transfer.setAmount(BigDecimal.valueOf(0.0001));
        JSONObject result = this.accountAPIService.transfer(transfer);
       // this.toResultString(AccountAPITests.LOG, "result", result);
    }


    @Test
    //Withdrawal 부분
    //private
    //您需设置资金密码后，才能提现 Password setting이 안되어 있다고 나옴
    public void withdraw() {
        Withdraw withdraw = new Withdraw();
        withdraw.setTo_address("17DKe3kkkkiiiiTvAKKi2vMPbm1Bz3CMKw");
        withdraw.setFee(new BigDecimal("0.00005"));
        withdraw.setCurrency("btc");
        withdraw.setAmount(BigDecimal.ONE);
        withdraw.setDestination(4);
        withdraw.setTrade_pwd("123456");
        JSONObject result = this.accountAPIService.withdraw(withdraw);
       // this.toResultString(AccountAPITests.LOG, "result", result);

        System.out.println(result);

    }

    @Test
    //Withdrawal Fees 부분
    //private
    public void getWithdrawFee() {
        List<WithdrawFee> result = this.accountAPIService.getWithdrawFee("btc");
        //this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    //Recent Withdrawal History
    //관련 API 콜하는 부분이 없어 AcountAPI부분(AccountAPI,ServiceImpl,Service) 부분에 추가
    //private
    public void getWithdrawalHistory(){

        this.accountAPIService.getWithdrawalHistory();

    }

    @Test
    //Recent Withdrawal History of a Currency
    //관련 API 콜하는 부분이 없어 AcountAPI부분(AccountAPI,ServiceImpl,Service) 부분에 추가
    public void getWithdrawalHistoryCurrency(){

        this.accountAPIService.getWithdrawalHistory("btc");

    }

    @Test
    //Bills Details
    //private
    public void getLedger() {
        List<Ledger> result = this.accountAPIService.getLedger(2, "btc",null, null, 10);
    }


    @Test
    //Get Deposit Address
    //private
    public void getDepositAddress() {
        JSONArray result = this.accountAPIService.getDepositAddress("btc");
    }

    @Test
    //Get Deposit History of All Currencies
    //Get Deposit History of a Currency
    //private

    public void getDepositHistory() {
        JSONArray result = this.accountAPIService.getDepositHistory();
       // this.toResultString(AccountAPITests.LOG, "result", result);


        JSONArray result2 = this.accountAPIService.getDepositHistory("btc");
       // this.toResultString(AccountAPITests.LOG, "result", result2);
    }


    //For 'the Token Trading' from here
    //For the Token Trading from here
    //For the Token Trading from here
    //For 'the Token Trading' from here

    @Test
    //Spot Trading Account
    //private
    public void getSpotTradingAccount(){
        final List<Account> accounts = this.spotAccountAPIService.getAccounts();
    }


    //Spot Trading Account of a Currency
    //private
    //Status: 200
    //		Message:
    //		Body: {"available":"0","balance":"0","currency":"BTC","hold":"0","id":"8793931"}
    @Test
    public void getAccountByCurrency() {
        final Account account = this.spotAccountAPIService.getAccountByCurrency("btc");

    }
    //Bills Details
    //private
    @Test
    public void getLedgersByCurrency() {
        spotAccountAPIService.getLedgersByCurrency("usdt", null, null, "2");

    }
    //Place an Order
    //private
    @Test
    //400 error  参数值填写错误
    public void addOrder() {
      //  for (int i = 0; i < 1; i++) {
       //     final long st = System.currentTimeMillis();

            final PlaceOrderParam order = new PlaceOrderParam();
            order.setClient_oid("09C76AF184AA42AD");
            order.setType("limit");
            order.setSide("sell");
            order.setInstrument_id("AUTO-KRW");
            order.setMargin_trading("1");
            order.setOrder_type("0");
            order.setPrice("21000.0");
            order.setSize("0.001");
            final OrderResult orderResult = this.spotOrderAPIServive.addOrder(order);

    }

    @Test
    //Place Multiple Orders
    //private
    //400 参数值填写错误
    public void batchAddOrder() {
        final List<PlaceOrderParam> list = new ArrayList<>();

        final PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("20180918");
        order.setInstrument_id("btc-usdt");
        order.setPrice("10001");
        order.setType("limit");
        order.setSide("sell");
        order.setSize("0.01");
        list.add(order);

        final PlaceOrderParam order1 = new PlaceOrderParam();
        order1.setClient_oid("20180917");
        order1.setInstrument_id("btc-usdt");
        order1.setPrice("10002");
        order1.setType("limit");
        order1.setSide("sell");
        order1.setSize("0.01");
        list.add(order1);

        final Map<String, List<OrderResult>> orderResult = this.spotOrderAPIServive.addOrders(list);
      //  this.toResultString(SpotOrderAPITest.LOG, "orders", orderResult);
    }

    //Cancel an Order
    //private
    @Test
    //400 订单不存在
    public void cancleOrderByOrderId_post() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc-usdt");
        order.setClient_oid("1234545");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByOrderId_post(order, "1644664675964928");
        //this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
    }


    //Cancel All Orders
    @Test
    //400 两个参数必须填一个
    public void batchCancleOrders_post() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();

        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("btc-usdt");
        final List<Long> order_ids = new ArrayList<>();
//        order_ids.add(1600593327162368L);
//        order_ids.add(1600593327162369L);
//        order_ids.add(1600593327162364L);
//        order_ids.add(1600593327162363L);
//        order_ids.add(1600593327162362L);
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

//        final OrderParamDto dto1 = new OrderParamDto();
//        dto1.setInstrument_id("etc_usdt");
//        cancleOrders.add(dto1);

        final Map<String, BatchOrdersResult> orderResult = this.spotOrderAPIServive.cancleOrders_post(cancleOrders);
      //  this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    //Get Order List
    //private
    @Test
    public void getOrders() {
        final List<OrderInfo> orderInfoList = this.spotOrderAPIServive.getOrders("Btc-usdT", "all", null, null, "2");
        //this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }
    //Get All Open Orders
    //private
    @Test
    public void getPendingOrders() {
        final List<OrderInfo> orderInfoList = this.spotOrderAPIServive.getPendingOrders(null, null, "3", null);
      //  this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    //Get Order Details
    //private

    @Test
    //400 订单不存在
    public void getOrderByOrderId() {
        final OrderInfo orderInfo = this.spotOrderAPIServive.getOrderByOrderId("bTC-USDT", "1673831663603712");
        //this.toResultString(SpotOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    //Get Transaction Details
    //private
    @Test
    public void getFills() {
        final List<Fills> fillsList = this.spotOrderAPIServive.getFills("23852", "btc-usdt", null, null, "2");
        //this.toResultString(SpotOrderAPITest.LOG, "fillsList", fillsList);
    }

    //Get Token Pair Details
    ////Public
    @Test
    // [{
    //"instrument_id": "BTC-USDT",
    //
    //"base_currency": "BTC",
    //
    //"quote_currency": "USDT",
    //
    //"min_size": "0.001",
    //
    //"size_increment": "0.00000001",
    //
    //"tick_size": "0.0001"
    //}]
    public void getProducts() {
        final List<Product> products = this.spotProductAPIService.getProducts();
        //this.toResultString(SpotProductAPITest.LOG, "products", products);
    }


    //Get Order Books
    // //Public
   // Message:
    //Body: {"asks":[["3873.8","0.41904234","1"],["3874.3","0.994","1"]...
    @Test
    public void bookProductsByProductId() {
        String[] currencName = new String[] {"auto_krw", "btc_krw"}; //"auto_krw","btc_krw","eth_krw"
        for (int i = 0; i < currencName.length; i++) {
            try {
                final Book book = this.spotProductAPIService.bookProductsByProductId(currencName[i], "10", null);   //BTC-USDT,
            }catch (Exception e) {
                e.printStackTrace();
            }
         //   this.toResultString(SpotProductAPITest.LOG, "book", book);
            System.out.println("==========i=" + i);
            try {
                Thread.sleep(400);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //Get All Token Pairs Information
    //Public
    //Mesaage:
    //Body: [{"ask":"0.014533","base_volume_24h":"157459.931391","best_ask":"0.014533","best_bid":"0.014525"...

    @Test
    public void getTickers() {
        final List<Ticker> tickers = this.spotProductAPIService.getTickers();
        //this.toResultString(SpotProductAPITest.LOG, "tickers", tickers);

    }


    //Get a Token Pair Information
    //Public
    //Message:
    //Body: {"ask":"3873.1","base_volume_24h":"20198.73883186","best_ask":"3873.1","best_bid":"3872.7"
    @Test

    public void getTickerByProductId() {
        final Ticker ticker = this.spotProductAPIService.getTickerByProductId("btc-USDt");
       // this.toResultString(SpotProductAPITest.LOG, "ticker", ticker);
    }

    //Get Filled Orders Information
    //Public
    //Message:
    //Body: [{"price":"3876.9","side":"sell","size":"0.00218","time":"2019-03-13T06:07:15.873Z"...

    @Test
    public void getTrades() {
        final List<Trade> trades = this.spotProductAPIService.getTrades("btc-USDt", null, null, "2");
       // this.toResultString(SpotProductAPITest.LOG, "trades", trades);
    }

    //Get Market Data
    //Public
    //Message:
    //Body: [["2019-03-13T06:10:00.000Z","3878.8","3879.6","3878.5","3878.8","3.68032449"],
    @Test
    public void getCandles() {
        this.spotProductAPIService.getCandles_1("BTC-usdt", "60", null, null);
       // this.toResultString(SpotProductAPITest.LOG, "klines", klines);
    }



}
