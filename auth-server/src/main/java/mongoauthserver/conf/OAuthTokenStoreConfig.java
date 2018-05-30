package mongoauthserver.conf;

import mongoauthserver.library.MongoTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OAuthTokenStoreConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthTokenStoreConfig.class);

    @Bean
    public TokenStore tokenStore() {
        LOGGER.info("Initializing with Mongo token store ...");
        return new MongoTokenStore();
    }
}
