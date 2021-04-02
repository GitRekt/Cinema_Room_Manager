package cinema;
import java.util.*;
public class Cinema {
    static int price;
    static final int MAX_ROWS = 9;
    static final int MAX_COLS = 9;
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        final int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        final int cols = sc.nextInt();
        try {
            Objects.checkIndex(rows - 1, MAX_ROWS+1);
            Objects.checkIndex(cols - 1, MAX_COLS+1);
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println("The number of row and seats can't be greater than 9!");
        }

        char[][] seats = new char[rows][cols];
        Arrays.stream(seats).forEach(a -> Arrays.fill(a, 'S'));

        final boolean flag;
        int total_seats = rows * cols, front=-1;
        if (total_seats <= 60)
        {
            price = 10;
            flag = false;
        }
        else
        {
            front = rows / 2;
            flag = true;
        }

        while(true)
        {
            System.out.println("\n1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n"+
                    "0. Exit");
            int choice = sc.nextInt();
            switch(choice)
            {
                case 1:
                    ShowSeats(seats);
                    break;

                case 2:
                    boolean wrongInput = true;
                    while(wrongInput) {
                        System.out.println("Enter a row number:");
                        int row_num = sc.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int col_num = sc.nextInt();
                        if (row_num>rows || col_num>cols)
                            System.out.println("Wrong input!");
                        else {
                            if (seats[row_num - 1][col_num - 1] == 'B')
                                System.out.println("That ticket has already been purchased!");
                            else {
                                if (front > -1)
                                    price = (row_num <= front) ? 10 : 8;
                                BuyTicket(row_num, col_num, price, seats);
                                wrongInput = false;
                            }
                        }
                    }
                    break;

                case 3:
                    Stats(seats, flag, front);
                    break;

                case 0: return;
            }
        }
    }

    static void ShowSeats(char[][] seats) {
        System.out.print("\nCinema:\n ");
        for (int i = 1; i <= seats[0].length; i++)
            System.out.print(" " + i);
        System.out.println();

        int counter = 1;
        for (char[] innerArray : seats) {
            System.out.print(counter);
            for (char ch : innerArray) {
                System.out.print(" " + ch);
            }
            System.out.println();
            counter++;
        }
    }

    static void Stats(char[][] seats, boolean flag, int front) {
        int purchasedTicks = 0, CurIncome = 0, TotIncome;
        int TotalSeats = seats.length*seats[0].length;
        if(flag)
        {
            TotIncome = (front*seats[0].length)*10 + ((seats.length-front)*seats[0].length)*8;
        }
        else
            TotIncome = TotalSeats*10;
        int i = 1;
        for (char[] innerArray : seats) {
            for (char ch : innerArray) {
                if (ch == 'B'){
                    ++purchasedTicks;
                    if(flag)
                        CurIncome += (i<=front) ? 10 : 8;
                    else
                        CurIncome += 10;
                }
            }
            i++;
        }
        double percent = (double) (purchasedTicks * 100) / TotalSeats;
        System.out.println("Number of purchased tickets: "+purchasedTicks);
        System.out.printf("Percentage: %.2f%%%n", percent);
        System.out.println("Current income: $" + CurIncome);
        System.out.println("Total income: $" + TotIncome);
    }

    static void BuyTicket(int row_num, int col_num, int Ticket_price, char[][] seats) {
        seats[row_num - 1][col_num - 1] = 'B';
        System.out.println("Ticket price: $" + Ticket_price);
    }
}