package com.connect.ansh0.connectmany;

public class chatOnePost {
   public String ChatImages;
   public String ChatName;
   public String ChatText;
   public String idChat;
   public String emailChat;
    chatOnePost(){

    }

    public chatOnePost(String chatImages, String chatName, String chatText, String idChat, String emailChat) {
        ChatImages = chatImages;
        ChatName = chatName;
        ChatText = chatText;
        this.idChat = idChat;
        this.emailChat = emailChat;
    }
}
