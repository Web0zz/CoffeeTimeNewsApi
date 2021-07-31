package com.web0zz.utils

fun String.isAlphaNumeric() = matches("[a-zA-Z0-9]+".toRegex())