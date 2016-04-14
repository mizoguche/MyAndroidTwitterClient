package info.mizoguche.mytwitterclient.domain.entity

class Tweet {
    val text: String
        get
    val username: String
        get
     constructor(text: String, username: String){
         this.text = text
         this.username = username
     }
}

