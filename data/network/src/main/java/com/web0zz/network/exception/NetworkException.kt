package com.web0zz.network.exception

import java.io.IOException

class ApiException(message: String) : IOException(message)

class NotInternetException(message: String) : IOException(message)