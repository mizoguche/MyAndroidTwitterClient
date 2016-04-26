package info.mizoguche.mytwitterclient.domain.collection

import info.mizoguche.mytwitterclient.domain.entity.User

class Users(users: List<User>) : List<User> by users
