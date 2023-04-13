package com.example.loginandregistration

import com.google.common.truth.Truth.assertThat
import com.mistershorr.loginandregistration.RegistrationUtil
import org.junit.Test

class RegistrationUtilTest {
    //methodName_someCondition_expectedResult
    @Test
    fun validatePassword_emptyPassword_isFalse(){
        val actual = RegistrationUtil.validatePassword("", "")
        //assert that thea actual value is equal to the desired value
        assertThat(actual).isFalse()
    }
    @Test
    fun validatePassword_passwordsDontMatch_isFalse(){
        val actual = RegistrationUtil.validatePassword("A1bcdefgh", "1Abcdefgh")
        assertThat(actual).isFalse()
    }
}