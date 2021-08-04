package com.web0zz.features.auth.domain.utils

fun String.isAlphaNumeric() = matches("[a-zA-Z0-9]+".toRegex())
