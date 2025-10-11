public class Quine {
  public static void main(String[] args) {
    String s = "cHVibGljIGNsYXNzIFF1aW5lIHsKICBwdWJsaWMgc3RhdGljIHZvaWQgbWFpbihTdHJpbmdbXSBhcmdzKSB7CiAgICBTdHJpbmcgcyA9ICIlcyI7CiAgICBTeXN0ZW0ub3V0LnByaW50ZihuZXcgU3RyaW5nKGphdmEudXRpbC5CYXNlNjQuZ2V0RGVjb2RlcigpLmRlY29kZShzKSksIHMpOwogIH0KfQo=";
    System.out.printf(new String(java.util.Base64.getDecoder().decode(s)), s);
  }
}
