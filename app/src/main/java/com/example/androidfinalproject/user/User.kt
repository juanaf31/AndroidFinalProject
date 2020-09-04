package com.example.androidfinalproject.user

class User(
    var id: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var fullname: String = "",
    var photo: String = "",
    var bornDate: String = "",
    var phoneNumber: String = "",
    var address: String = "",
    var createdAt: String = "",
    var editedAt: String = "",
    var deletedAt: String = "",
    var status: String = ""
)

class UserWallet(
    var id: String = "",
    var saldo: String = ""
)

class ResponseDataUser(
    var message: String = "",
    var status: String = "",
    var token: String = "",
    var result: Any
)