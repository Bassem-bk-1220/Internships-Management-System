package com.example.myapplication;



import com.example.myapplication.Activity.UpdateProfilCompanyActivity;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

public class UpdateProfileCompanyTest {
    @Test
    public void testIsEmailValid() {
        String testEmail = "anupamchugh@gmail.com";
        Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail), UpdateProfilCompanyActivity.checkEmailForValidity(testEmail), is(true));
    }
}
