package com.mistershorr.loginandregistration

import com.mistershorr.loginandregistration.RegistrationUtil.existingUsers

// object keyword makes it so all the functions are
// static functions
object RegistrationUtil {
    // use this in the test class for the is username taken test
    // make another similar list for some taken emails
    var existingUsers = listOf("cosmicF", "cosmicY", "bob", "alice")
//    you can use listOf<type>() instead of making the list & adding individually
//    List<String> blah = new ArrayList<String>();
//    blah.add("hi")
//    blah.add("hello")


    // isn't empty
    // already taken
    // minimum number of characters is 3
    fun validateUser(username: String): Boolean {
        val userPattern = """.{3,}""".toRegex()
        return userPattern.matches(username) &&
                !existingUsers.contains(username)
    }

    // make sure meets security requirements (deprecated ones that are still used everywhere)
    // min length 8 chars
    // at least one digit
    // at least one capital letter
    // both passwords match
    // not empty
    fun validatePassword(passwordOriginal: String, passwordConfirm: String): Boolean {
        // check if password has at least 8 characters, if not return false
        val eightCharacters = """.{8,}""".toRegex()
        if (!eightCharacters.matches(passwordOriginal)) return false
        // check if password has a digit, if not return false
        val oneDigit = """\d""".toRegex()
        if (!oneDigit.containsMatchIn(passwordOriginal)) return false
        // check if password has a capital letter
        val capitalLetter = """[A-Z]""".toRegex()
        if (!capitalLetter.containsMatchIn(passwordOriginal)) return false
        // check if passwords match
        if (passwordOriginal != passwordConfirm) return false
        // all conditions are met
        return true
    }

    // isn't empty
    fun validateName(name: String) : Boolean {
        return name.isNotEmpty()
    }

    // isn't empty
    // make sure the email isn't used
    // make sure it's in the proper email format user@domain.tld
    fun validateEmail(email: String) : Boolean {
        val emailPattern = """^[a-z0-9._]+@[a-z]+[.][a-z]+$""".toRegex()
        return emailPattern.matches(email.lowercase())
    }
}