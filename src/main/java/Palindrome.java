import java.util.Scanner;
public class Palindrome
{

    /*public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter any number:");
        int n = s.nextInt();
        StringBuffer sb = new StringBuffer("");
        while(n > 0) {
            sb.append(n % 10);
            n = n / 10;
        }
        System.out.println(sb);
    }*/

    public static void main(String args[])
    {
        int n, m, a = 0,x;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter any number:");
        n = s.nextInt();


        m = n;
        while(n > 0)
        {
            x = n % 10;
            a = a * 10 + x;
            n = n / 10;
        }
        if(a == m)
        {
            System.out.println("Given number "+m+" is Palindrome");
        }
        else
        {
            System.out.println("Given number "+m+" is Not Palindrome");
        }
    }
}