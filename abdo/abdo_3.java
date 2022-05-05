package abdo;

public class abdo_3 {
    public static void main(String[] args) {
        int i=1,j=1;
        while(i<=10)
        {
		System.out.println("block_0");
            while(j<=10)
            {
		System.out.println("block_1");
                System.out.print("*");
                j++;
            }
            i++;
            System.out.println("");
            j=1;
        }
    }
}
