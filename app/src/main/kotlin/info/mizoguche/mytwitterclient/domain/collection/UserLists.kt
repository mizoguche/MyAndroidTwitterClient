package info.mizoguche.mytwitterclient.domain.collection

import info.mizoguche.mytwitterclient.domain.entity.UserList

class UserLists(userLists: List<UserList>) : List<UserList> by userLists {
}
