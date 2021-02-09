package com.example.bankcleancodetest

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class ExtensionsTest {

    @Test
    fun should_return_true_with_cpf(){
        val input = "44064153863"
        val r = input.isCPF()
        assertTrue(r)
    }

    @Test
    fun should_return_false_with_invalid_cpf(){
        val input = "123684"
        val r = input.isCPF()
        assertFalse(r)
    }

    @Test
    fun should_return_false_with_invalid_number(){
        val input = "adada12"
        val r = input.isCPF()
        assertFalse(r)
    }

    @Test
    fun should_return_true_for_valid_email(){
        val input = "edu@123.com"
        val r = input.isEmail()
        assertTrue(r)
    }

    @Test
    fun should_return_false_for_invalid_email(){
        val input = "edu123.com"
        val r = input.isEmail()
        assertFalse(r)
    }

    @Test
    fun should_return_false_for_missing_dot_com(){
        val input = "jose@asdaddada.br"
        val r = input.isEmail()
        assertFalse(r)
    }

    @Test
    fun should_return_valid_password(){
        val input = "Ed.7"
        val r = input.validPassword()
        assertTrue(r)
    }

    @Test
    fun should_return_invalid_password(){
        val input = "1636523145"
        val r = input.validPassword()
        assertFalse(r)
    }

    @Test
    fun should_return_invalid_password2(){
        val input = "Edu1636523145"
        val r = input.validPassword()
        assertFalse(r)
    }

    @Test
    fun should_return_invalid_password3(){
        val input = "Edu@asdedc"
        val r = input.validPassword()
        assertFalse(r)
    }
}