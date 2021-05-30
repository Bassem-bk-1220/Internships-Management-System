package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.Activity.LoginActivity;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginUnitTest {
    private static final String ERROR_EMAIL_STRING = "please provide your email";
    private static final String ERROR_PASSWORD_STRING = "please provide your password";
    private static final String SUCCESSFULL_OGIN_STRING = "the email and password correct";
    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {

        LoginActivity myObjectUnderTest = new LoginActivity();

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate_sign("","123456");

        // ...then the result should be the expected one.
        assertThat(result, is(ERROR_EMAIL_STRING));
       result = myObjectUnderTest.validate_sign("bassem.bk@outlook.fr","");

        // ...then the result should be the expected one.
        assertThat(result, is(ERROR_PASSWORD_STRING));
         result = myObjectUnderTest.validate_sign("bassem.bk@outlook.fr","123456");

        // ...then the result should be the expected one.
        assertThat(result, is(SUCCESSFULL_OGIN_STRING));
    }
}
