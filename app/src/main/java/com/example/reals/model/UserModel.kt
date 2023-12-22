package com.example.reals.model

data class UserModel(
    var id:String = "",
    var email:String = "",
    var username:String = "",
    var profilePic:String = "",
    var followersList:MutableList<String> = mutableListOf(),
    var followingList:MutableList<String> = mutableListOf()
)
