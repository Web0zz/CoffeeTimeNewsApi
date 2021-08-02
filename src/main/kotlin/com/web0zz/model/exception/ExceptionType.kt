package com.web0zz.model.exception

class UnAuthorizedAccessException(override val message: String) : Exception(message)

class BadRequestException(override val message: String) : Exception(message)
