package com.okcoin.commons.okex.open.api.test.account;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.test.BaseTests;

/**
 * Account api basetests
 *
 * @author hucj
 * @version 1.0.0
 * @date 2018/7/04 18:23
 */

public class AccountAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com");


     //   config.setApiKey("");
      //  config.setSecretKey("");
      //  config.setPassphrase("");



        config.setApiKey("4a36cf7e-f115-4c8c-ad60-bcc46c6d613c");
        config.setSecretKey("9E9FB0BB6F935259ED507E514D24291C");
        config.setPassphrase("ok1314213142");



        config.setPrint(true);
        config.setI18n(I18nEnum.KOREA);

        return config;
    }


}
