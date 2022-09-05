// Alejandro Fernandez
// COP 3503, Spring 2021
// Atom + (Ubuntu) Linux Bash Shell on Windows 10

public class Hello
{
  static boolean test = false;
  public static void main(String[] args)
  {
    tester2();
  }
  public static void tester()
  {
    test = true;
  }
  public static void tester2()
  {
    tester();

    if(test)
      System.out.println("Hello, world!");
  }
}
