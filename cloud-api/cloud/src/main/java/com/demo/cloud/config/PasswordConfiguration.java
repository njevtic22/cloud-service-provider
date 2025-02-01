package com.demo.cloud.config;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class PasswordConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordValidator passwordValidator(@Value("${passay.messages.properties}") File passayMessages) throws IOException {
        PropertiesMessageResolver resolver = getResolver(passayMessages);
        return new PasswordValidator(
                resolver,
                new LengthRule(8, 50),
                // At least one upper case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // At least one lower case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // At least one digit
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // At least one special character
                // ! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~ ¡ ¢ £ ¤ ¥ ¦ § ¨ © ª « ¬ ­ ® ¯ ° ± ² ³ ´ µ ¶ · ¸ ¹ º » ¼ ½ ¾ ¿ × ÷ – — ― ‗ ‘ ’ ‚ ‛ “ ” „ † ‡ • … ‰ ′ ″ ‹ › ‼ ‾ ⁄ ⁊ ₠ ₡ ₢ ₣ ₤ ₥ ₦ ₧ ₨ ₩ ₪ ₫ € ₭ ₮ ₯ ₰ ₱ ₲ ₳ ₴ ₵ ₶ ₷ ₸ ₹ ₺ ₻ ₼ ₽ ₾
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );
    }

    private PropertiesMessageResolver getResolver(File passayMessages) throws IOException {
        Properties messagesProp = new Properties();
        BufferedReader in = new BufferedReader(new FileReader(passayMessages));
        messagesProp.load(in);
        in.close();
        return new PropertiesMessageResolver(messagesProp);
    }
}
