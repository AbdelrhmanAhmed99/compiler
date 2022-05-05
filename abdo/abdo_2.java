package abdo;

public class abdo_2 {
    public static void main(String[] args) {

                for (int i = 0; i < 5; i++) {
		System.out.println("block_0");
                    for (int j = 0; j < 5; j++) {
		System.out.println("block_1");
                        if (i >= j) {
		System.out.println("block_2");
                            System.out.print('*');
                        }
                    }
                    System.out.println("\n");
                }
            }
        }

